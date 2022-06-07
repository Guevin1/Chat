package chat.chat;

import io.papermc.paper.event.player.AsyncChatEvent;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.io.File;

import static net.kyori.adventure.text.event.ClickEvent.suggestCommand;

public class Handler implements Listener {

    //private main plugin;
    Handler(Chat plugin) {
        //	this.plugin = plugin;
    }

    public Handler() {

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(AsyncChatEvent e) {

        String MessageOriginal = PlainTextComponentSerializer.plainText().serialize(e.message());
        Bukkit.getLogger().info(MessageOriginal);
        Player pla = e.getPlayer();
        File u_file = new File("plugins/Chat/users.yml");
        FileConfiguration config_u = YamlConfiguration.loadConfiguration(u_file);
        File c_file = new File("plugins/Chat/config.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(c_file);
        if (config_u.getBoolean(pla.getName() + ".mute") == false) {
            if (config_u.getBoolean(pla.getName() + ".color")) {
                MessageOriginal = ChatColor.translateAlternateColorCodes('&',MessageOriginal);
            }
            String xyz = cmdf.getXYZ(pla);
            String L = pla.getLocation().getWorld().getName();

            String IsAnomFormat = "standart";
            if (config_u.getBoolean(pla.getName() + ".nick.hide")) {
                IsAnomFormat = "anom";
            }

            // Local and Global
            String GlobalFormat = config.getString("message.global." + IsAnomFormat).replace("{player}",pla.getName());
            String LocalFormat = config.getString("message.local." + IsAnomFormat).replace("{player}",pla.getName());
            String SpyFormat = config.getString("message.spy.local." + IsAnomFormat).replace("{player}",pla.getName());
            // PlaceholderApi превращение Placeholders в текст
            GlobalFormat = PlaceholderAPI.setPlaceholders(pla, GlobalFormat);
            LocalFormat = PlaceholderAPI.setPlaceholders(pla, LocalFormat);
            SpyFormat = PlaceholderAPI.setPlaceholders(pla, SpyFormat);
            // Chat's format
            Component Global = Component.text(GlobalFormat).hoverEvent(HoverEvent.showText(Component.text(cmdf.hoverPlayer(pla)))).clickEvent(suggestCommand("/w " + pla.getName()));
            Component Local = Component.text(LocalFormat).hoverEvent(HoverEvent.showText(Component.text(cmdf.hoverPlayer(pla)))).clickEvent(suggestCommand("/w " + pla.getName()));
            Component Spy = Component.text(SpyFormat).hoverEvent(HoverEvent.showText(Component.text(cmdf.hoverPlayer(pla)))).clickEvent(suggestCommand("/w " + pla.getName()));

            // mention
            String user_oper = "abco01";
            for (Player p : Bukkit.getOnlinePlayers()) {
                String nick_two = user_oper;
                if (config_u.getString(p.getName() + ".nick.name") != null) {
                    nick_two = config_u.getString(p.getName() + ".nick.name");
                }
                if (MessageOriginal.indexOf(p.getName()) != -1) {

                    user_oper = p.getName();
                    p.playSound(p.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_HIT, 1, 1);
                }if (MessageOriginal.indexOf(nick_two) != -1) {
                    user_oper = nick_two;
                    p.playSound(p.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_HIT, 1, 1);
                }
            }

            // Formation
            String replace = MessageOriginal.replace(":xyz:", xyz).replace(":loc:", xyz);
            Component messageg = Component.text(": " + replace.replaceFirst("! ", "").replaceFirst("!", "").replace(user_oper, ChatColor.GREEN + user_oper + ChatColor.RESET));
            e.message(Component.text(replace.replaceFirst("! ","").replaceFirst("!","")));
            // toggle Global
            boolean global_prefix = MessageOriginal.startsWith("!");
            if (config_u.getBoolean(e.getPlayer().getName() + ".local")){
                global_prefix = !MessageOriginal.startsWith("!");
            }
            // Send's Message
            if (global_prefix) {
                Component finalGlobal = Global;
                e.renderer((sender, displayName, message, viewer) -> finalGlobal.append(messageg));
            } else {
                // Local Message  100 blocks
                e.setCancelled(true);
                String message_viewer = pla.getName();
                for (Player p : Bukkit.getWorld(L).getPlayers()) {
                    int pa = 100;
                    if (p.getLocation().distance(pla.getLocation()) <= pa) {
                        message_viewer = message_viewer + " " + p.getName();
                        if(config_u.getBoolean(p.getName() + ".debug")){
                            p.sendMessage(Local.hoverEvent(Component.text(cmdf.hoverDebugPlayer(pla))).append(messageg));
                        }else {
                            p.sendMessage(Local.append(messageg));
                        }
                    }
                }
                // spy's
                for (Player spy_pl : Bukkit.getOnlinePlayers()) {
                    if (config_u.getBoolean(spy_pl.getName() + ".spy")) {
                        if (message_viewer.indexOf(spy_pl.getName()) == -1) {
                            if(config_u.getBoolean(spy_pl.getName() + ".debug")){
                                spy_pl.sendMessage(Spy.hoverEvent(Component.text(cmdf.hoverDebugPlayer(pla))).append(messageg));
                            }else {
                                spy_pl.sendMessage(Spy.append(messageg));
                            }
                        }
                    }
                }
                Bukkit.getLogger().info(LocalFormat +  ": " + replace);
            }
        } else {
            e.setCancelled(true);
            e.getPlayer().sendMessage(ChatColor.RED + "Вы в муте");
        }
    }

}