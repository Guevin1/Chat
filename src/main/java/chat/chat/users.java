package chat.chat;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class users {
    public static void save() {
        File c_file = new File("plugins/Chat/users.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(c_file);
        try {
            Bukkit.getLogger().info("Users.yml Saved");
            config.save(c_file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static FileConfiguration config(){
        File c_file = new File("plugins/Chat/users.yml");
        return YamlConfiguration.loadConfiguration(c_file);
    }


}
