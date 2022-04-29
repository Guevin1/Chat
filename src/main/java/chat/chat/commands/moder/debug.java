package chat.chat.commands.moder;

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

public class debug implements CommandExecutor {
    public debug(Chat plugin) {
        //	this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender se,Command command,String label,String[] args) {
        if (se instanceof Player) {
            Player p = (Player) se;
            if (se.hasPermission("bc.hide")) {
                File c_file = new File("plugins/Chat/users.yml");
                FileConfiguration config = YamlConfiguration.loadConfiguration(c_file);
                boolean spy = config.getBoolean(p.getName() + ".debug");
                String message = ChatColor.GREEN + "Включён";
                if (spy) {
                    config.set(p.getName() + ".nick.hide", false);
                } else {
                    config.set(p.getName() + ".nick.hide", true);
                    message = ChatColor.RED + "Выключен";
                }
                se.sendMessage(ChatColor.GRAY + "Дебаг " + message);
                try {
                    config.save(c_file);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }else {
                se.sendMessage(ChatColor.RED + "Вы не имеете прав");
            }
            return true;
        }else {
            se.sendMessage("Ты сервер ты и так читаешь всё");
        }
        return false;
    }
}
