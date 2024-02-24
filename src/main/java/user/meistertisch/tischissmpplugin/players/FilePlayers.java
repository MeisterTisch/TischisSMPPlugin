package user.meistertisch.tischissmpplugin.players;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import user.meistertisch.tischissmpplugin.Main;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class FilePlayers {
    public static FileConfiguration getPlayersFile(Player player){
        FileConfiguration config;
        File file;
        UUID uuid = player.getUniqueId();
        String name = player.getDisplayName();
        String fileName = uuid + " - " + name + ".yml";

        file = new File(Main.getPlugin().getDataFolder(), "players/" + fileName);

        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        config = YamlConfiguration.loadConfiguration(file);
        return config;
    }

    public static void safeConfig(Player player){
        FileConfiguration config;
        File file;
        UUID uuid = player.getUniqueId();
        String name = player.getDisplayName();
        String fileName = uuid + " - " + name + ".yml";

        file = new File(Main.getPlugin().getDataFolder(), "players/" + fileName);

        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        config = YamlConfiguration.loadConfiguration(file);

        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
