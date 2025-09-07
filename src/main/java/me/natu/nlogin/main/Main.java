package me.natu.nlogin.main;

import me.natu.nlogin.main.api.API;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class Main extends JavaPlugin {


    private static Main instance;

    public API api;

    @Override
    public void onLoad() {
        instance = this;
        api = new API();
    }

    @Override
    public void onEnable() {
        File f = new File(getDataFolder(), "config.yml");
        if(!f.exists()) {
            getConfig().options().copyDefaults(true);
        }

    }
    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


    public static Main getInstance() {
        return instance;
    }
}

