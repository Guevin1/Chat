package chat.chat;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class cmdf {
    private static String message;
    public static String getMessage() {
        return message;
    }
    public static void setMessage(String message) {
        message = message.replaceFirst("! ","").replaceFirst("!","");
    }



    public static String getXYZ(Player name) {
        Player player = name;
        Location location = player.getLocation();
        String world = player.getWorld().getName();
        String xyz = "X: " + location.getBlockX() + " Y: " + location.getBlockY() + " Z: " + location.getBlockZ();
        if(world.equals("world")) {
            xyz = ChatColor.GREEN + xyz + ChatColor.RESET;
        }else if(world.equals("world_nether")) {
            xyz = ChatColor.RED + xyz + ChatColor.RESET;
        }else {
            xyz = ChatColor.DARK_PURPLE + xyz + ChatColor.RESET;
        }
        return xyz;
    }
    public static String hoverPlayer(Player pl){
        String message = ChatColor.GRAY + "Версия: %viaversion_player_protocol_version%";
        message = PlaceholderAPI.setPlaceholders(pl,message);
        return message;
    }
    public static String hoverDebugPlayer(Player pl){
        File u_file = new File("plugins/Chat/users.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(u_file);
        String name = pl.getName();
        String message = ChatColor.GRAY + "Версия: %viaversion_player_protocol_version%\n" +  "spy: " + config.getBoolean(pl.getName() + ".spy") + " \ncolor: "
                + config.getBoolean(name + ".color") + "\nmute: " + config.getBoolean(name + ".mute") + "\nAnom: " + config.getBoolean(name + ".nick.hide")  + "\nPing: " + pl.getPing();
        message = PlaceholderAPI.setPlaceholders(pl,message);
        return message;
    }
    public static String translate(String message) {
        Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");
        Matcher matcher = pattern.matcher(message);
        while (matcher.find()) {
            String hexCode = message.substring(matcher.start(), matcher.end());
            String replaceSharp = hexCode.replace('#', 'x');
            char[] ch = replaceSharp.toCharArray();
            StringBuilder builder = new StringBuilder("");
            for (char c : ch)
                builder.append("&" + c);
            message = message.replace(hexCode, builder.toString());
            matcher = pattern.matcher(message);
        }
        Bukkit.getLogger().info(message);
        String pref = ChatColor.translateAlternateColorCodes('&', message);
        return pref;
    }
}