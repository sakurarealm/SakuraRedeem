package com.sakurarealm.sakuraredeem.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

public class CommandOpen implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player;
        if (sender instanceof Player) {
            player = (Player) sender;
            if (!player.hasPermission("srredeem.admin")) {
                player.sendMessage(ChatColor.DARK_RED + "你没有执行这条指令的权限!");
                return true;
            }
        } else {
            sender.sendMessage(ChatColor.DARK_RED + "只有玩家可以执行这条指令!");
            return true;
        }

        if (args.length != 1) {
            sender.sendMessage(ChatColor.DARK_RED + "使用方式错误!");
            return true;
        }
        String packageName = args[0];

        Inventory inventory = Bukkit.getServer().createInventory(null, InventoryType.CHEST, packageName);

        player.openInventory(inventory);

        return true;
    }
}
