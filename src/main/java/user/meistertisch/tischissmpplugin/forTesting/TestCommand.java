package user.meistertisch.tischissmpplugin.forTesting;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import user.meistertisch.tischissmpplugin.Main;

public class TestCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Bukkit.broadcastMessage(Main.getPlugin().getConfig().getString("dimensionAllowance.nether"));
        return true;
    }
}
