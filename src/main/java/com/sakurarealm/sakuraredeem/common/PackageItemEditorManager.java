package com.sakurarealm.sakuraredeem.common;

import com.sakurarealm.sakuraredeem.SakuraRedeem;
import com.sakurarealm.sakuraredeem.data.mysql.helper.ItemStackHelper;
import com.sakurarealm.sakuraredeem.data.mysql.helper.PackageHelper;
import com.sakurarealm.sakuraredeem.utils.AsyncTaskRunner;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class PackageItemEditorManager implements Listener {

    private static PackageItemEditorManager INSTANCE;
    private final Map<String, PackageEditorInstance> openedInventories = new HashMap<>();

    private PackageItemEditorManager() {

    }

    /**
     * Initializes the PackageItemEditorManager and registers it as an event listener.
     * <p></p>
     * <strong>Note:</strong> This method should be called during the plugin's startup sequence to ensure
     * proper event handling for the PackageItemEditorManager.
     */
    public static void init() {
        if (INSTANCE == null)
            INSTANCE = new PackageItemEditorManager();
        Bukkit.getPluginManager().registerEvents(INSTANCE, SakuraRedeem.getPlugin());
    }

    /**
     * Retrieves the singleton instance of the PackageItemEditorManager.
     *
     * @return The singleton instance of PackageItemEditorManager, or null if not initialized.
     */
    public static PackageItemEditorManager getInstance() {
        return INSTANCE;
    }

    /**
     * Event handler triggered when an inventory is closed.
     * <p>
     * Note: The function expects the inventory's name to match the package name.
     * </p>
     *
     * @param event The InventoryCloseEvent that triggered the method.
     */
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        String packageName = event.getInventory().getName();
        PackageEditorInstance editor = openedInventories.get(packageName);
        if (editor != null && editor.owner.equals(event.getPlayer())) {
            openedInventories.remove(packageName);
            // Database writing in async thread
            AsyncTaskRunner<Boolean> taskRunner = new AsyncTaskRunner<>();
            taskRunner.runAsyncTask(() -> ItemStackHelper.getInstance()
                            .saveItems(packageName, event.getInventory().getContents()),
            (success) -> {
                if (success)
                    editor.callback.accept(true, "成功保存" + packageName);
                else
                    editor.callback.accept( false, "保存" + packageName + "失败，请查看后台");
            });
        }
    }

    /**
     * 为一个玩家打开编辑某个包裹内容的编辑器
     *
     * @param player 为这个玩家打开页面
     * @param packageName 要打开的包裹的名字 pre: 已经存在
     * @param syncCallback 回调函数, boolean: 是否成功; String: 信息
     */
    public void openPackageInventory(Player player, String packageName, BiConsumer<Boolean, String> syncCallback) {
        // check if opened
        if (openedInventories.get(packageName) != null) {
            syncCallback.accept(false, "包裹" + packageName + "已被打开");
            return;
        }
        // Database operations in a separate thread.
        Bukkit.getScheduler().runTaskAsynchronously(SakuraRedeem.getPlugin(), () -> {
            boolean exists = PackageHelper.getInstance().exists(packageName);
            if (exists) {
                ItemStack[] items = ItemStackHelper.getInstance().loadItems(packageName);
                // Run Minecraft operation and hashtable in a synchronized thread
                Bukkit.getScheduler().runTask(SakuraRedeem.getPlugin(), () -> {
                    openedInventories.put(packageName, new PackageEditorInstance(player, syncCallback));
                    Inventory inventory = Bukkit.createInventory(player, 54, packageName);
                    if (items != null && items.length > 0) {
                        ItemStack[] fullItemStacks = new ItemStack[54];
                        System.arraycopy(items, 0, fullItemStacks, 0, items.length);
                        for (ItemStack item : items) {
                            if (item != null)
                                Bukkit.getLogger().info(item.toString());
                            else
                                Bukkit.getLogger().info("NULL");
                        }
                        inventory.setContents(fullItemStacks);
                    }
                    player.openInventory(inventory);
                });
            } else {
                runCallback(syncCallback, false, "包裹" + packageName + "不存在");
            }
        });
    }

    /**
     *
     * @param callback 回调函数, boolean: 是否成功; String: 信息
     * @param success 是否成功
     * @param msg 信息
     */
    private void runCallback(BiConsumer<Boolean, String> callback, boolean success, String msg) {
        Bukkit.getScheduler().runTask(SakuraRedeem.getPlugin(), () -> {
            callback.accept(false, msg);
        });
    }


    private static class PackageEditorInstance {
        Player owner;
        BiConsumer<Boolean, String> callback;

        PackageEditorInstance(Player owner, BiConsumer<Boolean, String> callback) {
            this.owner = owner;
            this.callback = callback;
        }
    }
}
