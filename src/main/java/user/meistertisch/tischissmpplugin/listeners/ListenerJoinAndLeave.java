package user.meistertisch.tischissmpplugin.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import user.meistertisch.tischissmpplugin.commands.dm.DirectMessageScheduler;
import user.meistertisch.tischissmpplugin.players.PlayerListPrefix;

public class ListenerJoinAndLeave implements Listener {
    @EventHandler
    public void playerJoined(PlayerJoinEvent event){
        PlayerListPrefix.makePrefix(event.getPlayer());
    }

    @EventHandler
    public void playerLeave(PlayerQuitEvent event){
        DirectMessageScheduler.removeFromList(event.getPlayer());
    }
}
