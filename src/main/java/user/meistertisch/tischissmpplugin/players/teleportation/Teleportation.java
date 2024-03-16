package user.meistertisch.tischissmpplugin.players.teleportation;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import user.meistertisch.tischissmpplugin.Main;
import user.meistertisch.tischissmpplugin.languages.Text;
import user.meistertisch.tischissmpplugin.messageMaker.MessageMaker;
import user.meistertisch.tischissmpplugin.messageMaker.TextTypes;
import user.meistertisch.tischissmpplugin.players.teleportation.ParticlesForTeleportation;

import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Teleportation {
    /**
     * Need this:
     * BukkitRunnable for time standing. --> Add/remove player to/from list, which checks the movement listener.
     * if player moves --> Stop teleportation
     * if not --> after three sec teleport
     */
    private static ScheduledExecutorService service;
    private static ScheduledFuture scheduledFuture;
    private static HashMap<Player, Integer> countdown = new HashMap<>();
    private static HashMap<Player, Location> location = new HashMap<>();

    public static void setup(){
        service = Executors.newSingleThreadScheduledExecutor();
        scheduledFuture = service.scheduleAtFixedRate(() -> {
            if(!countdown.isEmpty()) {
                for (Player player : countdown.keySet()) {
                    if (countdown.get(player) <= 0) {
                        makeTeleportation(player);
                        countdown.remove(player);
                    } else {
                        countdown.put(player, countdown.get(player) - 1);
                    }
                }
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    public static void teleport(Player player, Location newLocation){
        countdown.put(player, 3);
        location.put(player, newLocation);
        player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.teleportation_dontMove), TextTypes.IMPORTANT));
        new ParticlesForTeleportation(player.getLocation(), newLocation);
    }

    private static void makeTeleportation(Player player){
        Location newLoc = location.get(player);
        Bukkit.getScheduler().runTask(Main.getPlugin(), () -> player.teleport(newLoc));
        location.remove(player);
        player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.teleportation_successful), TextTypes.SUCCESS));
    }

    public static void stopScheduler(){
        scheduledFuture.cancel(true);
        service.shutdown();
    }

    public static HashMap<Player, Integer> getPlayersMap() {
        return countdown;
    }

    public static void removePlayer(Player player){
        countdown.remove(player);
        location.remove(player);
    }
}
