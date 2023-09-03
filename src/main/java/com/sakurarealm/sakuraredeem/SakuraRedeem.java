package com.sakurarealm.sakuraredeem;

import com.sakurarealm.sakuraredeem.command.CommandPackage;
import com.sakurarealm.sakuraredeem.utils.Config;
import org.bukkit.plugin.java.JavaPlugin;

public final class SakuraRedeem extends JavaPlugin {

    private static SakuraRedeem PLUGIN;

    public static SakuraRedeem getPlugin() {
        return PLUGIN;
    }

    @Override
    public void onEnable() {
        PLUGIN = this;

        Config.init(this);
        registerCommands();
    }

    private void registerCommands() {
        getCommand("sakurapackage").setExecutor(new CommandPackage());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

}
