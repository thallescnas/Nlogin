package me.natu.nlogin.main;

import me.natu.nlogin.main.api.API;
import me.natu.nlogin.main.commands.LoginCommand;
import me.natu.nlogin.main.commands.RegisterCommand;
import me.natu.nlogin.main.events.LoginEvent;
import me.natu.nlogin.main.events.MoveEvent;
import org.bukkit.plugin.java.JavaPlugin;


public final class Main extends JavaPlugin {


    private static Main instance;

    public API api;

    @Override
    public void onLoad() {
        instance = this;
        api = new API();

        saveDefaultConfig();
        api.loadUsers();
    }

    @Override
    public void onEnable() {
        loadEvents();
        getCommand("register").setExecutor(new RegisterCommand());
        getCommand("login").setExecutor(new LoginCommand());
    }
    @Override
    public void onDisable() {
        api.saveUsers();
    }


    public static Main getInstance() {
        return instance;
    }

    private void loadEvents() {
        getServer().getPluginManager().registerEvents(new LoginEvent(), this);
        getServer().getPluginManager().registerEvents(new MoveEvent(), this);
    }
}

