package guevin1.chat.cmd;

import guevin1.chat.cmdf;
import guevin1.chat.main;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class mes implements CommandExecutor {
    public mes(main main) {
    }

    @Override
    public boolean onCommand(CommandSender se, Command command, String label, String[] args) {
        FileConfiguration format = main.message;
        if (args.length == 0){
            String message = format.getString("plugin.format.commands.errors.me-arg");
            message = ChatColor.translateAlternateColorCodes('&',message);
            se.sendMessage(message);
        } else {
            if (se instanceof Player){
                String message = "";
                for(int ml = 0;ml <= args.length-1; ml++){
                    message = message + " "+args[ml];
                }
                String formatMessage = "";
                Player p = (Player) se;
                message = cmdf.textToSub(message,p);
                String display = PlainTextComponentSerializer.plainText().serialize(p.displayName());
                for (Player p2 : Bukkit.getOnlinePlayers()) {
                    if (p2.getWorld().getUID() == p.getWorld().getUID() && p.getLocation().distance(p2.getLocation()) <= main.config.getInt("plugin.rp.cmd.me.radius")) {
                        formatMessage = format.getString("plugin.format.commands.access.me.text")
                                .replace(":name:", p.getName())
                                .replace(":displayname:", p.getName())
                                .replace(":message:", message);
                        formatMessage = ChatColor.translateAlternateColorCodes('&', formatMessage);
                        p2.sendMessage(formatMessage);
                    }
                }
                for (Player spy : Bukkit.getOnlinePlayers()) {
                    if (!spy.getName().equals(p.getName())) {
                        if (main.data.getBoolean(spy.getName() + ".adm.spy")) {
                            formatMessage = format.getString("plugin.format.commands.access.me.spy")
                                    .replace(":name:", p.getName())
                                    .replace(":message:", message)
                                    .replace(":displayname:", display);
                            formatMessage = ChatColor.translateAlternateColorCodes('&', formatMessage);
                            spy.sendMessage(formatMessage);

                        }
                    }
                }

            }
        }

        return true;
    }
}
