package user.meistertisch.tischissmpplugin.players.teleportation;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import user.meistertisch.tischissmpplugin.languages.Text;
import user.meistertisch.tischissmpplugin.messageMaker.MessageMaker;
import user.meistertisch.tischissmpplugin.messageMaker.TextTypes;

public class ListenerMove implements Listener {
    @EventHandler
    public void playerMoved(PlayerMoveEvent event){
        if(Teleportation.getPlayersMap().containsKey(event.getPlayer())){
            //TODO: maybe do with Bounding Box
            if(event.getFrom().getX() == event.getTo().getX()
                    && event.getFrom().getY() == event.getTo().getY()
                    && event.getFrom().getZ() == event.getTo().getZ()
                    && event.getFrom().getDirection() != event.getTo().getDirection()){
                return;
            } else {
                Teleportation.removePlayer(event.getPlayer());
                event.getPlayer().sendMessage(MessageMaker.makeMessage(Text.getText(Text.teleportation_canceled), TextTypes.NO_SUCCESS));
            }
        }
    }
}
