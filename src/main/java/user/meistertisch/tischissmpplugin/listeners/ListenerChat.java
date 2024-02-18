package user.meistertisch.tischissmpplugin.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import user.meistertisch.tischissmpplugin.Main;
import user.meistertisch.tischissmpplugin.languages.Text;
import user.meistertisch.tischissmpplugin.messageMaker.MessageMaker;
import user.meistertisch.tischissmpplugin.messageMaker.TextTypes;

public class ListenerChat implements Listener {
    //TODO: staff only can write
    @EventHandler
    public void playerSayInChat(AsyncPlayerChatEvent event){
        if(Main.getPlugin().getConfig().getBoolean("chatDisabled")){
            event.setCancelled(true);
            event.getPlayer().sendMessage(MessageMaker.makeMessage(Text.getText(Text.chatDisabled_chatIsDisabled), TextTypes.COMMAND_NO_SUCCESS));
            return;
        }
        //TODO: Adding Teams
        event.setFormat(event.getPlayer().getDisplayName() + ": " + event.getMessage());
    }
}
