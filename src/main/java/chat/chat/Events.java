package chat.chat;

import chat.chat.other.GUI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.List;
import java.util.Random;

import static net.kyori.adventure.text.event.ClickEvent.suggestCommand;

public class Events implements Listener {
    public String message = "test";
    //private main plugin;
    Events(Chat chat) {
        //	this.plugin = plugin;
    }

    @EventHandler
    public void onChat(PlayerJoinEvent e) {
        File u_file = new File("plugins/Chat/message.yml");
        FileConfiguration config_m = YamlConfiguration.loadConfiguration(u_file);
        List join = config_m.getList("join_to");
        Random rand = new Random();
        int rand_int = rand.nextInt(join.size());
        String final_join = join.get(rand_int).toString();
        final_join = final_join.replace("<player>", e.getPlayer().getName());
        final_join = ChatColor.translateAlternateColorCodes('&',final_join);
        Player p = e.getPlayer();
        Component text = Component.text(ChatColor.YELLOW + final_join);
        text = text.hoverEvent(HoverEvent.showText(Component.text(cmdf.hoverPlayer(p)))).clickEvent(suggestCommand("/w " + p.getName()));
        e.joinMessage(text);
    }
    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        FileConfiguration config = Chat.getInstance().getConfig();
        File u_file = new File("plugins/Chat/message.yml");
        FileConfiguration config_m = YamlConfiguration.loadConfiguration(u_file);
        if (config.getBoolean("options.deaths")) {
            Component death_original = e.deathMessage();
            Player p = e.getPlayer();
            List death_message_no = config_m.getList("deaths_player");
            List death_message_pl = config_m.getList("deaths_from_player");
            Random rand = new Random();

            int rand_int = rand.nextInt(death_message_no.size());
            int rand_int_pl = rand.nextInt(death_message_pl.size());
            String final_death = (String) death_message_no.get(rand_int);
            if (p.getKiller() != null) {
                final_death = death_message_pl.get(rand_int_pl).toString().replace("<killer>",p.getKiller().getName());
            }
            final_death = final_death.replace("<player>",p.getName());
            final_death = ChatColor.translateAlternateColorCodes('&',final_death);
            Component text = Component.text(ChatColor.RED + final_death).hoverEvent(death_original);
            e.deathMessage(text);
            String death = PlainTextComponentSerializer.plainText().serialize(death_original);
            Bukkit.getLogger().info(death);
        }
    }
    /*
    @EventHandler
    public void onInventory(InventoryClickEvent e) {
        String InventoryTitle = PlainTextComponentSerializer.plainText().serialize(e.getView().title());
        if(InventoryTitle.contains("Chat Gui")){
            ItemStack ItemClicked = e.getCurrentItem();
            if (ItemClicked != null) {
                String ItemName = PlainTextComponentSerializer.plainText().serialize(ItemClicked.getItemMeta().displayName());

                Inventory gui = e.getInventory();
                if (ItemName == ChatColor.RED + "" + ChatColor.BOLD + "Выход") {
                    e.getClickedInventory().clear();
                }
                Player p = (Player) e.getWhoClicked();

                File Ufile = new File("plugins/Chat/users.yml");
                FileConfiguration config = YamlConfiguration.loadConfiguration(Ufile);
                String[] NameTwoItem = {"Анониность","Spy","Debug","Color chat"};
                int[] index = new int[]{1, 2, 3, 6};
                String[] ItemPath = {".nick.hide",".spy",".debug",".color"};
                String[] on = {"включена","включен","включен","включен"};
                String[] off = {"выключена","выключен","выключен","выключен"};
                for (int i = 0;i <=3;i++){
                    if (ItemName.contains(NameTwoItem[i] + " " + off[i])) {
                        gui.setItem(index[i], GUI.ItemBuilder(Material.GREEN_WOOL, "&a" + NameTwoItem[i] + " " + on[i]));
                        config.set(p.getName() + ItemPath[i], false);
                        p.sendMessage(ChatColor.GREEN + NameTwoItem[i] + " " + on[i]);
                    } else if (ItemName.contains(NameTwoItem[i] + " " + on[i])) {
                        gui.setItem(index[i], GUI.ItemBuilder(Material.RED_WOOL, "&c" + NameTwoItem[i] + " " + off[i]));
                        config.set(p.getName() + ItemPath[i], true);
                        p.sendMessage(ChatColor.RED + NameTwoItem[i] + " " + off[i]);
                    }
                    try {
                        config.save(Ufile);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    e.setCancelled(true);

                }
                if (ItemName.contains("Глобальный чатик")) {
                    gui.setItem(5, GUI.ItemBuilder(Material.LIGHT_GRAY_WOOL, "&7Локальный чатик"));
                    config.set(p.getName() + ".local", false);
                    p.sendMessage(ChatColor.GRAY + "Локальный чатик");
                } else if (ItemName.contains("Локальный чатик")) {
                    gui.setItem(5, GUI.ItemBuilder(Material.YELLOW_WOOL, "&6Глобальный чатик"));
                    config.set(p.getName() + ".local", true);
                    p.sendMessage(ChatColor.GOLD + "Глобальный чатик");
                }
                try {
                    config.save(Ufile);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                e.setCancelled(true);
            }
        }

    }

    public void ItemSet(Player p, Inventory gui,int index, String ItemName, String ItemNameTwo, String ItemPath,String On,String Off) {
        File Ufile = new File("plugins/Chat/users.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(Ufile);
        if (ItemName.contains(ItemNameTwo + " " + On)){
            gui.setItem(index, GUI.ItemBuilder(Material.RED_WOOL,"&c" + ItemNameTwo +" " + Off));
            config.set(p.getName() + ItemPath,false);
            p.sendMessage(ChatColor.RED + ItemNameTwo + " " + Off);
        } else if (ItemName.contains(ItemNameTwo + " " + Off)) {
            gui.setItem(index, GUI.ItemBuilder(Material.GREEN_WOOL,"&a" + ItemNameTwo +" " + On));
            config.set(p.getName() + ItemPath,true);
            p.sendMessage(ChatColor.GREEN + ItemNameTwo + " " + On);
        }
        try {
            config.save(Ufile);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
     */
}