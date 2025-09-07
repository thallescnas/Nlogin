package me.natu.nlogin.main.commands;

import me.natu.nlogin.main.Main;
import me.natu.nlogin.main.api.API;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RegisterCommand implements CommandExecutor {

    API api = Main.getInstance().api;
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) return false;
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if(!api.areRegistered(p)) {
                if(args.length == 2) {
                    if(args[0].equals(args[1])) {
                        api.registerUser(p, args[0]);
                        p.kickPlayer(ChatColor.translateAlternateColorCodes('&', Main.getInstance().getConfig().getString("messages.registerkicksucess")));
                    } else {
                        p.sendMessage(ChatColor.RED + "Senha de confirmação errada!");
                    }
                } else {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getInstance().getConfig().getString("messages.registererror")));
                }
            } else {
                p.sendMessage("Você já esta registrado! Utilize: /login <senha>");
            }
        }
        return false;
    }
}
