package user.meistertisch.tischissmpplugin.admin.freezing;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FreezingPlayer {
    private static List<UUID> playerList = new ArrayList<>();

    public static void addPlayer(Player player){
        playerList.add(player.getUniqueId());
    }

    public static void removePlayer(Player player){
        playerList.remove(player.getUniqueId());
    }

    public static List<UUID> getPlayerList() {
        return playerList;
    }
}
