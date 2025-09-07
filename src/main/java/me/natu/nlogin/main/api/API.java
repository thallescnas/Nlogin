package me.natu.nlogin.main.api;

import me.natu.nlogin.main.utils.Encryptor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

public final class API {


    private Map<UUID, String> users = new HashMap<>();
    private List<UUID> logged = new ArrayList<>();

    public Map<UUID, String> getUsers() {
        return users;
    }

    public void registerUser(Player p, String passwd) {
        registerUser(p.getUniqueId(), passwd);
    }
    public void registerUser(UUID p, String passwd) {
        if(!users.containsKey(p)) {
            users.put(p, Encryptor.hashPassword(passwd));
        }
    }

    public List<UUID> getLogged() {
        return logged;
    }

    public boolean areLogged(UUID p) {
        return logged.contains(p);
    }
    public boolean areLogged(Player p) {
        return areLogged(p.getUniqueId());
    }

    public void loginUser(UUID p, String passwd) {
        if(users.containsKey(p)) {
            if(!areLogged(p)) {
                if(Encryptor.verifyPassword(passwd, users.get(p))) {
                    logged.add(p);
                }
            }
        }
    }

    public void loginUser(Player p, String passwd) {
        loginUser(p.getUniqueId(), passwd);
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
