package com.sakurarealm.sakuraredeem.utils;

import com.sakurarealm.sakuraredeem.data.mysql.MybatisUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class Config {

    public static void init(Plugin plugin) {
        plugin.saveDefaultConfig();

        ConfigurationSection section = plugin.getConfig().getConfigurationSection("mysql");
        MybatisUtils.init(section);
        MybatisUtils.openSession();

    }

    public static boolean isAdmin(Player player) {
        return player.hasPermission("sakuraredeem.admin");
    }
}
