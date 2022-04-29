package chat.chat.commands.nick;

import chat.chat.Chat;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class name implements CommandExecutor {
    public name(Chat plugin) {
        //	this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender se,Command command,String label,String[] args) {
        if (se instanceof Player) {
            Player p = (Player) se;
            if (args.length == 1) {
                if (se.hasPermission("bc.name")) {
                    File c_file = new File("plugins/Chat/users.yml");
                    FileConfiguration config = YamlConfiguration.loadConfiguration(c_file);
                    se.sendMessage(ChatColor.GRAY + "Ваше второе имя " + args[0]);
                    config.set(p.getName() + ".nick.name", "" + args[0] + "");
                    try {
                        config.save(c_file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    se.sendMessage(ChatColor.RED + "Вы не имеете прав");
                }
            }

        }
        return true;
    }
}
