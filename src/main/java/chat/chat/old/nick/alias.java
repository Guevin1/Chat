package chat.chat.commands.nick;

import chat.chat.Chat;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class alias implements CommandExecutor {
    public alias(Chat chat) {
    }

    @Override
    public boolean onCommand(CommandSender se,Command command,String label,String[] args) {



        return true;
    }
}
