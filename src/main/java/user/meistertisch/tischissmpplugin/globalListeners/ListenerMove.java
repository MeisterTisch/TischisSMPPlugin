package user.meistertisch.tischissmpplugin.globalListeners;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.BoundingBox;
import user.meistertisch.tischissmpplugin.Main;
import user.meistertisch.tischissmpplugin.languages.Text;
import user.meistertisch.tischissmpplugin.messageMaker.MessageMaker;
import user.meistertisch.tischissmpplugin.messageMaker.TextTypes;
import user.meistertisch.tischissmpplugin.players.elytra.ElytraFlyAway;
import user.meistertisch.tischissmpplugin.players.teleportation.Teleportation;

public class ListenerMove implements Listener {
    Particle.DustOptions dustOptions = new Particle.DustOptions(Color.fromRGB(0, 127, 255), 1.0F);
    @EventHandler
    public void playerMoved(PlayerMoveEvent event){
        // FOR TELEPORTATION
        if(Teleportation.getPlayersMap().containsKey(event.getPlayer())){
            if(event.getFrom().getBlockX() != event.getTo().getBlockX()
            || event.getFrom().getBlockY() != event.getTo().getBlockY()
            || event.getFrom().getBlockZ() != event.getTo().getBlockZ()){
                Teleportation.removePlayer(event.getPlayer());
                event.getPlayer().sendMessage(MessageMaker.makeMessage(Text.getText(Text.teleportation_canceled), TextTypes.NO_SUCCESS));
            }
        }

        // FOR ELYTRA
//        if(Main.getPlugin().getConfig().isLocation("spawn.location") && Main.getPlugin().getConfig().get("spawn.location") != null){
//            Player player = event.getPlayer();
//            Location spawn = Main.getPlugin().getConfig().getLocation("spawn.location");
//            BoundingBox bb = new BoundingBox(spawn.getX(), spawn.getY(), spawn.getZ(), spawn.getX(), spawn.getY(), spawn.getZ()).expand(50);
//
//
//            if(bb.contains(player.getBoundingBox())
//                    && player.getLocation().getBlock().getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN).getType() == Material.AIR){
//                player.setGliding(true);
//                ElytraFlyAway.addPlayer(player);
//                player.sendMessage("in Box");
//            } else {
//                player.sendMessage("NICHTA WDA WD Box");
//            }
//        }
    }
}
