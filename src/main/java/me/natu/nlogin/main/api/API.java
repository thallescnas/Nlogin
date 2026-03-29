package me.natu.nlogin.main.api;

import me.natu.nlogin.main.Main;
import me.natu.nlogin.main.utils.DBManager;
import me.natu.nlogin.main.utils.Encryptor;
import me.natu.nlogin.main.utils.FileC;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;

public final class API {

    private final Map<UUID, String> users = new HashMap<>();
    private final List<UUID> logged = new ArrayList<>();
    private final FileC messagef = new FileC("messages.yml");
    private final DBManager dbManager = new DBManager();

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

    public List<Player> getRegisteredUsersP() {
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
            try {
                dbManager.saveUsers();
                Main.getInstance().getLogger().log(Level.FINE, "Usuários salvos com sucesso!");
            } catch (SQLException e) {
                Main.getInstance().getLogger().log(Level.SEVERE, "Não foi possivel salvar os usuarios! Fallback para modo yml...");
            }
        }
    }

    public void loadUsers() {
       try {
           dbManager.loadUsers();
           Main.getInstance().getLogger().log(Level.FINE, "Usuários carregados com sucesso!");
       } catch (SQLException e) {
           Main.getInstance().getLogger().log(Level.SEVERE, "Não foi possivel carregaar os usuarios!");
       }
    }

    public FileC getMessage() {
        if(!messagef.exists()) {
            Main.getInstance().saveResource("messages.yml", false);
        }
        return messagef;
    }



}
