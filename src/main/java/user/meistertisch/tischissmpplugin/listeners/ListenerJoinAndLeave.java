package user.meistertisch.tischissmpplugin.listeners;

import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import user.meistertisch.tischissmpplugin.Main;
import user.meistertisch.tischissmpplugin.languages.Text;
import user.meistertisch.tischissmpplugin.messageMaker.MessageMaker;
import user.meistertisch.tischissmpplugin.messageMaker.TextTypes;
import user.meistertisch.tischissmpplugin.players.FilePlayers;

import java.sql.Time;
import java.util.Timer;

public class ListenerJoinAndLeave implements Listener {
    //FOR BAN
    @EventHandler
    public void playerPreJoin(AsyncPlayerPreLoginEvent event){
        Player player = Bukkit.getPlayer(event.getUniqueId());
        FileConfiguration config = FilePlayers.getPlayersFile(player);

        if(config.getBoolean("ban.isBanned")){
            /**
             * Für ban so machen:
             * Einschreiben, wie lang bannen und wann gebannt wurde
             * dann immer prüfen, wenn der spieler reinkommt, ob die Zeit, also der Zeitpunkt plus dauer
             * schon her ist. Also, wenn die jetzige Zeit größer/älter ist oder so als der Banzeitpunkt plus dauer
             */
            String message;


            if(config.getBoolean("ban.duration")){
                message = MessageMaker.makeMessage(Text.getText(Text.ban_withDuration)
                        .replace("%reason%", config.getString("ban.reason"))
                        .replace("%duration%", )
                        , TextTypes.NO_SUCCESS);
            } else {

            }
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, );
        }
    }

    @EventHandler
    public void playerJoined(PlayerJoinEvent event){

    }

    @EventHandler
    public void playerLeave(PlayerQuitEvent event){

    }
}
