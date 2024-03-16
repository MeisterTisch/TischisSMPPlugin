package user.meistertisch.tischissmpplugin.players.elytra;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;


public class ListenerForElytra implements Listener {
    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntityType() == EntityType.PLAYER
                && (event.getCause() == EntityDamageEvent.DamageCause.FALL
                || event.getCause() == EntityDamageEvent.DamageCause.FLY_INTO_WALL)
                && ElytraFlyAway.getFlyingList().contains(event.getEntity())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDoubleJump(PlayerToggleFlightEvent event) {
        if (event.getPlayer().getGameMode() != GameMode.SURVIVAL && event.getPlayer().getGameMode() != GameMode.ADVENTURE) return;
        if (!ElytraFlyAway.isInSpawnRadius(event.getPlayer())) {
            return;
        }
        event.setCancelled(true);
        event.getPlayer().setGliding(true);
        ElytraFlyAway.addToFlyingList(event.getPlayer());
    }

    @EventHandler
    public void antiFalse(EntityToggleGlideEvent event){
        if(event.getEntity() instanceof Player player) {
            if (ElytraFlyAway.getFlyingList().contains(player)) {
                event.setCancelled(true);
            }
        }
    }
}
