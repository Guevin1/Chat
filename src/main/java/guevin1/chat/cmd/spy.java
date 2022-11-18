package guevin1.chat.cmd;

import guevin1.chat.main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class spy implements CommandExecutor {
    public spy(main main) {
    }

    @Override
    public boolean onCommand(CommandSender se, Command command, String label, String[] args) {
        FileConfiguration format = main.message;
        FileConfiguration data = main.data;
        if (se instanceof Player){
            Player p = (Player) se;
            if (args.length == 0){
                Boolean on = false;
                if (!data.getBoolean(p.getName()+".adm.spy")){
                    on = true;
                }
                String message = format.getString("plugin.format.commands.access.spy.text");
                String formatSpy = format.getString("plugin.format.commands.access.spy."+on);
                message = ChatColor.translateAlternateColorCodes('&',message).replace(":spy:",formatSpy);
                se.sendMessage(message);
                data.set(p.getName()+".adm.spy",on);
                main.save(data,main.dataFile);
            }
        }
        return true;
    }
}
