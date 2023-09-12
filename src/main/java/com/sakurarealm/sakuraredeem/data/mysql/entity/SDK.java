package com.sakurarealm.sakuraredeem.data.mysql.entity;

import org.bukkit.ChatColor;

import java.time.LocalDateTime;

public class SDK {

    public String sdk;

    public String package_name;

    public LocalDateTime created_at;

    public LocalDateTime expire_at;

    public Package package_;

    public String toString() {
        return ChatColor.GREEN + "SDK: " + ChatColor.BLUE + sdk +
                ChatColor.GREEN + " Package name: " + ChatColor.BLUE + package_name +
                ChatColor.GREEN + " Created at: " + ChatColor.BLUE + created_at.toString() +
                ChatColor.GREEN + " Will expire at: " + ChatColor.BLUE + expire_at.toString();
    }

}
