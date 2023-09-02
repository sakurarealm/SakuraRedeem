package com.sakurarealm.sakuraredeem.utils;

import org.bukkit.Bukkit;

public class BukkitLogger {

    private static final String TAG = "[SRRedeem] ";

    public static void info(String info) {
        Bukkit.getLogger().info(TAG + info);
    }

    public static void warn(String warn) {
        Bukkit.getLogger().warning(TAG + warn);
    }

    public static void error(String error) {
        Bukkit.getLogger().severe(error);
    }
}
