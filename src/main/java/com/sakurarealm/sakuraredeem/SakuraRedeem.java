package com.sakurarealm.sakuraredeem;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class SakuraRedeem extends JavaPlugin {

    private PlayerListener playerListener;

    @Override
    public void onEnable() {
        // Plugin startup logic
        playerListener = new PlayerListener();

        Bukkit.getPluginManager().registerEvents(playerListener, this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
