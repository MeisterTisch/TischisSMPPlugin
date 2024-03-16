package user.meistertisch.tischissmpplugin.players.elytra;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.util.BoundingBox;
import user.meistertisch.tischissmpplugin.Main;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ElytraFlyAway {
    private static List<Player> flying = new ArrayList<>();
    private static ScheduledExecutorService service;
    private static ScheduledFuture scheduledFuture;
    private static final Location spawn = Main.getPlugin().getConfig().getLocation("spawn.location");

    public static void addToFlyingList(Player player){
        if(!flying.contains(player)){
            flying.add(player);
        }
    }
    public static void removePlayer(Player player){
        flying.remove(player);
    }

    public static void setup(){
        if(spawn == null || spawn.getWorld() == null) return;
        BoundingBox boundingBox = new BoundingBox(spawn.getX()-50, spawn.getY()-75, spawn.getZ()-50,
                spawn.getX()+50, spawn.getY()+75, spawn.getZ()+50);

        service = Executors.newSingleThreadScheduledExecutor();
        scheduledFuture = service.scheduleAtFixedRate(() -> {
            spawn.getWorld().getPlayers().forEach(player -> {
                if (player.getGameMode() != GameMode.SURVIVAL && player.getGameMode() != GameMode.ADVENTURE) return;
                player.setAllowFlight(boundingBox.contains(player.getBoundingBox()));
                if (flying.contains(player) && !player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType().isAir()) {
                    player.setAllowFlight(false);
                    player.setGliding(false);
                    Bukkit.getScheduler().runTaskLater(Main.getPlugin(), () -> flying.remove(player), 5);
                }
            });
        }, 0, 200, TimeUnit.MILLISECONDS);
    }

    public static void stop(){
        scheduledFuture.cancel(true);
        service.shutdown();
    }

    public static List<Player> getFlyingList() {
        return flying;
    }
    public static boolean isInSpawnRadius(Player player){
        return new BoundingBox(spawn.getX()-50, spawn.getY()-75, spawn.getZ()-50,
                spawn.getX()+50, spawn.getY()+75, spawn.getZ()+50).contains(player.getBoundingBox());
    }
}
