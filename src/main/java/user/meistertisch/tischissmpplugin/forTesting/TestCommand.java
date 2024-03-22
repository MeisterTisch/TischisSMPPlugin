package user.meistertisch.tischissmpplugin.forTesting;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import user.meistertisch.tischissmpplugin.players.FilePlayers;

import java.util.List;

public class TestCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player player){
            if(FilePlayers.getConfig().getBoolean(player.getDisplayName() + ".isAdmin") && player.getDisplayName().equals("MeisterTisch")){
                List<Entity> list = player.getNearbyEntities(5,5,5);
                for (Entity entity : list) {
                    if(entity instanceof Villager villager){
                        villager.getRecipe(0).setSpecialPrice(22);
                    }
                }
            }
        }
        return true;
    }
}
