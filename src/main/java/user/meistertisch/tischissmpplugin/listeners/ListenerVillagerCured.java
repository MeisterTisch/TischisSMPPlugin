package user.meistertisch.tischissmpplugin.listeners;

import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTransformEvent;
import user.meistertisch.tischissmpplugin.players.FilePlayers;

import java.util.Collection;

public class ListenerVillagerCured implements Listener {
    @EventHandler
    public void villagerCured(EntityTransformEvent event){
        if(event.getTransformReason() != EntityTransformEvent.TransformReason.CURED) return;
        if(!(event.getTransformedEntity() instanceof Villager villager)) return;
        if(!(event.getEntity() instanceof Player player)) return;

        boolean isPlayerInTeam = FilePlayers.getConfig().get(player.getDisplayName() + ".team") != null
                && FilePlayers.getConfig().isString(player.getDisplayName() + ".team");
        if(!isPlayerInTeam) return;
        String team = FilePlayers.getConfig().getString(player.getDisplayName() + ".team");

    }
}
