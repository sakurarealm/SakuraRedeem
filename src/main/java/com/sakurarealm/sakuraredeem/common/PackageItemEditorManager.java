package com.sakurarealm.sakuraredeem.common;

import com.sakurarealm.sakuraredeem.SakuraRedeem;
import com.sakurarealm.sakuraredeem.data.mysql.helper.ItemStackHelper;
import com.sakurarealm.sakuraredeem.data.mysql.helper.PackageHelper;
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

    private final Map<String, PackageEditorInstance> openedInventories = new HashMap<>();

    private static PackageItemEditorManager INSTANCE;

    private PackageItemEditorManager() {

    }

    public static void init() {
        if (INSTANCE != null)
            INSTANCE = new PackageItemEditorManager();
        Bukkit.getPluginManager().registerEvents(INSTANCE, SakuraRedeem.getPlugin());
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        String packageName = event.getInventory().getName();
        PackageEditorInstance editor = openedInventories.get(packageName);
        if (editor != null && editor.owner.equals(event.getPlayer())) {
            openedInventories.remove(packageName);
            // Database writing in async thread
            Bukkit.getScheduler().runTaskAsynchronously(SakuraRedeem.getPlugin(), () -> {
                boolean success = ItemStackHelper.getInstance().saveItems(packageName, event.getInventory().getContents());
                // Call the callback functions in a synchronized thread
                if (success)
                    runCallback(editor.callback, true, "成功保存" + packageName);
                else
                    runCallback(editor.callback, false, "保存" + packageName + "失败，请查看后台");
            });
        }
    }

    public static PackageItemEditorManager getInstance() {
        return INSTANCE;
    }

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
                    inventory.setContents(items);
                    player.openInventory(inventory);
                });
            } else {
                runCallback(syncCallback, false, "包裹" + packageName + "不存在");
            }
        });
    }

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
