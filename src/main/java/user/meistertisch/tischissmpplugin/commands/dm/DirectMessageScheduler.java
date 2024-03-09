package user.meistertisch.tischissmpplugin.commands.dm;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class DirectMessageScheduler {
    private static ScheduledExecutorService service;
    private static ScheduledFuture scheduledFuture;
    private static HashMap<HashMap<Player, Player>, Integer> countdown = new HashMap<>();

    public static void setup(){
        service = Executors.newSingleThreadScheduledExecutor();
        scheduledFuture = service.scheduleAtFixedRate(() -> {
            if(!countdown.isEmpty()) {
                for (HashMap<Player, Player> map : countdown.keySet()) {
                    if (countdown.get(map) <= 0) {
                        countdown.remove(map);
                    } else {
                        countdown.put(map, countdown.get(map) - 1);
                    }
                }
            }
        }, 0, 1, TimeUnit.MINUTES);
    }

    public static void stopScheduler(){
        scheduledFuture.cancel(true);
        service.shutdown();
    }

    public static void addToList(Player sender, Player target){
        HashMap<Player, Player> map = new HashMap<>();
        map.put(sender, target);
        map.put(target, sender);
        countdown.put(map, 15);
    }

    public static void removeFromList(Player player){
        for (HashMap<Player, Player> map : countdown.keySet()) {
            map.remove(player);
            for(Player p : Bukkit.getOnlinePlayers()){
                map.remove(p, player);
            }
        }
    }

    public static Player getLastTarget(Player sender){
        for (HashMap<Player, Player> map : countdown.keySet()) {
            if (map.get(sender) != null) {
                return map.get(sender);
            }
        }
        return null;
    }
}
