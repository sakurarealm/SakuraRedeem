package com.sakurarealm.sakuraredeem.utils;

import com.sakurarealm.sakuraredeem.common.PackageItemEditorManager;
import com.sakurarealm.sakuraredeem.data.mysql.MybatisUtils;
import com.sakurarealm.sakuraredeem.data.mysql.helper.PackageHelper;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class Config {

    public static void init(Plugin plugin) {
        plugin.saveDefaultConfig();

        MybatisUtils.init(plugin.getConfig());
        MybatisUtils.openSession();

        PackageHelper.getInstance().createAllTables();

        PackageItemEditorManager.init();
    }

    public static boolean isAdmin(Player player) {
        return player.hasPermission("sakuraredeem.admin");
    }
}
