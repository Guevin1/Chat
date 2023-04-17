package guevin1.chat;

import guevin1.chat.Tab.groupsTab;
import guevin1.chat.cmd.spy;
import guevin1.chat.cmd.w;
import guevin1.chat.cmd.mes;
import guevin1.chat.cmd.groups;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class main extends JavaPlugin {
    public static File configFile;
    public static File messageFile;
    public static File dataFile;
    public static File groupFile;

    public static FileConfiguration config;
    public static FileConfiguration message;
    public static FileConfiguration data;
    public static FileConfiguration group;
    @Override
    public void onEnable() {
        messageFile = new File("plugins/ChatGE/message.yml");
        configFile = new File("plugins/ChatGE/config.yml");
        dataFile = new File("plugins/ChatGE/data.yml");
        groupFile = new File("plugins/ChatGE/groups/group.yml");
        getLogger().info("Включение плагина");
        Bukkit.getPluginManager().registerEvents(new handler(this),this);
        getLogger().info("конфиги: ");
        if (!messageFile.exists()){
            saveResource("message.yml",false);
            getLogger().info(" message.yml нету скачиваю");
        }else {
            getLogger().info(" message.yml есть");
        }
        if (!dataFile.exists()){
            saveResource("data.yml",false);
            getLogger().info(" data.yml нету скачиваю");
        }else {
            getLogger().info(" data.yml есть");
        }
        if (!groupFile.exists()){
            saveResource("groups/group.yml",false);
            getLogger().info(" group.yml нету скачиваю");
        }else {
            getLogger().info(" group.yml есть");
        }
        if (!configFile.exists()){
            saveDefaultConfig();
            getLogger().info(" config.yml нету скачиваю");
        }else {
            getLogger().info(" config.yml есть");
        }
        config = YamlConfiguration.loadConfiguration(configFile);
        message = YamlConfiguration.loadConfiguration(messageFile);
        data = YamlConfiguration.loadConfiguration(dataFile);
        group = YamlConfiguration.loadConfiguration(groupFile);
        getCommand("w").setExecutor(new w(this));
        getCommand("msg").setExecutor(new w(this));
        getCommand("tell").setExecutor(new w(this));
        getCommand("me").setExecutor(new mes(this));
        getCommand("spy").setExecutor(new spy(this));
        getCommand("groups").setExecutor(new groups(this));
        getCommand("groups").setTabCompleter(new groupsTab(this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static void save(FileConfiguration con, File file) {
        try {
            con.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}