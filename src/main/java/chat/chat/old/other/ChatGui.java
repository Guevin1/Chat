package chat.chat.commands.other;

import chat.chat.Chat;
import chat.chat.other.GUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ChatGui implements CommandExecutor {
    public ChatGui(Chat chat) {
    }

    @Override
    public boolean onCommand(CommandSender se,Command command,String label,String[] args) {
        if (se instanceof Player){
            Player p = ((Player) se).getPlayer();
            GUI.OpenGUIChat(p);
        }else {
            se.sendMessage("Пошёв нахрен");
        }
        return true;
    }
}
