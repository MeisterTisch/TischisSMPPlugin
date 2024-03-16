package user.meistertisch.tischissmpplugin.forTesting;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import user.meistertisch.tischissmpplugin.players.elytra.ElytraFlyAway;

public class TestCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player player){
            Bukkit.broadcastMessage(String.valueOf(ElytraFlyAway.getFlyingList().contains(player)));
        }
        return true;
    }
}
