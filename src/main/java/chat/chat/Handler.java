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

        String message_original = PlainTextComponentSerializer.plainText().serialize(e.message());

        Player pla = e.getPlayer();
        File u_file = new File("plugins/Chat/users.yml");
        FileConfiguration config_u = YamlConfiguration.loadConfiguration(u_file);
        File c_file = new File("plugins/Chat/config.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(c_file);
        if (config_u.getBoolean(pla.getName() + ".mute") == false) {
            String prefix = config.getString("options.prefix");
            String suffix = config.getString("options.suffix");
            if (config_u.getBoolean(pla.getName() + ".color")) {
                message_original = ChatColor.translateAlternateColorCodes('&',message_original);
            }
            String xyz = cmdf.getXYZ(pla);
            String L = pla.getLocation().getWorld().getName();
            String name = ChatColor.GRAY + prefix + ChatColor.GRAY + ChatColor.RESET + pla.getName() + suffix + ChatColor.GRAY;
            name = PlaceholderAPI.setPlaceholders(pla, name);

            // Chat's format
            Component global = Component.text(ChatColor.GOLD + "G | " + ChatColor.RESET);
            Component local = Component.text(ChatColor.GRAY + "L | " + ChatColor.RESET);

            // name's
            Component nameHC = Component.text(name).hoverEvent(HoverEvent.showText(Component.text(cmdf.hoverPlayer(pla)))).clickEvent(suggestCommand("/w " + pla.getName()));
            if (config_u.getBoolean(pla.getName() + ".nick.hide")){
                nameHC = Component.text(PlaceholderAPI.setPlaceholders(pla,prefix));

            }

            String user_oper = "abco01";
            for (Player p : Bukkit.getOnlinePlayers()) {
                String nick_two = user_oper;
                if (config_u.getString(p.getName() + ".nick.name") != null) {
                    nick_two = config_u.getString(p.getName() + ".nick.name");
                }
                if (message_original.indexOf(p.getName()) != -1) {

                    user_oper = p.getName();
                    p.playSound(p.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_HIT, 1, 1);
                }if (message_original.indexOf(nick_two) != -1) {
                    user_oper = nick_two;
                    p.playSound(p.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_HIT, 1, 1);
                }
            }


            String replace = message_original.replace(":xyz:", xyz).replace(":loc:", xyz);
            Component messageg = Component.text(": " + replace.replaceFirst("! ", "").replaceFirst("!", "").replace(user_oper, ChatColor.GREEN + user_oper + ChatColor.RESET));
            e.message(Component.text(message_original.replaceFirst("! ","").replaceFirst("!","")));
            boolean global_prefix = message_original.startsWith("!");
            if (config_u.getBoolean(e.getPlayer().getName() + ".local")){
                global_prefix = !message_original.startsWith("!");
            }
            if (global_prefix) {
                Component finalNameHC = nameHC;
                e.renderer((sender, displayName, message, viewer) -> global.append(finalNameHC).append(messageg));
            } else {
                e.setCancelled(true);
                String message_viewer = pla.getName();
                for (Player p : Bukkit.getWorld(L).getPlayers()) {
                    int pa = 100;
                    if (p.getLocation().distance(pla.getLocation()) <= pa) {
                        message_viewer = message_viewer + " " + p.getName();
                        if(config_u.getBoolean(p.getName() + ".debug")){
                            p.sendMessage(local.append(nameHC.hoverEvent(Component.text(cmdf.hoverDebugPlayer(pla)))).append(messageg));
                        }else {
                            p.sendMessage(local.append(nameHC).append(messageg));
                        }
                    }
                }
                // spy's
                for (Player spy_pl : Bukkit.getOnlinePlayers()) {
                    if (config_u.getString(spy_pl.getName() + ".spy") == "true") {
                        if (message_viewer.indexOf(spy_pl.getName()) == -1) {
                            Component spy_user = Component.text(ChatColor.GRAY + "" + ChatColor.ITALIC + "[L] ");
                            if(config_u.getBoolean(spy_pl.getName() + ".debug")){
                                spy_pl.sendMessage(spy_user.append(nameHC.hoverEvent(Component.text(cmdf.hoverDebugPlayer(pla)))).append(messageg));
                            }else {
                                spy_pl.sendMessage(spy_user.append(nameHC).append(messageg));
                            }
                        }
                    }
                }
                Bukkit.getLogger().info("L | " + name + ": " + replace);
            }
        } else {
            e.setCancelled(true);
            e.getPlayer().sendMessage(ChatColor.RED + "Вы в муте");
        }
    }

}