package chat.chat;

import chat.chat.commands.chat.dof;
import chat.chat.commands.chat.me;
import chat.chat.commands.chat.rand;
import chat.chat.commands.chat.w;
import chat.chat.commands.moder.*;
import chat.chat.commands.nick.alias;
import chat.chat.commands.nick.hide;
import chat.chat.commands.nick.name;
import chat.chat.commands.other.*;
import chat.chat.commands.test;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Chat extends JavaPlugin {
    private static Chat instance;

    @Override
    public void onEnable() {
        instance = this;
        Bukkit.getPluginManager().registerEvents(new Handler(this),this);
        Bukkit.getPluginManager().registerEvents(new Events(this),this);
        getLogger().info("сима ddлох");
        getCommand("do").setExecutor(new dof(this));
        getCommand("me").setExecutor(new me(this));
        getCommand("w").setExecutor(new w(this));
        getCommand("msg").setExecutor(new w(this));
        getCommand("tell").setExecutor(new w(this));
        getCommand("test").setExecutor(new test(this));
        getCommand("spy").setExecutor(new spy(this));
        getCommand("unmutec").setExecutor(new unmute(this));
        getCommand("mutec").setExecutor(new mute(this));
        getCommand("hide-name").setExecutor(new hide(this));
        getCommand("new-nick").setExecutor(new name(this));
        getCommand("color-chat").setExecutor(new color(this));
        getCommand("change-chat").setExecutor(new local(this));
        getCommand("try").setExecutor(new rand(this));
        getCommand("tempmutec").setExecutor(new tempmute(this));
        getCommand("debug").setExecutor(new debug(this));
        getCommand("resetConfig").setExecutor(new ResapwnConfig(this));
        getCommand("block").setExecutor(new block(this));
        getCommand("unblock").setExecutor(new unblock(this));
        getLogger().info("Plugin Started!");
        getLogger().info("Спасибо За использование");
        saveDefaultConfig();
        saveResource("users.yml",false);
        saveResource("message.yml",false);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    public static Chat getInstance(){
        return instance;
    }

}