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

import java.util.ArrayList;
import java.util.Arrays;
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
                se.sendMessage(cmdf.C2T(format,"plugin.format.groups.sub.help"));
            }else {
                switch (args[0]){
                    case("list"):
                        if (se.hasPermission("chatge.groups.list")) {
                            String listG = "";
                            for (String key : groups.getKeys(true)) {
                                if (key.contains(".name") ) {
                                    String nameG = cmdf.C2T(format, "plugin.format.sub.format.groups-l").replace(":nameG:", cmdf.C2T(groups, key));
                                    nameG = ChatColor.translateAlternateColorCodes('&', nameG);
                                    listG = listG + nameG + "\n";
                                }
                            }
                            String text = cmdf.C2T(format, "plugin.format.groups.sub.list").replace(":groups:", listG);
                            se.sendMessage(text);
                        }else {
                            se.sendMessage(cmdf.C2T(format,"plugin.format.groups.errors.perm"));
                        }
                        break;
                    case("join"):
                        if (se.hasPermission("chatge.groups.join")) {
                            for (String idG : groups.getKeys(false)){
                                String name = cmdf.SA2S(1, args);
                                String nameG = groups.getString(idG + ".name") + " ";
                                if (nameG.equals(name)){
                                    if (!groups.getBoolean(idG+".settings.closed")) {
                                        if(!groups.getStringList(idG+".players").contains(p.getName())){
                                            se.sendMessage(cmdf.C2T(format, "plugin.format.groups.accept.group-join").replace(":nameG:", nameG));
                                            List<String> plg = groups.getStringList(idG + ".players");
                                            plg.add(p.getName());
                                            groups.set(idG + ".players", plg);

                                            data.set(p.getName()+".group",idG);
                                            main.save(data,main.dataFile);
                                            main.save(groups, main.groupFile);
                                        }else  {
                                            se.sendMessage(cmdf.C2T(format, "plugin.format.groups.errors.group-join-cancel").replace(":nameG:", nameG));
                                        }
                                    }else {
                                        if (!groups.getBoolean(idG+".settings.ticket")){
                                            se.sendMessage(cmdf.C2T(format, "plugin.format.groups.errors.group-join-no-ticket").replace(":nameG:", nameG));
                                        }else{
                                            se.sendMessage(cmdf.C2T(format, "plugin.format.groups.errors.group-join-ticket").replace(":nameG:", nameG));

                                        }

                                    }
                                }
                            }
                        }else {
                            se.sendMessage(cmdf.C2T(format,"plugin.format.groups.errors.perm"));
                        }
                        break;
                    case("chat"):
                        if (se.hasPermission("chatge.groups.chat")){
                            String groupActive = data.getString(p.getName()+".group");
                            if (args.length == 0){
                                se.sendMessage(cmdf.C2T(format, "plugin.format.groups.errors.group-chat-notext"));
                            }else {
                                String idGS = "";
                                String message = cmdf.SA2S(1, args);
                                for (String idG : groups.getKeys(false)) {
                                    String nameG = "["+groups.getString(idG + ".name")+"]";
                                    if (message.startsWith(nameG) && groups.getStringList(idG+".players").contains(p.getName())) {
                                        cmdf.ce(nameG);
                                        idGS = idG;

                                    }

                                }
                                if (groupActive != null && idGS.equals("")) {

                                    idGS = groupActive;
                                    String nameG = groups.getString(groupActive+".name");
                                    message = message.replace("["+nameG+"] ", "");

                                }
                                cmdf.ce(idGS);
                                groupActive = groups.getString(idGS+".name");
                                message = message.replace( "["+groupActive+"] ","");
                                if (idGS.equals("")){
                                    se.sendMessage(cmdf.C2T(format, "plugin.format.groups.errors.group-chat-noactive"));
                                }else {
                                    List<String> pl = groups.getStringList(idGS + ".players");
                                    List<String> vp = new ArrayList<>();
                                    for(String pla : pl){
                                        if(Bukkit.getOnlinePlayers().toString().contains(pla)){
                                            vp.add(pla);
                                        }
                                    }
                                    cmdf.ce(vp);
                                    for (String pla : vp) {
                                        Player player = Bukkit.getPlayer(pla);
                                        if (player.isOnline()){
                                            String nameG = groups.getString(idGS+".name");
                                            String formatMessage = cmdf.C2T(format,"plugin.format.groups.sub.chat.message").replace(":message:",message).replace(":name:",p.getName()).replace(":nameG:",nameG).replace(":idG:",idGS);
                                            if (se.hasPermission("chatge.color")){
                                                formatMessage = ChatColor.translateAlternateColorCodes('&',formatMessage);
                                            }

                                            Component HoverText = Component.text("");
                                            for(String i : format.getStringList("plugin.format.groups.sub.chat.hover")){
                                                i = cmdf.placeholder(p,i);
                                                i = ChatColor.translateAlternateColorCodes('&', i);
                                                String[] vps = vp.toArray(new String[vp.size()]);
                                                i = i.replace(":viewers:",cmdf.SA2S(0, vps)).replace(":nameG:",nameG).replace(":idG:",idGS);
                                                HoverText = HoverText.append(Component.text(i));
                                            }
                                            String ClickCommand = cmdf.C2T(format,"plugin.format.groups.sub.chat.click").replace(":nameG:",nameG).replace(":name:",p.getName());
                                            Component textF = Component.text(formatMessage).hoverEvent(HoverEvent.showText(HoverText)).clickEvent(ClickEvent.suggestCommand(ClickCommand));

                                            player.sendMessage(textF);
                                        };
                                    }
                                    groupActive = idGS;
                                    data.set(p.getName()+".group",groupActive);
                                    main.save(data,main.dataFile);
                                }
                            }
                        }
                        break;
                    case("create"):
                        if (se.hasPermission("chatge.groups.create")){

                        }
                    case("leave"):
                        if (se.hasPermission("chatge.groups.list")) {
                            for (String idG : groups.getKeys(false)) {
                                String name = cmdf.SA2S(1, args);
                                String nameG = groups.getString(idG + ".name") + " ";
                                if (nameG.equals(name)) {
                                    if (groups.getStringList(idG + ".players").contains(p.getName())) {
                                        if (!groups.getString(idG + ".owner").equals(p.getName())) {
                                            se.sendMessage(cmdf.C2T(format, "plugin.format.groups.accept.group-leave").replace(":nameG:", nameG));
                                            List<String> plg = groups.getStringList(idG + ".players");
                                            plg.remove(plg.indexOf(p.getName()));

                                            cmdf.ce(plg);
                                            groups.set(idG + ".players", plg);
                                            main.save(groups, main.groupFile);
                                            data.set(p.getName()+".group","");
                                            main.save(data,main.dataFile);
                                        } else {
                                            se.sendMessage(cmdf.C2T(format, "plugin.format.groups.errors.group-owner-l").replace(":nameG:", nameG));
                                        }
                                    } else {
                                        se.sendMessage(cmdf.C2T(format, "plugin.format.groups.errors.group-not").replace(":nameG:", nameG));
                                    }
                                }
                            }
                        }else {
                            se.sendMessage(cmdf.C2T(format,"plugin.format.groups.errors.perm"));
                        }
                        break;
                }
            }
        }
        return true;
    }
}
