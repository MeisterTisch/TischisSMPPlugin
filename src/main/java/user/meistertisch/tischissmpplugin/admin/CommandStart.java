package user.meistertisch.tischissmpplugin.admin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import user.meistertisch.tischissmpplugin.Main;

import java.util.List;

public class CommandStart implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player player && player.getDisplayName().equals("MeisterTisch")){
            Main.getPlugin().getConfig().set("isStarted", true);
            Main.getPlugin().saveConfig();
            Main.getPlugin().reloadConfig();
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return null;
    }
}
