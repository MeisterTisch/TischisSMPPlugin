package user.meistertisch.tischissmpplugin.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import user.meistertisch.tischissmpplugin.Main;
import user.meistertisch.tischissmpplugin.admin.teams.FileTeams;
import user.meistertisch.tischissmpplugin.languages.Text;
import user.meistertisch.tischissmpplugin.messageMaker.ColorSolver;
import user.meistertisch.tischissmpplugin.messageMaker.MessageMaker;
import user.meistertisch.tischissmpplugin.messageMaker.TextTypes;
import user.meistertisch.tischissmpplugin.misc.PlayerListPrefix;
import user.meistertisch.tischissmpplugin.players.FilePlayers;

public class ListenerChat implements Listener {
    @EventHandler
    public void playerSayInChat(AsyncPlayerChatEvent event){
        String chatMessage = ColorSolver.solveColorForChat(event.getMessage());
        Player player = event.getPlayer();
        if(Main.getPlugin().getConfig().getBoolean("chatDisabled") && !FilePlayers.getConfig().getBoolean(player.getDisplayName() + ".isAdmin")) {
            event.setCancelled(true);
            player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.chatDisabled_chatIsDisabled), TextTypes.NO_SUCCESS));
            return;
        }

        if(FilePlayers.getConfig().getBoolean(player.getDisplayName() + ".isInTeamChat")){
            for(String c : FileTeams.getConfig().getStringList("takenColors")){
                if(FileTeams.getConfig().getString(c+".name").equals(FilePlayers.getConfig().getString(player.getDisplayName()+".team"))){
                    for(String pl : FileTeams.getConfig().getStringList(c + ".players")){
                        if(Bukkit.getPlayer(pl) != null){
                            Bukkit.getPlayer(pl).sendMessage(MessageMaker.makeMessage(
                                    player.getDisplayName() + ": " + chatMessage, TextTypes.CHAT_TEAM));
                            Bukkit.getPlayer(pl)
                                    .playSound(Bukkit.getPlayer(pl), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 1,1,1);
                        }
                    }
                    break;
                }
            }
            event.setCancelled(true);
            return;
        }

        if(FilePlayers.getConfig().getString(player.getDisplayName() + ".team") != null){
            event.setFormat(PlayerListPrefix.givePrefix(player) + ": " + chatMessage);
        } else {
            event.setFormat(event.getPlayer().getDisplayName() + ": " + chatMessage);
        }
    }
}
