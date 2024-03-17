package user.meistertisch.tischissmpplugin.listeners;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import user.meistertisch.tischissmpplugin.Main;
import user.meistertisch.tischissmpplugin.commands.dm.DirectMessageScheduler;
import user.meistertisch.tischissmpplugin.messageMaker.MessageMaker;
import user.meistertisch.tischissmpplugin.messageMaker.TextTypes;
import user.meistertisch.tischissmpplugin.players.PlayerListPrefix;

import java.util.Random;
import java.util.random.RandomGenerator;

public class ListenerJoinAndLeave implements Listener {
    @EventHandler
    public void playerJoined(PlayerJoinEvent event){
        PlayerListPrefix.makePrefix(event.getPlayer());

        event.getPlayer().setPlayerListHeader(ChatColor.BLUE + "Craft " + ChatColor.AQUA + "Mining\n" + ChatColor.AQUA + "-----" + ChatColor.BLUE + "-----");

        int random = (int) (Math.random() * 10) + 1;
        String message = Main.getPlugin().getConfig().getString("messages.join.random" + random);
        if(message == null) return;
        event.setJoinMessage(MessageMaker.makeMessage(ChatColor.BLUE + message.replace("%player%",
                ChatColor.AQUA + event.getPlayer().getDisplayName() + ChatColor.BLUE), TextTypes.DEFAULT));
    }

    @EventHandler
    public void playerLeave(PlayerQuitEvent event){
        DirectMessageScheduler.removeFromList(event.getPlayer());

        int random = (int) (Math.random() * 10) + 1;
        String message = Main.getPlugin().getConfig().getString("messages.leave.random" + random);
        if(message == null) return;
        event.setQuitMessage(MessageMaker.makeMessage(ChatColor.BLUE + message.replace("%player%",
                ChatColor.AQUA + event.getPlayer().getDisplayName() + ChatColor.BLUE), TextTypes.DEFAULT));
    }
}
