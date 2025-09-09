package me.natu.nlogin.main.api;

import me.natu.nlogin.main.Main;
import me.natu.nlogin.main.utils.Encryptor;
import me.natu.nlogin.main.utils.FileC;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.logging.Level;

public final class API {


    private final FileC userf = new FileC("users.yml");
    private final Map<UUID, String> users = new HashMap<>();
    private final List<UUID> logged = new ArrayList<>();

    public Map<UUID, String> getUsers() {
        return users;
    }

    public void registerUser(Player p, String passwd) {
        registerUser(p.getUniqueId(), passwd);
    }

    public void registerUser(UUID p, String passwd) {
        if (!users.containsKey(p)) {
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

    public void loginUser(UUID p) {
        logged.add(p);
    }

    public void loginUser(Player p) {
        loginUser(p.getUniqueId());
    }

    public List<UUID> getRegisteredUsers() {
        if (!users.isEmpty()) {
            return new ArrayList<>(users.keySet());
        }
        return null;
    }

    public List<Player> getRegisteredUsersp() {
        ArrayList<Player> a = new ArrayList<Player>();
        if (!users.isEmpty()) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (getUsers().containsKey(p.getUniqueId()) && Bukkit.getOnlinePlayers().contains(p)) {
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

    public boolean verifyPasswd(UUID p, String passwd) {
        if (areRegistered(p)) {
            return Encryptor.verifyPassword(passwd, users.get(p));
        } else {
            return false;
        }
    }

    public boolean verifyPasswd(Player p, String passwd) {
        return verifyPasswd(p.getUniqueId(), passwd);
    }

    public void saveUsers() {
        if (!users.isEmpty()) {
            for (Map.Entry<UUID, String> k : users.entrySet()) {
                if(!userf.getConfig().contains("users." + k.getKey().toString())) {
                    userf.getConfig().set("users." + k.getKey().toString(), k.getValue());
                }
            }
            try {
                userf.saveConfig();
            } catch (Exception e) {
                Main.getInstance().getLogger().log(Level.WARNING, "Não foi possivel salvar os usuários!");
            }
        }
    }

    public void loadUsers() {
        if (userf.exists() && userf.getConfig().getConfigurationSection("users") != null) {
            for (String u : userf.getConfig().getConfigurationSection("users").getKeys(false)) {
                users.put(UUID.fromString(u), userf.getConfig().getString("users." + u));
            }
        }
    }


}
