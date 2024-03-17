package user.meistertisch.tischissmpplugin.messageMaker;

import net.md_5.bungee.api.ChatColor;
import user.meistertisch.tischissmpplugin.Main;
import user.meistertisch.tischissmpplugin.languages.Text;

public class MessageMaker {
    ChatColor color1;
    ChatColor color2;
    static String makedMessage;
    public static String makeMessage(String message, TextTypes type){
        ChatColor color1 = ChatColor.getByChar(Main.getPlugin().getConfig().getString("color.color1").charAt(0));
        ChatColor color2 = ChatColor.getByChar(Main.getPlugin().getConfig().getString("color.color2").charAt(0));

        switch(type){
            case DEFAULT -> {
                makedMessage = message;
            }
            case NORMAL -> {
                makedMessage = Main.getPlugin().getConfig().getString("prefix") + ChatColor.WHITE + message;
            }
            case SUCCESS -> {
                makedMessage = Main.getPlugin().getConfig().getString("prefix") + ChatColor.GREEN + message;
            }
            case NO_SUCCESS -> {
                makedMessage = Main.getPlugin().getConfig().getString("prefix") + ChatColor.RED + message;
            }
            case CHAT_TEAM -> {
                makedMessage = color1 + "[" + color2 + Text.getText(Text.teamchat) + color1 + "] " + ChatColor.RESET + message;
            }
            case IMPORTANT -> {
                makedMessage = Main.getPlugin().getConfig().getString("prefix") + ChatColor.GOLD + "[" + Text.getText(Text.important) + "]" + ChatColor.WHITE + "\n" + message;
            }
            case ERROR -> {
                makedMessage = Main.getPlugin().getConfig().getString("prefix") + ChatColor.RED + "[" + Text.getText(Text.error) + "]" + ChatColor.WHITE + "\n" + message;
            }
            case ANNOUNCEMENT -> {
                makedMessage = Main.getPlugin().getConfig().getString("prefix") + ChatColor.GOLD + "[" + Text.getText(Text.announcement) + "]" + ChatColor.WHITE + "\n" + message;
            }
            case DM -> {
                makedMessage = color1 + "[" + color2 + "%sender%" + color1 + " -> " + color2 + "%target%" + color1 + "]" + ChatColor.WHITE + message;
            }
            case DEBUG -> {
                makedMessage = Main.getPlugin().getConfig().getString("prefix") + ChatColor.GRAY + "[DEBUG]" + ChatColor.WHITE + "\n" + message;
            }
            case LOG -> {
                //TODO: Need command and Files
            }
        }

        return ColorSolver.solveColor(makedMessage);
    }
}
