package user.meistertisch.tischissmpplugin.admin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.file.YamlConfiguration;
import user.meistertisch.tischissmpplugin.Main;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CommandBan implements TabExecutor {
    YamlConfiguration bannedPlayersConfig = YamlConfiguration.loadConfiguration(new File(Main.getPlugin().getDataFolder(), "bannedPlayers"));
    //TODO: Admin only
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(s.toLowerCase(Locale.ROOT).equals("ban")){

        } else if(s.toLowerCase(Locale.ROOT).equals("unban")) {

        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        List<String> list = new ArrayList<>();
        if(s.toLowerCase(Locale.ROOT).equals("ban")){

        } else if(s.toLowerCase(Locale.ROOT).equals("unban")) {
            for(String name : bannedPlayersConfig.getKeys(false)){
                list.add(name);
            }
        }
        return list;
    }
}
