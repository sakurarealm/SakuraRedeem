package com.sakurarealm.sakuraredeem;

import com.sakurarealm.sakuraredeem.command.CommandOpen;
import com.sakurarealm.sakuraredeem.utils.Config;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class SakuraRedeem extends JavaPlugin {


    @Override
    public void onEnable() {
        Config.init(this);
        registerCommands();
    }

    private void registerCommands() {

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
