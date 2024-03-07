package user.meistertisch.tischissmpplugin.players.teleportation;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Teleportation {
    Player player;
    Location lastLoc;
    Location newLoc;

    /**
     * Need this:
     * BukkitRunnable for time standing. --> Add/remove player to/from list, which checks the movement listener.
     * if player moves --> Stop teleportation
     * if not --> after three sec teleport
     *
     */
}
