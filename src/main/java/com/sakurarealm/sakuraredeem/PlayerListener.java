package com.sakurarealm.sakuraredeem;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.getPlayer().sendMessage("Hello!");
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {

    }

}
