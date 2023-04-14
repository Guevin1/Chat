package guevin1.chat;

import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.bukkit.Sound.valueOf;

public class cmdf {
    public static Component hex(String message){
        String format = "&#[0-9A-Za-z]{6}.*?(?=&)|.*?(?=&#|!=&r|$)";
        String formatHEX= "&?#[a-z,A-Z,0-9]{3,6}";
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
                String mentc = ment.replace(":name:", p.getName());
                String subc = sub.replace(":name:", p.getName());
                Location loc = p.getLocation();
                Bukkit.getLogger().info(subc);
                if (message.contains(subc)) {
                    ment =ChatColor.translateAlternateColorCodes('&',placeholder(pl,mentc));
                    String soC = main.message.getString("plugin.format.sub.mentoin.sound.name");
                    Float svC = (float) main.message.getDouble("plugin.format.sub.mentoin.sound.volume");
                    Float sv = (svC == null) ? 0.5f : svC;
                    Sound so = (soC == null) ? Sound.valueOf("block_bell_use".toUpperCase()) : Sound.valueOf(soC.toUpperCase());

                    p.playSound(loc, so,0.1f, 0.1f);
                    text = text.replace(subc, ment);

                }
            }

        }
        return text;
    }
    public static Component HoverPlayer(List<String> list, Player p){
        Component text = Component.text("");
        for(String i : list){
            i = placeholder(p,i);
            i = ChatColor.translateAlternateColorCodes('&', i);
            text = text.append(Component.text(i));
        }
        return text;
    }

    public static Component HoverPlayerL(List<String> list,Player p,Player pl){
        Component text = Component.text("");
        for(String i : list){
            i = placeholder(p,i);
            i = ChatColor.translateAlternateColorCodes('&', i);
            String y = String.format("%.1f",p.getLocation().distance(pl.getLocation()));
            if(pl.getWorld().getUID() != p.getWorld().getUID()){
                i = "??";
            }
            i = i.replace(":blocks:",y);
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
    public static String C2T(FileConfiguration config, String path){
        Object textC = config.get(path);
        String text = "";

        if (textC != null) {
            if (textC instanceof String) {
                text = ChatColor.translateAlternateColorCodes('&', textC.toString());

            } else if (textC instanceof List) {
                for (Object run : ((List<?>) textC).toArray()) {
                    text = text + run.toString()+"\n";
                    Bukkit.getLogger().info(run.toString());
                }

                text = ChatColor.translateAlternateColorCodes('&', text);

            }
        }else {
            Bukkit.getLogger().info(path);
            text = ChatColor.RED + "NULL";
        }
        return text;
    }
    public static String SA2S(int index,String[] args){
        String message = "";
        for(int ml = index;ml <= args.length-1; ml++){
            message = message + args[ml] + " ";
        }
        return message;
    }
    public static void ce(Object i){
        Bukkit.getLogger().info(i.toString());
    }
}
