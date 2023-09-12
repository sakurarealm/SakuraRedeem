package com.sakurarealm.sakuraredeem.data.mysql.entity;

import org.bukkit.ChatColor;

public class Command {

    public String command;

    public boolean use_terminal;

    public String toString() {
        return ChatColor.GREEN + "Command: " + ChatColor.BLUE + command +
                ChatColor.GREEN + " executed by " +
                (use_terminal ? ChatColor.YELLOW + "terminal" : ChatColor.YELLOW + "player");
    }
}
