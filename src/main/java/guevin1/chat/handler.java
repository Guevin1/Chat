package guevin1.chat;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.List;

public class handler implements Listener {
    public handler(main main) {
    }
    public Component chat(String messageF, String formatS, Player pl,Player... p ){
        FileConfiguration format = main.message;
        FileConfiguration config = main.config;
        String configS = "";
        if (config.getString("plugin." + formatS + ".prefix") != null){
            configS = config.getString("plugin." + formatS + ".prefix");
        }
        messageF = messageF.replaceFirst(configS,""); // замена "!" на пустоту
        if (messageF.startsWith(" ")){
            messageF = messageF.replaceFirst(" ",""); // убираем пробел после знака "!" если он есть
        }
        messageF = cmdf.textToSub(messageF, pl);
        String dn = PlainTextComponentSerializer.plainText().serialize(pl.displayName());
        String global = cmdf.C2T(format,"plugin.format."+formatS+".message").replace(":name:",pl.getName()).replace(":displayname:",dn)
                .replace(":message:",messageF);
        global = cmdf.placeholder(pl, global);
        List<String> hover = format.getStringList("plugin.format."+formatS+".hover");
        Component HoverText = cmdf.HoverPlayer(hover, pl);
        if(p.length == 1) {
            HoverText = cmdf.HoverPlayerL(hover, pl,p[0]);
        }

        String ClickCommand = cmdf.C2T(format,"plugin.format."+formatS+".click").replace(":name:",pl.getName());
        Component text = Component.text(global);
        if (pl.hasPermission("chatge.color")){

            global = ChatColor.translateAlternateColorCodes('&',global);
            text = cmdf.hex(global);
        }
        text = text
                .hoverEvent(HoverEvent.showText(HoverText))
                .clickEvent(ClickEvent.suggestCommand(ClickCommand));
        return text;
    }
    @EventHandler
    public void onChat(AsyncChatEvent e){
        String messageF = PlainTextComponentSerializer.plainText().serialize(e.message());
        FileConfiguration format = main.message;
        FileConfiguration config = main.config;
        FileConfiguration groups = main.group;
        FileConfiguration data = main.data;
        Player pl = e.getPlayer();
        if (messageF.startsWith(config.getString("plugin.global.prefix"))){ // Писание в глобал с помощью "!"
            e.message(Component.text(messageF.replaceFirst("!","")));
            Component text = chat(messageF, "global",e.getPlayer());
            e.renderer((sender, displayName, message, viewer) -> text);
        }else if (messageF.startsWith(config.getString("plugin.rp.prefix"))) {
            for (Player p : Bukkit.getOnlinePlayers()) {

                Location loc = pl.getLocation();
                if (pl.getWorld().getUID() == p.getWorld().getUID() && loc.distance(p.getLocation()) <= config.getInt("plugin.rp.chat.radius")) {
                    Component text = chat(messageF, "rp", pl,p);
                    p.sendMessage(text);
                } else {
                    if (data.getBoolean(p.getName() + ".adm.spy")) {
                        Component text = chat(messageF, "spy_rp", pl,p);
                        p.sendMessage(text);
                    }
                }
                e.setCancelled(true);
            }
        } else if (messageF.startsWith(config.getString("plugin.group.prefix"))) {
            if (data.getInt(pl.getName() + ".group") != 0){

            }else {

            }
        } else{
            Component text = Component.text("");
            for (Player p : Bukkit.getOnlinePlayers()){

                Location loc = pl.getLocation();
                if (pl.getWorld().getUID() == p.getWorld().getUID() && loc.distance(p.getLocation()) <= config.getInt("plugin.local.radius")){
                    text = chat(messageF,"local",pl,p);
                    p.sendMessage(text);
                }else {
                    if(data.getBoolean(p.getName()+".adm.spy")){
                        text = chat(messageF,"spy_local",pl);
                        p.sendMessage(text);
                    }
                }
                e.setCancelled(true);
            }
            Bukkit.getLogger().info(PlainTextComponentSerializer.plainText().serialize(text));
        }

    }
}
