package user.meistertisch.tischissmpplugin.misc.spawn;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.BoundingBox;
import user.meistertisch.tischissmpplugin.Main;
import user.meistertisch.tischissmpplugin.languages.Text;
import user.meistertisch.tischissmpplugin.messageMaker.MessageMaker;
import user.meistertisch.tischissmpplugin.messageMaker.TextTypes;

import javax.print.attribute.standard.MediaSize;

public class ListenerSpawn implements Listener {
    private final Location spawn = Main.getPlugin().getConfig().getLocation("spawn.location");
    private final BoundingBox boundingBox = new BoundingBox(spawn.getX()-75, spawn.getY()-75, spawn.getZ()-75,
            spawn.getX()+75, spawn.getY()+75, spawn.getZ()+75);
    @EventHandler
    public void antiBuild(BlockPlaceEvent event){
        if(boundingBox.contains(event.getBlock().getBoundingBox())){
            event.setCancelled(true);
            event.getPlayer().sendMessage(MessageMaker.makeMessage(Text.getText(Text.spawn_noBuild), TextTypes.NO_SUCCESS));
        }
    }
    @EventHandler
    public void antiBreak(BlockBreakEvent event){
        if(boundingBox.contains(event.getBlock().getBoundingBox())){
            event.setCancelled(true);
            event.getPlayer().sendMessage(MessageMaker.makeMessage(Text.getText(Text.spawn_noDestroy), TextTypes.NO_SUCCESS));
        }
    }

    @EventHandler
    public void antiInteract(PlayerInteractEvent event){
        if(event.getClickedBlock() == null) return;
        if(event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if(event.getPlayer().getInventory().getItemInMainHand().getType() != Material.AIR) return;
        if(boundingBox.contains(event.getClickedBlock().getBoundingBox())){
            event.setCancelled(true);
            event.getPlayer().sendMessage(MessageMaker.makeMessage(Text.getText(Text.spawn_noInteract), TextTypes.NO_SUCCESS));
        }
    }

    @EventHandler
    public void antiBreak(EntityDamageEvent event){
        if(boundingBox.contains(event.getEntity().getBoundingBox())){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void noHunger(FoodLevelChangeEvent event){
        event.setFoodLevel(20);
        event.setCancelled(true);
    }

    @EventHandler
    public void noLeave(PlayerMoveEvent event){
        Location spawn = Main.getPlugin().getConfig().getLocation("spawn.location");
        boolean isStarted = Main.getPlugin().getConfig().getBoolean("isStarted");
        if(spawn == null) return;
        if(isStarted) return;
        if(!boundingBox.contains(event.getPlayer().getBoundingBox())){
            event.getPlayer().teleport(spawn);
        }
    }
}
