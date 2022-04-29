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

public class spy implements CommandExecutor {
    public spy(Chat plugin) {
        //	this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender se,Command command,String label,String[] args) {
        if (se instanceof Player) {
            Player p = (Player) se;
            if (se.hasPermission("bc.spy")) {
                File c_file = new File("plugins/Chat/users.yml");
                FileConfiguration config = YamlConfiguration.loadConfiguration(c_file);
                String spy = config.getString(p.getName() + ".spy");
                if (spy != "") {
                    String spy_message = ChatColor.RED + "Выключено";
                    if (spy == "true") {
                        config.set(p.getName() + ".spy", false);
                        Bukkit.getLogger().info(config.getString(p.getName() + ".spy"));
                    } else {
                        config.set(p.getName() + ".spy", true);
                        spy_message = ChatColor.GREEN + "Включено";
                    }
                    se.sendMessage(ChatColor.GRAY + "Прослушивание игроков " + spy_message);
                    Bukkit.getPluginManager().getPlugin("Chat").reloadConfig();

                    try {
                        Bukkit.getLogger().info("Users.yml Saved");
                        config.save(c_file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else {
                    config.set(p.getName() + ".spy", true);
                    se.sendMessage(ChatColor.GRAY + "Прослушивание игроков " + ChatColor.GREEN + "Включено");
                    try {
                        Bukkit.getLogger().info("Users.yml Saved");
                        config.save(c_file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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
