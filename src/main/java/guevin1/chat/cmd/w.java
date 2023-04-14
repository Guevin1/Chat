package guevin1.chat.cmd;

import guevin1.chat.cmdf;
import guevin1.chat.main;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
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
            String message = cmdf.C2T(format,"plugin.format.commands.errors.w.arg-name");
            se.sendMessage(message);
        } else if (args.length == 1) {
            String message = cmdf.C2T(format,"plugin.format.commands.errors.w.arg-message");
            se.sendMessage(message);
        }else {
            Boolean isPlayer = false;
            for(Player p : Bukkit.getOnlinePlayers()){
                if(p.getName().toLowerCase().equals(args[0].toLowerCase())){ isPlayer = true;}
            }
            if (isPlayer){
                Player p2 = Bukkit.getPlayer(args[0]);
                String message = cmdf.SA2S(1,args);
                if (se instanceof Player){
                    String formatMessage = "";
                    Player p = (Player) se;
                    message = cmdf.textToSub(message,p);

                    formatMessage = cmdf.C2T(format,"plugin.format.commands.access.w.to").replace(":name:",p2.getName()).replace(":message:",message);
                    String ClickCommand = cmdf.C2T(format,"plugin.format.commands.access.w.click").replace(":name:",p2.getName());
                    Component text = Component.text(formatMessage)
                            .clickEvent(ClickEvent.suggestCommand(ClickCommand));
                    p.sendMessage(text);
                    formatMessage = cmdf.C2T(format,"plugin.format.commands.access.w.from").replace(":name:",p.getName()).replace(":message:",message);
                    if (se.hasPermission("chatge.color")){
                        formatMessage = ChatColor.translateAlternateColorCodes('&',formatMessage);
                    }
                    ClickCommand = cmdf.C2T(format,"plugin.format.commands.access.w.click").replace(":name:",p.getName());
                    text = Component.text(formatMessage)
                            .clickEvent(ClickEvent.suggestCommand(ClickCommand));
                    p2.sendMessage(text);
                    for (Player spy : Bukkit.getOnlinePlayers()){
                        if(main.data.getBoolean(spy.getName()+".adm.spy") && !spy.getName().equals(p.getName()) && !spy.getName().equals(p2.getName())){
                            formatMessage = cmdf.C2T(format,"plugin.format.commands.access.w.spy")
                                    .replace(":name:",p.getName())
                                    .replace(":name2:",p2.getName())
                                    .replace(":message:",message);
                            spy.sendMessage(formatMessage);
                        }
                    }
                }else {

                }
            }else {

                String message = cmdf.C2T(format,"plugin.format.commands.errors.w.arg-online");
                se.sendMessage(message);
            }
        }
        return true;
    }
}
