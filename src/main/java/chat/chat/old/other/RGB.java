package chat.chat.other;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;

import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class RGB {
    public static Component translate(String textToTranslate) {
        Matcher matcher = Pattern.compile("<#\\s*(?:(\\d{1,3})\\s*,?){3}>").matcher(textToTranslate);
        Component component = Component.text(textToTranslate);
        while (matcher.find()) {
            String[] RGB = matcher.group().replace("<#", "").replace(">", "").split(",");
            String text = textToTranslate.substring(0, textToTranslate.indexOf(matcher.group()));
            component = Component.text(text).append(Component.text(textToTranslate.replace(text, "").replace("<#"+RGB[0]+","+RGB[1]+","+RGB[2]+">","")).color(TextColor.color(Integer.parseInt(RGB[0]), Integer.parseInt(RGB[1]), Integer.parseInt(RGB[2]))));


        }
        return component;
    }
}
