package user.meistertisch.tischissmpplugin.admin.freezing;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class FreezingPlayer {
    private static List<Player> playerList = new ArrayList<>();

    public static void addPlayer(Player player){
        playerList.add(player);
    }

    public static void removePlayer(Player player){
        playerList.remove(player);
    }

    public static List<Player> getPlayerList() {
        return playerList;
    }
}
