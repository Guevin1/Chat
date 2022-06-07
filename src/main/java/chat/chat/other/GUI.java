package chat.chat.other;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;

public class GUI {

    public static ItemStack ItemBuilder(Material material, String name){
        Component ItemName = Component.text(ChatColor.translateAlternateColorCodes('&',name));
        ItemStack Item = new ItemStack(material);
        ItemMeta meta = Item.getItemMeta();
        meta.displayName(ItemName);
        Item.setItemMeta(meta);
        return Item;
    }
    public static void OpenGUIChat(Player p){
        File file = new File("plugins/Chat/users.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        String GuiText = ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Chat Gui";
        Component GUITitle = Component.text(GuiText);
        Inventory gui = Bukkit.createInventory(null,9,GUITitle);
        // Item 's
        String PlayerName = p.getName();
        boolean isAnom = config.getBoolean(PlayerName + ".nick.hide");
        boolean isSpy = config.getBoolean(PlayerName + ".spy");
        boolean isDebug = config.getBoolean(PlayerName + ".debug");
        boolean isColor = config.getBoolean(PlayerName + ".color");
        boolean isChange = config.getBoolean(PlayerName + ".local");
        if (p.isOp()){
            if (isAnom && p.hasPermission("BC.hide")) {
                gui.setItem(1,ItemBuilder(Material.GREEN_WOOL,"&aАнонимность включена"));
            } else if (!isAnom && p.hasPermission("BC.hide")) {
                gui.setItem(1,ItemBuilder(Material.RED_WOOL,"&cАнонимность выключена"));
            }

            if (isSpy && p.hasPermission("BC.spy")) {
                gui.setItem(2,ItemBuilder(Material.GREEN_WOOL,"&aSpy включен"));
            } else if (!isSpy && p.hasPermission("BC.spy")) {
                gui.setItem(2,ItemBuilder(Material.RED_WOOL,"&cSpy выключена"));
            }

            if (isDebug && p.hasPermission("BC.debug")) {
                gui.setItem(3,ItemBuilder(Material.GREEN_WOOL,"&aDebug включен"));
            } else if (!isDebug && p.hasPermission("BC.debug")) {
                gui.setItem(3,ItemBuilder(Material.RED_WOOL,"&cDebug выключен"));
            }
        }
        if (isChange && p.hasPermission("BC.change")) {
            gui.setItem(5,ItemBuilder(Material.YELLOW_WOOL,"&6Глобальный чатик"));
        } else if (!isChange && p.hasPermission("BC.change")) {
            gui.setItem(5,ItemBuilder(Material.LIGHT_GRAY_WOOL,"&7Локальный чатик"));
        }
        if (isColor && p.hasPermission("BC.color")) {
            gui.setItem(6,ItemBuilder(Material.GREEN_WOOL,"&aColor chat включен"));
        } else if (!isColor && p.hasPermission("BC.color")) {
            gui.setItem(6,ItemBuilder(Material.RED_WOOL,"&cColor chat выключен"));
        }
        p.openInventory(gui);
    }
}
