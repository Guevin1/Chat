package chat.chat.commands.other;

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

public class color implements CommandExecutor {
    public color(Chat plugin) {
        //	this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender se,Command command,String label,String[] args) {
        if (se instanceof Player) {
            Player p = (Player) se;
            if (se.hasPermission("bc.color")) {
                File c_file = new File("plugins/Chat/users.yml");
                FileConfiguration config = YamlConfiguration.loadConfiguration(c_file);
                String c_l = ".color";
                boolean c_b = config.getBoolean(p.getName() + c_l);
                String message = ChatColor.RED + "Выключен";
                if (c_b) {
                    config.set(p.getName() + c_l, false);
                } else {
                    config.set(p.getName() + c_l, true);
                    message = ChatColor.GREEN + "Включен";
                }
                se.sendMessage(ChatColor.GRAY + "Цветной чатик " + message);
                try {
                    config.save(c_file);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }else {
                se.sendMessage(ChatColor.RED + "Вы не имеете прав");
            }
            return true;
        }
        return false;
    }
}
