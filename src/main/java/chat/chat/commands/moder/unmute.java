package chat.chat.commands.moder;

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
import java.io.IOException;
import java.lang.management.PlatformLoggingMXBean;

public class unmute implements CommandExecutor {
    public unmute(Chat plugin) {
        //	this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender se,Command command,String label,String[] args) {
        if (se.hasPermission("bc.unmute")) {
            if (args.length == 0){
                se.sendMessage(ChatColor.RED + "Укажите Пользователя");
            }else {
                File c_file = new File("plugins/Chat/users.yml");
                FileConfiguration config = YamlConfiguration.loadConfiguration(c_file);
                if (args[0].equals("@a")) {
                    for (Player p : Bukkit.getOnlinePlayers()){
                        config.set(p.getName() + ".mute", false);
                        se.sendMessage(ChatColor.GRAY + "Человек " + ChatColor.LIGHT_PURPLE + "Размучен");
                    }
                }else {
                    config.set(args[0] + ".mute", false);
                    se.sendMessage(ChatColor.GRAY + "Человек " + ChatColor.LIGHT_PURPLE + "Размучен");
                }
                try {
                    config.save(c_file);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }else {
            se.sendMessage(ChatColor.RED + "Вы не имеете прав");
        }
        return true;
    }
}
