package me.natu.nlogin.main.utils;

import me.natu.nlogin.main.Main;
import org.bukkit.ChatColor;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.logging.Level;

public final class DBManager {

    private Connection con;

    public Connection getCon(String type) throws SQLException {
        switch (type.toLowerCase()) {
            case "mysql":
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
        PreparedStatement st = con.prepareStatement("CREATE TABLE IF NOT EXISTS users(uuid varchar(255), passwd varchar(255));");
        st.executeUpdate();
        Main.getInstance().getServer().getLogger().log(Level.INFO, ChatColor.GREEN + "Tabela criada com sucesso!");
        con.close();
    }


}
