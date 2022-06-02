package chat.chat.commands.moder;

import chat.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class tempmute implements CommandExecutor {
    public tempmute(Chat plugin) {
        //	this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender se,Command command,String label,String[] args) {
        if (se.hasPermission("bc.tempmute")) {
            if (args.length == 0){
                se.sendMessage(ChatColor.RED + "Укажите Пользователя");
            }else if(args.length == 1){
                se.sendMessage(ChatColor.RED + "Укажите время в Секундах");
            }else if(Integer.parseInt(args[1]) <= 60){
                se.sendMessage(ChatColor.RED + "Укажите больше 60 секунд");
            }
            else {
                File c_file = new File("plugins/Chat/users.yml");
                FileConfiguration config = YamlConfiguration.loadConfiguration(c_file);
                config.set(args[0] + ".mute", true);
                try {
                    config.save(c_file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                se.sendMessage(ChatColor.GRAY + "Человек " + ChatColor.DARK_GREEN + "Замучен");
                Bukkit.getScheduler().scheduleSyncDelayedTask(Chat.getInstance(), () -> {
                    config.set(args[0] + ".mute", false);
                    try {
                        config.save(c_file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }, Integer.parseInt(args[1]) * 20);
            }

        }else {
            se.sendMessage(ChatColor.RED + "Вы не имеете прав");
        }
        return true;
    }
}
