package guevin1.chat;

import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class cmdf {
    public static Component hex(String message){
        String format = "(.*?(?=&#|$))|(&#[a-f,A-F,0-9]{3,6}.*?(&r|$))";
        String formatHEX= "#[a-z,A-Z,0-9]{3,6}";
        Matcher matcher = Pattern.compile(format).matcher(message);
        Component text = Component.text("");
        while (matcher.find()){
            String group = matcher.group();
            Matcher hex = Pattern.compile(formatHEX).matcher(group);
            if (hex.find()) {
                String HEX = hex.group(0);
                group = group.replace(HEX, "").replace("&r", "");
                text = text.append(Component.text(group, TextColor.fromHexString(HEX.replace("&",""))));
            }else {
                group = ChatColor.translateAlternateColorCodes('&',group);
                text = text.append(Component.text(group));
            }
        }
        return text;
    }
    public static String worldColor(World world){
        String name = world.getName();
        String color = "&a";
        if (name.contains("nether")) {
            color = "&c";
        }
        if(name.contains("end")) {
            color = "&d";
        }
        return color;
    }
    public static String textToSub(String message, Player pl){
        String text = message;
        for (String sub : main.message.getStringList("plugin.format.sub.loc.format")) {
            String loc = main.message.getString("plugin.format.sub.loc.message");
            Location location = pl.getLocation();;
            loc = loc.replace(":color-world:",worldColor(pl.getWorld()))
                    .replace(":world:",pl.getWorld().getName())
                    .replace(":x:",String.valueOf(location.getBlockX()))
                    .replace(":y:",String.valueOf(location.getBlockY()))
                    .replace(":z:",String.valueOf(location.getBlockZ()));
            loc = placeholder(pl,loc);
            text = text.replace(sub,loc);
        }
        for (String sub : main.message.getStringList("plugin.format.sub.mentoin.format")) {
            String ment = main.message.getString("plugin.format.sub.mentoin.message");
            for (Player p : Bukkit.getOnlinePlayers()) {
                ment = ment.replace(":name:", p.getName());
                sub = sub.replace(":name:", p.getName());
            }
            if (message.contains(sub)) {
                ment = placeholder(pl,ment);
                text = text.replace(sub, ment);

            }
        }
        return text;
    }
    public static Component HoverPlayer(List<String> list,Player p){
        Component text = Component.text("");
        for(String i : list){
            i = placeholder(p,i);
            i = ChatColor.translateAlternateColorCodes('&', i);
            text = text.append(Component.text(i));
        }
        return text;
    }

    public static Component HoverPlayer(List<String> list,Player p,Player pl){
        Component text = Component.text("");
        for(String i : list){
            i = placeholder(p,i);
            i = ChatColor.translateAlternateColorCodes('&', i);
            i = i.replace(":blocks:",String.valueOf(p.getLocation().distance(pl.getLocation())));
            text = text.append(Component.text(i));

        }
        return text;
    }
    public static String placeholder(Player pl,String message){
        String text = message;
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderApi")){
            text = PlaceholderAPI.setPlaceholders(pl,message);
        }
        return text;
    }
}
