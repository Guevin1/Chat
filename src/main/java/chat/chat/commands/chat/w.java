package chat.chat.commands.chat;

import chat.chat.Chat;
import chat.chat.cmdf;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.List;
import java.util.UUID;

import static net.kyori.adventure.text.event.ClickEvent.suggestCommand;

public class w implements CommandExecutor {
    //private Main plugin;
    public w(Chat plugin) {
        //	this.plugin = plugin;

    }
    @Override
    public boolean onCommand(CommandSender se, Command cmd, String arg, String[] args) {
        File Data = new File("plugins/SuperVanish/data.yml");
        FileConfiguration DataConfig = YamlConfiguration.loadConfiguration(Data);
        String user_name = "Server";
        if (se instanceof Player) {
            Player pl = (Player) se;
            user_name = pl.getName();
        }else{
            user_name = "Server";
        }
        if(args.length == 0) {
            se.sendMessage(ChatColor.RED + "Укажите ник");
            return true;
        }else {
            if(args.length == 1) {
                se.sendMessage(ChatColor.RED + "Укажите сообщение");
            }else {

                String message = "";
                for (int i = 1; i < args.length; i++) {
                    message = message + args[i] + " ";
                }
                boolean message_the_player = true;
                for (Player player_list : Bukkit.getOnlinePlayers()) {
                    String UUID = String.valueOf(player_list.getUniqueId());
                    List InvisiblePlayer = DataConfig.getStringList("InvisiblePlayers");
                    // pl.sendMessage("Args: " + args[0] + " Players: " + player_list.getName());
                    if (player_list.getName().equalsIgnoreCase(args[0]) && !InvisiblePlayer.contains(UUID)) {
                        String xyz;
                        if (user_name.equals("Server")){
                            xyz = ChatColor.BLUE + "Кординаты не найдены";
                        }else {
                            xyz = cmdf.getXYZ(Bukkit.getPlayer(user_name));
                        }
                        Component messageP = Component.text(ChatColor.GRAY + "" + ChatColor.ITALIC + " " + message.replace(":xyz:", xyz).replace(":loc:", xyz));
                        Player user = Bukkit.getPlayer(args[0]);
                        Component nameHC;
                        Component namePl;
                        if (user_name.equals("Server")){
                            nameHC = Component.text(ChatColor.GRAY + "" + ChatColor.ITALIC + "> [Личные сообщения с " + user_name + "]").hoverEvent(HoverEvent.showText(Component.text(ChatColor.GRAY + "Это сервер")));
                            namePl = Component.text(ChatColor.GRAY + "" + ChatColor.ITALIC + "< [Личные сообщения с " + user.getName() + "]");
                        }else {
                            nameHC = Component.text(ChatColor.GRAY + "" + ChatColor.ITALIC + "> [Личные сообщения с " + user_name + "]").hoverEvent(HoverEvent.showText(Component.text(cmdf.hoverPlayer(Bukkit.getPlayer(user_name))))).clickEvent(suggestCommand("/w " + user_name));
                            namePl = Component.text(ChatColor.GRAY + "" + ChatColor.ITALIC + "< [Личные сообщения с " + user.getName() + "]").hoverEvent(HoverEvent.showText(Component.text(cmdf.hoverPlayer(Bukkit.getPlayer(args[0]))).clickEvent(suggestCommand("/w " + args[0]))));
                        }
                        se.sendMessage(namePl.append(messageP));
                        user.sendMessage(nameHC.append(messageP));
                        user.playSound(user.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_HIT,1,1);
                        message_the_player = false;
                        for (Player p : Bukkit.getOnlinePlayers()){
                            File c_file = new File("plugins/Chat/users.yml");
                            FileConfiguration config = YamlConfiguration.loadConfiguration(c_file);
                            File Cfile = new File("plugins/Chat/config.yml");
                            FileConfiguration Cconfig = YamlConfiguration.loadConfiguration(Cfile);
                            String SpyFormat = Cconfig.getString("message.spy.pm");
                            SpyFormat = ChatColor.translateAlternateColorCodes('&',SpyFormat);
                            if(config.getString(p.getName() + ".spy") == "true" && user.getName() != p.getName() && user_name != p.getName()){
                                Component spy_user = Component.text(SpyFormat.replace("{PlayerSend}", user_name).replace("{PlayerGets}", user.getName()));
                                p.sendMessage(spy_user.append(messageP));
                            }
                        }
                    }
                }
                if (message_the_player) {
                    se.sendMessage(ChatColor.RED + "Игрок не найден");
                }
            }
            return true;
        }
    }
}