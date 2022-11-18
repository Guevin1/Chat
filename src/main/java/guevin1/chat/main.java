package guevin1.chat;

import guevin1.chat.cmd.spy;
import guevin1.chat.cmd.w;
import guevin1.chat.cmd.mes;
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

    public static FileConfiguration config;
    public static FileConfiguration message;
    public static FileConfiguration data;
    @Override
    public void onEnable() {
        messageFile = new File("plugins/Chat/message.yml");
        configFile = new File("plugins/Chat/config.yml");
        dataFile = new File("plugins/Chat/data.yml");
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
        if (!configFile.exists()){
            saveDefaultConfig();
            getLogger().info(" config.yml нету скачиваю");
        }else {
            getLogger().info(" config.yml есть");
        }
        config = YamlConfiguration.loadConfiguration(configFile);
        message = YamlConfiguration.loadConfiguration(messageFile);
        data = YamlConfiguration.loadConfiguration(dataFile);
        getCommand("w").setExecutor(new w(this));
        getCommand("msg").setExecutor(new w(this));
        getCommand("tell").setExecutor(new w(this));
        getCommand("me").setExecutor(new mes(this));
        getCommand("spy").setExecutor(new spy(this));
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