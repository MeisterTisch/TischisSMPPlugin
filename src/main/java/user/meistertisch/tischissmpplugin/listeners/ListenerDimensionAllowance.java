package user.meistertisch.tischissmpplugin.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import user.meistertisch.tischissmpplugin.Main;
import user.meistertisch.tischissmpplugin.languages.Text;
import user.meistertisch.tischissmpplugin.messageMaker.MessageMaker;
import user.meistertisch.tischissmpplugin.messageMaker.TextTypes;

import java.util.ArrayList;
import java.util.List;

public class ListenerDimensionAllowance implements Listener {
    //TODO: BIG BUG WHEN PORTAL ENTER SERVER KAPUTTTT
    private static List<Player> thrownBackPlayers = new ArrayList<>();
    @EventHandler
    public void noTeleporting(PlayerPortalEvent event){
        if(event.getCause() == PlayerTeleportEvent.TeleportCause.END_PORTAL && event.getFrom().getWorld().getName().equals("world")){
            if(!Main.getPlugin().getConfig().getBoolean("dimensionAllowance.end")){
                event.setCancelled(true);
                Player player = event.getPlayer();
                player.setVelocity(player.getEyeLocation().getDirection().multiply(-0.75));
                while(!thrownBackPlayers.contains(player))
                    player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.dimensionAllowance_endNotAllowed), TextTypes.IMPORTANT));
                thrownBackPlayers.add(player);
            }
        } else if(event.getCause() == PlayerTeleportEvent.TeleportCause.NETHER_PORTAL && event.getFrom().getWorld().getName().equals("world")){
            if(!Main.getPlugin().getConfig().getBoolean("dimensionAllowance.nether")){
                event.setCancelled(true);
                Player player = event.getPlayer();
                player.setVelocity(player.getEyeLocation().getDirection().multiply(-0.75));
                while(!thrownBackPlayers.contains(player))
                    player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.dimensionAllowance_netherNotAllowed), TextTypes.IMPORTANT));
                thrownBackPlayers.add(player);
            }
        }
    }

    @EventHandler
    public void noFallDamage(EntityDamageEvent event){
        if(event.getCause() == EntityDamageEvent.DamageCause.FALL
                && event.getEntityType() == EntityType.PLAYER
                && thrownBackPlayers.contains((Player) event.getEntity())){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void playerLanded(PlayerMoveEvent event){
        if(thrownBackPlayers.contains(event.getPlayer())){
            Player player = event.getPlayer();
            Location loc = player.getLocation();

            Block block = player.getWorld().getBlockAt(new Location(player.getWorld(), loc.getBlockX(), loc.getBlockY()-1, loc.getBlockZ()));
            if(block.getType() != Material.AIR){
                thrownBackPlayers.remove(player);
            }
        }
    }
}
