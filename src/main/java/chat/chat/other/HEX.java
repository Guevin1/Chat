package chat.chat.other;

import net.md_5.bungee.api.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HEX {
    public static String translate(String textToTranslate) {
        Matcher matcher = Pattern.compile("<#[A-Fa-f0-9]{6}>").matcher(textToTranslate);
        int hexAmount = 0; //Amount of hexes in message
        while (matcher.find()) {
            matcher.region(matcher.end() - 1, textToTranslate.length());
            hexAmount++;
        }
        int startIndex = 0; //Starting point in the message when looking for hexes.
        for (int hexIndex = 0; hexIndex < hexAmount; hexIndex++) {
            int msgIndex = textToTranslate.indexOf("<#", startIndex);
            String hex = textToTranslate.substring(msgIndex + 1, msgIndex + 8);
            startIndex = msgIndex + 2;
            textToTranslate = textToTranslate.replace("<" + hex + ">", ChatColor.of(hex) + ""); //Replaces the hex code with the color
        }
        return textToTranslate;
    }
}
