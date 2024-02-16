package user.meistertisch.tischissmpplugin.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ListenerChat implements Listener {
    @EventHandler
    public void playerSayInChat(AsyncPlayerChatEvent event){
        //TODO: Adding Teams
        event.setFormat(event.getPlayer().getDisplayName() + ": " + event.getMessage());
    }
}
