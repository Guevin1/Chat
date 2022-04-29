package chat.chat.commands;

import chat.chat.Chat;
import chat.chat.users;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class test implements CommandExecutor {
    private static Chat instance;
    public test(Chat plugin) {
        //	this.plugin = plugin;

    }

    @Override
    public boolean onCommand(CommandSender se, Command command, @NotNull String label, String[] args) {
        Player p = (Player) se;
        se.sendMessage(p.getName() + ".spy");
        se.sendMessage(String.valueOf(users.config().getBoolean("Guevin1.mute")));
        return false;
    }
}
