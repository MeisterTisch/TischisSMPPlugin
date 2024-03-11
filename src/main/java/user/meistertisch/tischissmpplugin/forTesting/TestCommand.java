package user.meistertisch.tischissmpplugin.forTesting;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import user.meistertisch.tischissmpplugin.players.FilePlayers;
import user.meistertisch.tischissmpplugin.players.teleportation.Teleportation;

public class TestCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player player){
            player.sendMessage(String.valueOf(player.getInventory().getItemInMainHand()));
        }
        return true;
    }
}
