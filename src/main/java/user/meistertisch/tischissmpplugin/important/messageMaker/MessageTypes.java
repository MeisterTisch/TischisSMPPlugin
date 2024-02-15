package user.meistertisch.tischissmpplugin.important.messageMaker;

import net.md_5.bungee.api.ChatColor;

public enum MessageTypes {
    NORMAL(null), //Normal Message for one player or console
    CHAT(ChatColor.WHITE), //Chat Message from one player to all online players
    CHAT_TEAM(ChatColor.WHITE), //Chat message from one player to team
    IMPORTANT(ChatColor.GOLD), //Important or warning Message for one player or console
    ERROR(ChatColor.RED), //Error Message for one player or console
    ANNOUNCEMENT(null), //Announcement for all players, from one staff player or console
    DM(null), //Direct Message to one player, from another player or console
    JOIN_OR_LEAVE(null),
    DEBUG(ChatColor.GRAY), //Messages for one staff player or console
    LOG(ChatColor.GRAY); //Specific logs for one staff player or all logs for console.

    ChatColor color;
    MessageTypes(ChatColor color) {
        this.color = color;
    }
}
