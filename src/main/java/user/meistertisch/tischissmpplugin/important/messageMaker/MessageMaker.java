package user.meistertisch.tischissmpplugin.important.messageMaker;

import net.md_5.bungee.api.ChatColor;
import user.meistertisch.tischissmpplugin.Main;

public class MessageMaker {
    ChatColor color1;
    ChatColor color2;
    static String makedMessage;
    public static String makeMessage(String message, MessageTypes type){
        if(Main.getColorsWork()){
            ChatColor color1 = ChatColor.getByChar((Character) Main.getPlugin().getConfig().get("color.color1"));
            ChatColor color2 = ChatColor.getByChar((Character) Main.getPlugin().getConfig().get("color.color2"));
        } else {
            makedMessage = null; //TODO: AFTER MAKING LANGUAGE FILES, DO THE ERROR MESSAGE.
        }

        switch(type){
            case NORMAL -> {
            }
            case CHAT -> {
            }
            case CHAT_TEAM -> {
            }
            case IMPORTANT -> {
            }
            case ERROR -> {
            }
            case ANNOUNCEMENT -> {
            }
            case DM -> {
            }
            case DEBUG -> {
            }
            case LOG -> {
            }
        }

        return makedMessage;
    }
}
