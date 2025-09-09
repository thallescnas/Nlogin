package me.natu.nlogin.main.commands;

import me.natu.nlogin.main.Main;
import me.natu.nlogin.main.api.API;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LoginCommand implements CommandExecutor {

    API api = Main.getInstance().api;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) return false;
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (api.areRegistered(p) && !api.areLogged(p)) {
                if (api.verifyPasswd(p, args[0])) {
                    api.loginUser(p);
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getInstance().getConfig().getString("messages.loginsucessmessage")));
                }
            } else if (!api.areRegistered(p)) {
                p.sendMessage(ChatColor.RED + "Você não esta registrado! Utilize: /register <senha> <senha>");
            }
        }
        return false;
    }
}
