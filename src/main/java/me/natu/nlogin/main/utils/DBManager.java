package me.natu.nlogin.main.utils;

import me.natu.nlogin.main.Main;
import me.natu.nlogin.main.api.API;
import org.bukkit.ChatColor;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

public final class DBManager {

    private Connection con;

    public Connection getCon(String type) throws SQLException {
        switch (type.toLowerCase()) {
            case "mysql":
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + "", "", "");
                break;

            default:
                File f = new File(Main.getInstance().getDataFolder(), "users.db");
                if (!f.exists()) {
                    try {
                        f.createNewFile();
                    } catch (IOException e) {
                        Main.getInstance().getLogger().log(Level.WARNING, "Não foi possivel criar o banco de dados.");
                    }
                }
                con = DriverManager.getConnection("jdbc:sqlite:users.db");
                break;
        }
        return con;
    }


    public void createTable() throws SQLException {
        PreparedStatement st = con.prepareStatement("CREATE TABLE IF NOT EXISTS users(uuid varchar(255) NOT NULL PRIMARY KEY, passwd varchar(255) NOT NULL);");
        st.executeUpdate();
        Main.getInstance().getServer().getLogger().log(Level.INFO, ChatColor.GREEN + "Tabela criada com sucesso!");
        con.close();
    }

    public void insertUser(UUID u, String passwd) throws  SQLException {
        PreparedStatement st = con.prepareStatement("INSERT INTO users(uuid, passwd) VALUES (?,?);");

        st.setString(1, u.toString());
        st.setString(2, passwd);

        st.executeUpdate();
    }

    public List<UUID> getUsers() throws SQLException {

        List<UUID> a = new ArrayList<UUID>();

        PreparedStatement st = con.prepareStatement("SELECT uuid FROM users;");
        ResultSet rs = st.executeQuery();

        while(rs.next()) {
            a.add(UUID.fromString(rs.getString("uuid")));
        }
        return a;
    }

    public boolean hasUser(UUID uuid) throws SQLException {
        PreparedStatement st = con.prepareStatement("SELECT * FROM users WHERE uuid = ?;");
        st.setString(1, uuid.toString());
        return st.executeQuery().next();
    }

    public void saveUsers() throws SQLException {
        API api = Main.getInstance().api;

        if(!api.getUsers().isEmpty()) {
            for(Map.Entry<UUID, String> u : api.getUsers().entrySet()) {
                if(!hasUser(u.getKey())) {
                    insertUser(u.getKey(), u.getValue());
                    con.close();
                }
            }
        }
    }

    public void loadUsers() throws SQLException {
        API api = Main.getInstance().api;
        PreparedStatement st = con.prepareStatement("SELECT * FROM users;");
        ResultSet rs = st.executeQuery();
        while(rs.next()) {
            UUID uuid = UUID.fromString(rs.getString("uuid"));
            String passwd = rs.getString("passwd");
            api.getUsers().put(uuid, passwd);
        }
    }

}
