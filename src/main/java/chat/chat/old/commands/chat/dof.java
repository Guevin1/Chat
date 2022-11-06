package chat.chat.commands.chat;

import chat.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class dof implements CommandExecutor {
    //private Main plugin;
    public dof(Chat plugin) {
        //	this.plugin = plugin;

    }
    @Override
    public boolean onCommand(CommandSender se, Command cmd, String arg, String[] args) {
        File u_file = new File("plugins/Chat/users.yml");
        FileConfiguration config_u = YamlConfiguration.loadConfiguration(u_file);
        if (se instanceof Player) {
            Player pl = (Player) se;
            if (args.length == 0) {
                se.sendMessage(ChatColor.RED + "Укажите Действие");
                return true;
            }
            if (!config_u.getBoolean(pl.getName() + ".mute")) {
                if (se.hasPermission("BC.me")) {
                    String L = pl.getLocation().getWorld().getName();
                    for (Player p : Bukkit.getWorld(L).getPlayers()) {
                        int me_r = 100;
                        if (p.getLocation().distance(pl.getLocation()) <= me_r) {
                            StringBuilder message = new StringBuilder();
                            for (int i = 0; i < args.length; i++) {
                                message.append(args[i]).append(" ");
                            }
                            p.sendMessage(ChatColor.LIGHT_PURPLE + "[do] " + pl.getName() + " " + message);
                        }
                    }
                }
            }else {
                se.sendMessage(ChatColor.RED + "Вы в муте");
            }

        }else {
            Bukkit.getLogger().info("Вы сервер Но мы отправим это сообщении");
            for (Player p : Bukkit.getOnlinePlayers()) {
                StringBuilder message = new StringBuilder();
                for (int i = 0; i < args.length; i++) {
                    message.append(args[i]).append(" ");
                }
                p.sendMessage(ChatColor.LIGHT_PURPLE + "[ Server ] " + message);

            }
        }
        return true;
    }
}
