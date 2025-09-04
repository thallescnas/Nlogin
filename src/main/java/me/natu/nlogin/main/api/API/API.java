package me.natu.nlogin.main.api.API;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class API {


    private Map<UUID, String> users = new HashMap<>();

    public Map<UUID, String> getUsers() {
        return users;
    }

    public void addUser(Player p, String password) {
        users.put(p.getUniqueId(), password);
    }
    public void addUser(UUID p, String password) {
        users.put(p, password);
    }
}
