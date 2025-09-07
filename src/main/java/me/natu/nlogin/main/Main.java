package me.natu.nlogin.main;

import me.natu.nlogin.main.api.API;
import org.bukkit.plugin.java.JavaPlugin;

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
        // Plugin startup logic

    }
    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


    public static Main getInstance() {
        return instance;
    }
}

