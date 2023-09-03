package com.sakurarealm.sakuraredeem.command;

import com.sakurarealm.sakuraredeem.data.mysql.helper.PackageHelper;
import com.sakurarealm.sakuraredeem.utils.AsyncTaskRunner;
import com.sakurarealm.sakuraredeem.utils.Config;
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
            if (Config.isAdmin(player)) {
                player.sendMessage(ChatColor.DARK_RED + "你没有执行这条指令的权限!");
                return true;
            }
        }

        if (args.length < 1) {
            sender.sendMessage(ChatColor.DARK_RED + "错误的使用方式!");
            return true;
        }

        if (args[0].equals("new")) {
            newPackage(sender, cmd, args);
            return true;
        }
        sender.sendMessage(ChatColor.DARK_RED + "错误的使用方式!");
        return true;

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
                        sender.sendMessage("");
                    }
                });

    }


    private void item(CommandSender sender, String cmd, String[] args) {

    }


}
