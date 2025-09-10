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
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getInstance().api.getMessage().getConfig().getString("login.sucess")));
                } else {
                    int i = 3;
                    if (i <= 3) {
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getInstance().api.getMessage().getConfig().getString("login.wrong_passwd")
                                .replace("{tentativas}", String.valueOf(i))));
                        i--;
                    } else if (i == 0) {
                        p.kickPlayer(ChatColor.RED + "Numero maximo de tentativas atingido!");
                    }
                }
            } else if (!api.areRegistered(p)) {
                p.sendMessage(ChatColor.RED + "Você não esta registrado! Utilize: /register <senha> <senha>");
            }
        }
        return false;
    }
}
