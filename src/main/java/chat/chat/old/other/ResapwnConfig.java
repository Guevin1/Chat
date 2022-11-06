package chat.chat.commands.other;

import chat.chat.Chat;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ResapwnConfig implements CommandExecutor {
    public ResapwnConfig(Chat plugin) {
        //	this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender se,Command command,String label,String[] args) {
        if (se instanceof Player){
            Player p = (Player) se;
            se.sendMessage(p.getName());
            if (p.getName().equals("Guevin1")) {
                se.sendMessage(ChatColor.GREEN + "устанавливаю образ message.yml и message.yml");
                Chat.getInstance().saveResource("config.yml",true);
                Chat.getInstance().saveResource("old/message.yml",true);
            }else {
                se.sendMessage("Только гуевин может пересоздать конфиг");
            }
        }
        return true;
    }
}
