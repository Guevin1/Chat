package guevin1.chat.Tab;

import guevin1.chat.main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public class groupsTab implements TabCompleter {
    public groupsTab(main main) {
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        FileConfiguration groups = main.group;
        List<String> completions = new ArrayList<>();
        if (args.length == 1){
            completions.add("join");
            completions.add("leave");
            completions.add("info");
            completions.add("list");
            completions.add("create");
            completions.add("edit");
            completions.add("chat");
        }if (args.length == 2){
            switch (args[0]){
                case("chat"):
                    for(String key : groups.getKeys(true)){

                        if (key.contains("players")){
                            if (groups.getStringList(key).contains(sender.getName())){
                                completions.add("["+groups.getString(key.replace("players","name"))+"]");
                            }

                        }
                    }
                    break;

                case ("leave"):
                    for(String key : groups.getKeys(true)){

                        if (key.contains("players")){
                            if (groups.getStringList(key).contains(sender.getName())){
                                completions.add(groups.getString(key.replace("players","name")));
                            }

                        }
                    }
                    break;
                case ("edit"):
                    for(String key : groups.getKeys(true)){

                        if (key.contains("subowner")){
                            if (groups.getStringList(key).contains(sender.getName()) && groups.getStringList(key.replace("subowner","players")).contains(sender.getName())){
                                completions.add(groups.getString(key.replace("subowner","name")));
                            }

                        }
                    }
                case("delete"):
                    for(String key : groups.getKeys(true)){

                        if (key.contains("owner")){
                            if (groups.getString(key).equals(sender.getName())){
                                completions.add(groups.getString(key.replace("owner","name")));
                            }

                        }
                    }
                    break;
                case("join"):
                    for(String key : groups.getKeys(true)){
                        if (key.contains("players")){
                            if (!groups.getStringList(key).contains(sender.getName())) {
                                completions.add(groups.getString(key.replace("players", "name")));
                            }
                        }
                    }
                    break;
            }
        }
        return completions;

    }
}
