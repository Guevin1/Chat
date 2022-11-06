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
import java.util.Random;

public class rand implements CommandExecutor {
    public rand(Chat plugin) {
        //	this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender se,Command command,String label,String[] args) {
        if (se instanceof Player) {
            Player pl = (Player) se;
            File u_file = new File("plugins/Chat/users.yml");
            FileConfiguration config_u = YamlConfiguration.loadConfiguration(u_file);
            if(pl.hasPermission("bc.try")){
                if (!config_u.getBoolean(pl.getName() + ".mute")) {
                    Random rand_num = new Random();
                    int fin_num = rand_num.nextInt(10) + 1;
                    String L = pl.getLocation().getWorld().getName();
                    for (Player p : Bukkit.getWorld(L).getPlayers()) {
                        int me_r = 100;
                        if (p.getLocation().distance(pl.getLocation()) <= me_r) {
                            StringBuilder message = new StringBuilder();
                            String message_try = ChatColor.GREEN + "Успешно";
                            if (fin_num > 5) {
                                message_try = ChatColor.RED + "Провально";
                            }
                            for (int i = 0; i < args.length; i++) {
                                message.append(args[i]).append(" ");
                            }
                            p.sendMessage(ChatColor.GRAY + pl.getName() + " " + message_try + ChatColor.GRAY + " " + message);
                        }
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
