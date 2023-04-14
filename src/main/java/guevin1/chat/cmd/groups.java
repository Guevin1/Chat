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

import java.util.List;

public class groups implements CommandExecutor {
    public groups(main main) {
    }

    @Override
    public boolean onCommand(CommandSender se, Command command, String label, String[] args) {
        FileConfiguration format = main.message;
        FileConfiguration data = main.data;
        FileConfiguration groups = main.group;
        if (se instanceof Player){
            Player p = (Player) se;
            if (args.length == 0){
                se.sendMessage(cmdf.C2T(format,"plugin.format.groups.help"));
            }else {
                switch (args[0]){
                    case("list"):
                        String listG = "";
                        for (String key :groups.getKeys(true)) {
                            if(key.contains(".name")){
                                String nameG = cmdf.C2T(format,"plugin.format.sub.format.groups-l").replace(":nameG:",cmdf.C2T(groups,key));
                                nameG = ChatColor.translateAlternateColorCodes('&',nameG);
                                listG = listG + nameG + "\n";
                            }
                        }
                        String text = cmdf.C2T(format,"plugin.format.groups.sub.list").replace(":groups:",listG);
                        se.sendMessage(text);
                        break;
                    case("leave"):
                        for(String idG : groups.getKeys(false)){
                            String name = cmdf.SA2S(1,args);
                            String nameG = groups.getString(idG+".name") + " ";
                            if (nameG.equals(name)){
                                if (groups.getStringList(idG+".players").contains(p.getName())){
                                    if (!groups.getString(idG+".owner").equals(p.getName())) {
                                        se.sendMessage(cmdf.C2T(format, "plugin.format.groups.accept.group-leave").replace(":nameG:", nameG));
                                        List<String> plg = groups.getStringList(idG + ".players");
                                        plg.remove(plg.indexOf(p.getName()));

                                        cmdf.ce(plg);
                                        groups.set(idG + ".players", plg);
                                        main.save(groups, main.groupFile);
                                    }else {
                                        se.sendMessage(cmdf.C2T(format,"plugin.format.groups.errors.group-owner-l").replace(":nameG:",nameG));
                                    }
                                }else {
                                    se.sendMessage(cmdf.C2T(format,"plugin.format.groups.errors.group-not").replace(":nameG:",nameG));
                                }
                            }
                        }
                        break;
                }
            }
        }
        return true;
    }
}
