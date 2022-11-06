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
import java.util.ArrayList;
import java.util.List;

public class block implements CommandExecutor {
    public block(Chat plugin) {
        //	this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender se,Command command,String label,String[] args) {
        if (se instanceof Player) {
            Player p = (Player) se;
            if (se.hasPermission("bc.block") && args.length > 0) {
                File c_file = new File("plugins/Chat/users.yml");
                FileConfiguration config = YamlConfiguration.loadConfiguration(c_file);
                List BlockedUser = config.getList(p.getName() + ".blocked");
                if (BlockedUser != null) {
                    BlockedUser.add(args[0]);
                }else {
                    BlockedUser = new ArrayList();
                    BlockedUser.add(args[0]);
                }
                config.set(p.getName() + ".blocked", BlockedUser);
                try {
                    config.save(c_file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                se.sendMessage(ChatColor.RED + "Вы заблокировали " + args[0]);
            }else {
                se.sendMessage(ChatColor.RED + "Вы не имеете прав либо напишите ник");
            }
            return true;
        }else {
            se.sendMessage("Ты сервер ты и так читаешь всё");
        }
        return false;
    }
}
