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

public class local implements CommandExecutor {
    public local(Chat plugin) {
        //	this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender se,Command command,String label,String[] args) {
        if (se instanceof Player) {
            Player p = (Player) se;
            if (se.hasPermission("bc.local")) {
                File c_file = new File("plugins/Chat/users.yml");
                FileConfiguration config = YamlConfiguration.loadConfiguration(c_file);
                String spy = config.getString(p.getName() + ".local");
                String spy_message = "Локальном";
                if (spy == "true") {
                    config.set(p.getName() + ".local", false);
                } else {
                    config.set(p.getName() + ".local", true);
                    spy_message = ChatColor.GOLD + "Глобальном";
                }
                se.sendMessage(ChatColor.GRAY + "Вы теперь общаетесь в " + spy_message);
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
