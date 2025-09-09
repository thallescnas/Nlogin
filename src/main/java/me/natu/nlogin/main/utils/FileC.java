package me.natu.nlogin.main.utils;

import me.natu.nlogin.main.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.File;
import java.io.IOException;

public class FileC extends File {

    private FileConfiguration config;

    public FileC(@NonNull String name) {
        super(Main.getInstance().getDataFolder(), name);
        if(!this.exists()) {
            try {
                this.createNewFile();
            } catch (IOException e) {
                // oohh
            }
        }
        this.config = YamlConfiguration.loadConfiguration(this);
    }


    public FileConfiguration getConfig() {
        return config;
    }

    public void saveConfig() throws java.io.IOException {
            this.getConfig().save(this);
    }
}
