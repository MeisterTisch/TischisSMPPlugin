package user.meistertisch.tischissmpplugin.messageMaker;

import net.md_5.bungee.api.ChatColor;

public enum TextTypes {
    NORMAL, //Normal Message for one player or console
    CHAT_TEAM, //Chat message from one player to team
    IMPORTANT, //Important or warning Message for one player or console
    ERROR, //Error Message for one player or console
    ANNOUNCEMENT, //Announcement for all players, from one staff player or console
    DM, //Direct Message to one player, from another player or console
    JOIN_OR_LEAVE,
    DEBUG, //Messages for one staff player or console
    LOG; //Specific logs for one staff player or all logs for console.

}
