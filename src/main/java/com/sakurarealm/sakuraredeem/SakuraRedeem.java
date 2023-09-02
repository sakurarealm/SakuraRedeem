package com.sakurarealm.sakuraredeem;

import com.sakurarealm.sakuraredeem.command.CommandOpen;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class SakuraRedeem extends JavaPlugin {

    private PlayerListener playerListener;

    @Override
    public void onEnable() {
        // Plugin startup logic
        playerListener = new PlayerListener();

        Bukkit.getPluginManager().registerEvents(playerListener, this);

        getCommand("redeemeopn").setExecutor(new CommandOpen());

        saveDefaultConfig();
    }

    private void registerCommands() {

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
