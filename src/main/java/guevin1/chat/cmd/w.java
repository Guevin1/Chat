package guevin1.chat.cmd;

import guevin1.chat.cmdf;
import guevin1.chat.main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class w implements CommandExecutor {
    public w(main main) {
    }

    @Override
    public boolean onCommand(CommandSender se, Command command, String label, String[] args) {
        FileConfiguration format = main.message;
        if (args.length == 0){
            String message = format.getString("plugin.format.commands.errors.w.arg-name");
            message = ChatColor.translateAlternateColorCodes('&',message);
            se.sendMessage(message);
        } else if (args.length == 1) {
            String message = format.getString("plugin.format.commands.errors.w.arg-message");
            message = ChatColor.translateAlternateColorCodes('&',message);
            se.sendMessage(message);
        }else {
            Boolean isPlayer = false;
            for(Player p : Bukkit.getOnlinePlayers()){
                if(p.getName().toLowerCase().equals(args[0].toLowerCase())){ isPlayer = true;}
            }
            if (isPlayer){
                Player p2 = Bukkit.getPlayer(args[0]);
                String message = "";
                for(int ml = 1;ml <= args.length-1; ml++){
                    message = message + " "+args[ml];
                }
                if (se instanceof Player){
                    String formatMessage = "";
                    Player p = (Player) se;
                    message = cmdf.textToSub(message,p);
                    formatMessage = format.getString("plugin.format.commands.access.w.to").replace(":name:",p2.getName()).replace(":message:",message);
                    formatMessage = ChatColor.translateAlternateColorCodes('&', formatMessage);
                    p.sendMessage(formatMessage);
                    formatMessage = format.getString("plugin.format.commands.access.w.from").replace(":name:",p.getName()).replace(":message:",message);
                    formatMessage = ChatColor.translateAlternateColorCodes('&', formatMessage);
                    p2.sendMessage(formatMessage);
                    for (Player spy : Bukkit.getOnlinePlayers()){
                        if(main.data.getBoolean(spy.getName()+".adm.spy") && !spy.getName().equals(p.getName()) && !spy.getName().equals(p2.getName())){
                            formatMessage = format.getString("plugin.format.commands.access.w.spy")
                                    .replace(":name:",p.getName())
                                    .replace(":name2:",p2.getName())
                                    .replace(":message:",message);
                            formatMessage = ChatColor.translateAlternateColorCodes('&', formatMessage);
                            spy.sendMessage(formatMessage);
                        }
                    }
                }else {

                }
            }else {

                String message = format.getString("plugin.format.commands.errors.w.arg-online");
                message = ChatColor.translateAlternateColorCodes('&',message);
                se.sendMessage(message);
            }
        }
        return true;
    }
}
