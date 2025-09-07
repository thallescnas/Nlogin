package me.natu.nlogin.main.events;

import me.natu.nlogin.main.Main;
import me.natu.nlogin.main.api.API;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class MoveEvent implements Listener {

    API api = Main.getInstance().api;

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if(!api.areLogged(p)) {
            e.setCancelled(true);
            e.setTo(e.getFrom());
        }
    }
}
