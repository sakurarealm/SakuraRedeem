package com.sakurarealm.sakuraredeem.command;

import com.sakurarealm.sakuraredeem.data.mysql.helper.PackageHelper;
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

        switch (args[0]) {
            case "new":
                newPackage(sender, cmd, args);
                return true;
            default:
                sender.sendMessage(ChatColor.DARK_RED + "错误的使用方式!");
                return true;
        }

        return true;
    }

    private void newPackage(CommandSender sender, String cmd, String[] args) {
        if (args.length != 2) {
            sender.sendMessage(ChatColor.DARK_RED + "错误的使用方式！\n/" + cmd + " new <package> - 新建一个包。注意：这个指令将清空同名包 (Op Only)");
            return;
        }
        // Create new package
        if (PackageHelper.getInstance().newPackage(args[1])) {
            sender.sendMessage(ChatColor.GREEN + "成功建立了新的包裹" + args[1]);
        } else {
            sender.sendMessage(ChatColor.DARK_RED + "后端错误, 请查看终端!");
        }

    }



}