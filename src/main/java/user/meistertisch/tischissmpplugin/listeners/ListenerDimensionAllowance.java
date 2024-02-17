package user.meistertisch.tischissmpplugin.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerVelocityEvent;
import user.meistertisch.tischissmpplugin.Main;
import user.meistertisch.tischissmpplugin.languages.Text;
import user.meistertisch.tischissmpplugin.messageMaker.MessageMaker;
import user.meistertisch.tischissmpplugin.messageMaker.TextTypes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListenerDimensionAllowance implements Listener {
    //TODO: Maybe do HashMap to List when i move the message up to noTeleporting()
    private HashMap<Player, String> thrownPlayers = new HashMap<>();
    @EventHandler
    public void noTeleporting(PlayerPortalEvent event){
        if(event.getCause() == PlayerTeleportEvent.TeleportCause.END_PORTAL && event.getFrom().getWorld().getName().equals("world")){
            if(!Main.getPlugin().getConfig().getBoolean("dimensionAllowance.end")){
                event.setCancelled(true);
                Player player = event.getPlayer();
                player.setVelocity(player.getEyeLocation().getDirection().multiply(-0.75));
                thrownPlayers.put(player, "end");
                Bukkit.broadcastMessage(String.valueOf("end: " + thrownPlayers)); //DEBUG
            }
        } else if(event.getCause() == PlayerTeleportEvent.TeleportCause.NETHER_PORTAL && event.getFrom().getWorld().getName().equals("world")){
            if(!Main.getPlugin().getConfig().getBoolean("dimensionAllowance.nether")){
                event.setCancelled(true);
                Player player = event.getPlayer();
                player.setVelocity(player.getEyeLocation().getDirection().multiply(-0.75));
                thrownPlayers.put(player, "nether");
                Bukkit.broadcastMessage(String.valueOf(thrownPlayers)); //DEBUG
            }
        }
    }


    @EventHandler
    public void noFallDamage(EntityDamageEvent event){
        Bukkit.broadcastMessage(String.valueOf(thrownPlayers)); //DEBUG
        if(event.getCause() == EntityDamageEvent.DamageCause.FALL
                && event.getEntityType() == EntityType.PLAYER
                && thrownPlayers.containsKey((Player) event.getEntity())){
            event.setCancelled(true);
//            thrownPlayers.remove((Player) event.getEntity());
        }
    }
    //TODO: If entity doesn't get fall damage, it remains till it gets
    //TODO: Only when he lands on the ground
//    @EventHandler
//    public void playerStoppedFalling(PlayerVelocityEvent event){
//        Bukkit.broadcastMessage("velocity: " + event.getVelocity()); //DEBUG
//        Bukkit.broadcastMessage("Block: " + event.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN).getType() + "\n"); //DEBUG
//        if(event.getVelocity().getBlockY() == 0
//                && event.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN).getType() != Material.AIR){
////            thrownPlayers.remove(event.getPlayer());
//            Bukkit.broadcastMessage("i removed the thingy: " + String.valueOf(thrownPlayers)); //DEBUG
//        }
//    }

    @EventHandler
    public void playerLanded(PlayerMoveEvent event){
        if(thrownPlayers.containsKey(event.getPlayer())
            && event.getTo().getBlock().getRelative(BlockFace.DOWN).getType() == Material.AIR){
            thrownPlayers.remove(event.getPlayer());
            Bukkit.broadcastMessage("i removed the thingy: " + thrownPlayers); //DEBUG
        }
    }

//    @EventHandler
//    public void playerLanded(PlayerMoveEvent event){
//        if(thrownPlayers.containsKey(event.getPlayer())){
//            Player player = event.getPlayer();
//            Location loc = player.getLocation();
//            Block block = player.getWorld().getBlockAt(new Location(player.getWorld(), loc.getBlockX(), loc.getBlockY()-1, loc.getBlockZ()));
//            if(block.getType() != Material.AIR){
//                if(thrownPlayers.get(player).equals("nether"))
//                    player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.dimensionAllowance_netherNotAllowed), TextTypes.IMPORTANT));
//                else if(thrownPlayers.get(player).equals("end"))
//                    player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.dimensionAllowance_endNotAllowed), TextTypes.IMPORTANT));
//                thrownPlayers.remove(player);
//            }
//        }
//    }
}
