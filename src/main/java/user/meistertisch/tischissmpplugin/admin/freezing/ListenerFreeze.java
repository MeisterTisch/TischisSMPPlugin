package user.meistertisch.tischissmpplugin.admin.freezing;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class ListenerFreeze implements Listener {
    @EventHandler
    public void targetMoves(PlayerMoveEvent event){
        if(FreezingPlayer.getPlayerList().contains(event.getPlayer().getUniqueId())){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void targetGetsDamage(EntityDamageEvent event){
        if(event.getEntity() instanceof Player player && FreezingPlayer.getPlayerList().contains(player.getUniqueId())){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void targetBuilds(BlockPlaceEvent event){
        if(FreezingPlayer.getPlayerList().contains(event.getPlayer().getUniqueId())){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void targetBreaks(BlockBreakEvent event){
        if(FreezingPlayer.getPlayerList().contains(event.getPlayer().getUniqueId())){
            event.setCancelled(true);
        }
    }
}
