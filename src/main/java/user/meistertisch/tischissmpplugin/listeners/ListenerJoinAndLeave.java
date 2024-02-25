package user.meistertisch.tischissmpplugin.listeners;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import user.meistertisch.tischissmpplugin.languages.Text;
import user.meistertisch.tischissmpplugin.messageMaker.MessageMaker;
import user.meistertisch.tischissmpplugin.messageMaker.TextTypes;
import user.meistertisch.tischissmpplugin.players.FilePlayers;

import java.time.LocalDateTime;

public class ListenerJoinAndLeave implements Listener {
    //FOR BAN
//    @EventHandler
//    public void playerPreJoin(AsyncPlayerPreLoginEvent event){
//        FileConfiguration config = FilePlayers.getConfig();
//        if(config.getBoolean(event.getName() + ".ban.isBanned")){
//            /**
//             * Für ban so machen:
//             * Einschreiben, wie lang bannen und wann gebannt wurde
//             * dann immer prüfen, wenn der spieler reinkommt, ob die Zeit, also der Zeitpunkt plus dauer
//             * schon her ist. Also, wenn die jetzige Zeit größer/älter ist oder so als der Banzeitpunkt plus dauer
//             */
//            String message;
//            String duration;
//
//
//            if(config.getBoolean("ban.withDuration")){
//                message = MessageMaker.makeMessage(Text.getText(Text.ban_withDuration)
//                        .replace("%reason%", config.getString("ban.reason"))
//                        .replace("%duration%", )
//                        , TextTypes.NO_SUCCESS);
//            } else {
//
//            }
//            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, );
//        }
//    }

    @EventHandler
    public void playerJoined(PlayerJoinEvent event){

    }

    @EventHandler
    public void playerLeave(PlayerQuitEvent event){

    }
}
