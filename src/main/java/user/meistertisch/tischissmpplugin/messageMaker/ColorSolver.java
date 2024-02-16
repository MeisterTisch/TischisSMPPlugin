package user.meistertisch.tischissmpplugin.messageMaker;

import net.md_5.bungee.api.ChatColor;
import user.meistertisch.tischissmpplugin.Main;

public class ColorSolver {
    static String solvedMessage;
    public static String solveColor(String message){
        ChatColor color1 = ChatColor.getByChar(Main.getPlugin().getConfig().getString("color.color1").charAt(0));
        ChatColor color2 = ChatColor.getByChar(Main.getPlugin().getConfig().getString("color.color2").charAt(0));

        solvedMessage = message.replace("%color1%", ""+color1);
        solvedMessage = solvedMessage.replace("%color2%" , ""+color2);

        for(char color : ChatColor.ALL_CODES.toCharArray()){
            solvedMessage = solvedMessage.replace("%"+color+"%", ChatColor.getByChar(color)+"");
        }
        return solvedMessage;
    }
}
