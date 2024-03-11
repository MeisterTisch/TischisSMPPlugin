package user.meistertisch.tischissmpplugin.messageMaker;

import net.md_5.bungee.api.ChatColor;
import user.meistertisch.tischissmpplugin.Main;

import java.util.Locale;

public class ColorSolver {
    public static String solveColor(String message){
        ChatColor color1 = ChatColor.getByChar(Main.getPlugin().getConfig().getString("color.color1").charAt(0));
        ChatColor color2 = ChatColor.getByChar(Main.getPlugin().getConfig().getString("color.color2").charAt(0));

        message = message.replace("%color1%", ""+color1);
        message = message.replace("%color2%" , ""+color2);

        for(char color : ChatColor.ALL_CODES.toCharArray()){
            message = message.replace("%"+color+"%", ChatColor.getByChar(color)+"");
        }
        return message;
    }

    public static String solveColorForChat(String message){
        for(char color : ChatColor.ALL_CODES.toCharArray()){
            message = message.replace("&" + color, ChatColor.getByChar(color) + "");
        }
        return message;
    }

    public static ChatColor giveColorByName(String colorName){
        switch (colorName.toLowerCase(Locale.ROOT)) {
            case "black"        -> { return ChatColor.BLACK; }
            case "dark_blue"    -> { return ChatColor.DARK_BLUE; }
            case "dark_green"   -> { return ChatColor.DARK_GREEN; }
            case "dark_aqua"    -> { return ChatColor.DARK_AQUA; }
            case "dark_red"     -> { return ChatColor.DARK_RED; }
            case "dark_purple"  -> { return ChatColor.DARK_PURPLE; }
            case "gold"         -> { return ChatColor.GOLD; }
            case "gray"         -> { return ChatColor.GRAY; }
            case "dark_gray"    -> { return ChatColor.DARK_GRAY; }
            case "blue"         -> { return ChatColor.BLUE; }
            case "green"        -> { return ChatColor.GREEN; }
            case "aqua"         -> { return ChatColor.AQUA; }
            case "red"          -> { return ChatColor.RED; }
            case "light_purple" -> { return ChatColor.LIGHT_PURPLE; }
            case "yellow"       -> { return ChatColor.YELLOW; }
            case "white"        -> { return ChatColor.WHITE; }
            default             -> { return null; }
        }
    }

    public static ChatColor giveEffectByName(String effectName){
        switch (effectName.toLowerCase(Locale.ROOT)) {
            case "strikethrough"        -> { return ChatColor.STRIKETHROUGH; }
            case "underlined"    -> { return ChatColor.UNDERLINE; }
            case "italic"   -> { return ChatColor.ITALIC; }
            default             -> { return null; }
        }
    }
}
