package user.meistertisch.tischissmpplugin.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import user.meistertisch.tischissmpplugin.commands.dm.DirectMessageScheduler;

public class ListenerJoinAndLeave implements Listener {
    @EventHandler
    public void playerJoined(PlayerJoinEvent event){

    }

    @EventHandler
    public void playerLeave(PlayerQuitEvent event){
        DirectMessageScheduler.removeFromList(event.getPlayer());
    }
}
