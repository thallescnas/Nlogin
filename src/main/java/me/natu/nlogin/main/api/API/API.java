package me.natu.nlogin.main.api.API;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

public final class API {


    private Map<UUID, String> users = new HashMap<>();

    public Map<UUID, String> getUsers() {
        return users;
    }

    public void registerUser(Player p, String password) {
        users.put(p.getUniqueId(), password);
    }
    public void registerUser(UUID p, String password) {
        users.put(p, password);
    }

    public List<UUID> getRegisteredUsers() {
        if(!users.isEmpty()) {
            return new ArrayList<>(users.keySet());
        }
        return null;
    }

    public List<Player> getRegisteredUsersp() {
        ArrayList<Player> a = new ArrayList<Player>();
        if(!users.isEmpty()) {
            for(Player p : Bukkit.getOnlinePlayers()) {
                if(getUsers().containsKey(p.getUniqueId()) && Bukkit.getOnlinePlayers().contains(p)) {
                    a.add(p);
                }
            }
        }
        return a;
    }

    public boolean areRegistered(UUID p) {
        return users.containsKey(p);
    }

    public boolean areRegistered(Player p) {
        return areRegistered(p.getUniqueId());
    }

    public String getPasswd(UUID p) {
        if (areRegistered(p)) {
            return users.get(p);
        } else {
            return null;
        }
    }

    public String getPasswd(Player p) {
        return getPasswd(p.getUniqueId());
    }

}
