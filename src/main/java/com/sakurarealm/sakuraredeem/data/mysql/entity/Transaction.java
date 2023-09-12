package com.sakurarealm.sakuraredeem.data.mysql.entity;

import org.bukkit.ChatColor;

import java.time.LocalDateTime;

public class Transaction {

    public String sdk;

    public String player_uuid;

    public LocalDateTime redeemed_at;

    @Override
    public String toString() {
        return ChatColor.GREEN + "SDK: " + ChatColor.BLUE + sdk +
                ChatColor.GREEN + " Player uuid: " + ChatColor.BLUE + player_uuid +
                ChatColor.GREEN + " Redeemed at: " + ChatColor.BLUE + redeemed_at.toString();
    }
}
