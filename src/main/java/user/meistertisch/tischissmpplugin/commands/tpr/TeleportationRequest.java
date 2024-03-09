package user.meistertisch.tischissmpplugin.commands.tpr;

import org.bukkit.entity.Player;

public class TeleportationRequest {
    private Player sender;
    private Player target;
    private boolean isReversed;

    public TeleportationRequest(Player sender, Player target, boolean isReversed) {
        this.sender = sender;
        this.target = target;
        this.isReversed = isReversed;
    }

    public Player getSender() {
        return sender;
    }

    public Player getTarget() {
        return target;
    }

    public boolean isReversed() {
        return isReversed;
    }
}
