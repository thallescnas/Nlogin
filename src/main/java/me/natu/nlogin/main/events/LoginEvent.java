package me.natu.nlogin.main.events;

import me.natu.nlogin.main.Main;
import me.natu.nlogin.main.api.API;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class LoginEvent implements Listener {


    private API api = Main.getInstance().api;

    @EventHandler
    public void PlayerLogin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (!api.areLogged(p)) {
            new BukkitRunnable() {
                int time = 20;

                @Override
                public void run() {
                    if (!api.areRegistered(p)) {
                        if (time > 0) {
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getInstance().api.getMessage().getConfig().getString("register.timer")
                                    .replace("{tempo}", String.valueOf(time))));
                        } else {
                            p.kickPlayer(ChatColor.translateAlternateColorCodes('&', Main.getInstance().api.getMessage().getConfig().getString("register.timeout")));
                            cancel();
                        }
                    } else if (api.areRegistered(p)) {
                        if (!api.areLogged(p)) {
                            if (time > 0) {
                                p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getInstance().api.getMessage().getConfig().getString("login.timer")
                                        .replace("{tempo}", String.valueOf(time))));
                            } else {
                                p.kickPlayer(ChatColor.translateAlternateColorCodes('&', Main.getInstance().api.getMessage().getConfig().getString("login.timeout")));
                                cancel();
                            }
                        } else {
                            cancel();
                        }
                    }
                    time--;
                }
            }.runTaskTimer(Main.getInstance(), 0, 20);
        }
    }

    @EventHandler
    public void PlayerChat(AsyncPlayerChatEvent e) {
        if (!api.areLogged(e.getPlayer())) {
            if (!e.getMessage().equals("/login") || e.getMessage().equals("/register")) {
                e.setCancelled(true);
                e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getInstance().api.getMessage().getConfig().getString("chat_error")));
            }
        }
    }
}
