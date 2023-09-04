package com.sakurarealm.sakuraredeem.command;

import com.sakurarealm.sakuraredeem.SakuraRedeem;
import com.sakurarealm.sakuraredeem.common.PackageItemEditorManager;
import com.sakurarealm.sakuraredeem.data.mysql.helper.CommandHelper;
import com.sakurarealm.sakuraredeem.data.mysql.helper.PackageHelper;
import com.sakurarealm.sakuraredeem.utils.AsyncTaskRunner;
import com.sakurarealm.sakuraredeem.utils.Config;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandPackage implements CommandExecutor {

    private static final String COMMAND = "sakurapackage";

    public static String getCommand() {
        return COMMAND;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String cmd, String[] args) {
        Player player;
        if (sender instanceof Player) {
            player = (Player) sender;
            if (!Config.isAdmin(player)) {
                player.sendMessage(ChatColor.BOLD.toString() + ChatColor.DARK_RED.toString() + "你没有执行这条指令的权限!");
                return true;
            }
        }

        if (args.length < 1) {
            sender.sendMessage(ChatColor.DARK_RED + "错误的使用方式!");
            return true;
        }


        switch (args[0]) {
            case "new":
                newPackage(sender, cmd, args);
                return true;
            case "item":
                item(sender, cmd, args);
                return true;
            default:
                sender.sendMessage(ChatColor.DARK_RED + "错误的使用方式!");
                return true;
        }

    }

    private void newPackage(CommandSender sender, String cmd, String[] args) {
        if (args.length != 2) {
            sender.sendMessage(ChatColor.DARK_RED + "错误的使用方式！\n/" + cmd + " new <package> - 新建一个包。注意：这个指令将清空同名包 (Op Only)");
            return;
        }
        // Create new package
        AsyncTaskRunner<Boolean> taskRunner = new AsyncTaskRunner<>();
        taskRunner.runAsyncTask(() -> PackageHelper.getInstance().newPackage(args[1]),
                (success) -> {
                    if (success) {
                        sender.sendMessage(ChatColor.GREEN + "已成功创建新的包裹： " + args[1]);
                    } else {
                        sender.sendMessage(ChatColor.DARK_RED.toString() + "数据库读写错误, 请检查后台!");
                    }
                });

    }


    private void item(CommandSender sender, String cmd, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.DARK_RED + "这个指令只能由玩家执行!");
            return;
        } else if (args.length != 2) {
            sender.sendMessage(ChatColor.DARK_RED + "错误的使用方式！\n/" + cmd + " item <package> - 编辑包中的物品 (Op Only)");
            return;
        }

        PackageItemEditorManager.getInstance().openPackageInventory((Player) sender, args[1],
            (success, msg) -> {
                if (success) {
                    sender.sendMessage(ChatColor.GREEN + "操作成功, 对" + args[1] + "的编辑以保存！");
                } else {
                    sender.sendMessage(ChatColor.DARK_RED + "操作失败: " + msg);
                }
            });
    }

    public void cmd(CommandSender sender, String cmd, String[] args) {
        if (args.length < 3 || args.length > 5 || // Check the command syntax
            (args.length == 3 && !args[2].equalsIgnoreCase("clear")) ||
            ((args.length == 4 || args.length == 5) && !args[2].equalsIgnoreCase("add")) ||
            (args.length == 5 && !(args[4].equalsIgnoreCase("self") || args[4].equalsIgnoreCase("terminal")))) {
            sender.sendMessage(ChatColor.DARK_RED + "错误的使用方式！\n/" + cmd +
                    " add <executable> [self|terminal] - 为兑换包时添加执行一条指令 (Op Only)\n/" +
                    cmd + " <package> clear - 清空执行包时的所有指令 (Op Only)");
            return;
        }

        AsyncTaskRunner<Boolean> taskRunner = new AsyncTaskRunner<>();

        taskRunner.runAsyncTask(() -> PackageHelper.getInstance().exists(args[1]), (success) -> {
            if (success) {
                // Clear commands
                if (args.length == 3) {
                    AsyncTaskRunner<Boolean> clearTaskRunner = new AsyncTaskRunner<>();
                    clearTaskRunner.runAsyncTask(() -> CommandHelper.getInstance().clearCommands(args[1]),
                        (success1) -> {
                            if (success1) {

                            } else {
                                sender.sendMessage(ChatColor.DARK_RED.toString() + "数据库读写错误, 请检查后台!");
                            }
                        });
                }
            } else {
                sender.sendMessage(ChatColor.DARK_RED + "包裹" + args[1] + "不存在");
            }
        });

    }

}
