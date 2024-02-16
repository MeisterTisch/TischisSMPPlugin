package user.meistertisch.tischissmpplugin.important.messageMaker;

import net.md_5.bungee.api.ChatColor;
import user.meistertisch.tischissmpplugin.Main;

public class ColorSolver {
    static String solvedMessage;
    public static String solveColor(String message){
        ChatColor color1 = ChatColor.getByChar((Character) Main.getPlugin().getConfig().get("color.color1"));
        ChatColor color2 = ChatColor.getByChar((Character) Main.getPlugin().getConfig().get("color.color2"));

        solvedMessage = message.replace("%color1%", ""+color1);
        solvedMessage = solvedMessage.replace("%color2%" , ""+color2);



        for(char color : ChatColor.ALL_CODES.toCharArray()){
            solvedMessage = solvedMessage.replace("§"+color, ChatColor.getByChar(color)+"");
        }

        return null;
    }
}
