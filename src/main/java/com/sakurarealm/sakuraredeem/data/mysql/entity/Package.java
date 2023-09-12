package com.sakurarealm.sakuraredeem.data.mysql.entity;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Represents a package with its associated details.
 * <p>
 * Each package has a name, creation timestamp, list of items (represented as ItemStacks),
 * and a list of associated commands.
 * </p>
 */
public class Package {

    /**
     * The name of the package.
     */
    public String name;

    /**
     * The date and time when the package was created.
     */
    public LocalDateTime created_at;

    /**
     * A list of items associated with the package.
     * <p>
     * The ItemStack is of org.bukkit.inventory.ItemStack instance.
     * </p>
     */
    public List<ItemStack> items;

    /**
     * A list of commands associated with the package.
     */
    public List<Command> commands;

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(ChatColor.BOLD).append(ChatColor.GOLD).append("Package Information:\n");
        builder.append(formatPackage()).append('\n');

        if (items != null) {
            builder.append(ChatColor.BOLD).append(ChatColor.GOLD).append("Package Items:\n");

            final int maxHeight = 9;
            final int maxLength = 40;

            for (int i=0; i < items.size() && i < maxHeight; i++) {
                String text = formatItem(items.get(i));
                if (text.length() > maxLength + 3) {
                    text = text.substring(0, maxLength) + ChatColor.GREEN + "...";
                }
                builder.append(text).append("\n");
            }

            if (items.size() > maxHeight) {
                builder.append(ChatColor.GREEN + "... (").append(items.size() - maxHeight)
                        .append(" more items to display.)");
            }
        }

        if (commands != null) {
            builder.append(ChatColor.BOLD).append(ChatColor.GOLD).append("Package Commands:\n");

            final int maxHeight = 5;
            final int maxLength = 40;

            for (int i=0; i < commands.size() && i < maxHeight; i++) {
                String text = commands.get(i).toString();
                if (text.length() > maxLength + 3) {
                    text = text.substring(0, maxLength) + ChatColor.GREEN + "...";
                }
                builder.append(text).append("\n");
            }

            if (commands.size() > maxHeight) {
                builder.append(ChatColor.GREEN + "... (").append(commands.size() - maxHeight)
                        .append(" more commands to display.)");
            }
        }

        return builder.toString();
    }

    public String formatPackage() {
        return ChatColor.GREEN + "Package name: " + ChatColor.BLUE + name + "\n" +
                ChatColor.GREEN + " Created at: " + ChatColor.BLUE + created_at.toString();
    }

    public String formatItem(ItemStack itemStack) {
        ItemMeta meta = itemStack.getItemMeta();
        return ChatColor.GREEN + "Item id: " + ChatColor.BLUE + itemStack.getType().toString() +
                ChatColor.GREEN + "Display name: " + ChatColor.RESET + meta.getDisplayName();
    }
}