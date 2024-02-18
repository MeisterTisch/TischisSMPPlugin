package user.meistertisch.tischissmpplugin.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import user.meistertisch.tischissmpplugin.Main;
import user.meistertisch.tischissmpplugin.languages.Text;
import user.meistertisch.tischissmpplugin.messageMaker.MessageMaker;
import user.meistertisch.tischissmpplugin.messageMaker.TextTypes;

import java.util.HashMap;

public class ListenerDimensionAllowance implements Listener {
    private HashMap<Player, String> thrownPlayers = new HashMap<>();
    @EventHandler
    public void noTeleporting(PlayerPortalEvent event){
        if(event.getCause() == PlayerTeleportEvent.TeleportCause.END_PORTAL && event.getFrom().getWorld().getName().equals("world")){
            if(!Main.getPlugin().getConfig().getBoolean("dimensionAllowance.end")){
                event.setCancelled(true);
                Player player = event.getPlayer();
                player.setVelocity(player.getEyeLocation().getDirection().multiply(-1));
                thrownPlayers.put(player, "end");
            }
        } else if(event.getCause() == PlayerTeleportEvent.TeleportCause.NETHER_PORTAL && event.getFrom().getWorld().getName().equals("world")){
            if(!Main.getPlugin().getConfig().getBoolean("dimensionAllowance.nether")){
                event.setCancelled(true);
                Player player = event.getPlayer();
                player.setVelocity(player.getEyeLocation().getDirection().multiply(-1));
                thrownPlayers.put(player, "nether");
            }
        }
    }

    @EventHandler
    public void playerLanded(PlayerMoveEvent event){
        if(thrownPlayers.containsKey(event.getPlayer())){
            event.getPlayer().sendMessage(MessageMaker.makeMessage(Text.getText("dimensionAllowance_"
                    + thrownPlayers.get(event.getPlayer())+"NotAllowed"), TextTypes.NO_SUCCESS));
            thrownPlayers.remove(event.getPlayer());
        }
    }
}
