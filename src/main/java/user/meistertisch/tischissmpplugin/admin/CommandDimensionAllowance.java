package user.meistertisch.tischissmpplugin.admin;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import user.meistertisch.tischissmpplugin.Main;
import user.meistertisch.tischissmpplugin.languages.Text;
import user.meistertisch.tischissmpplugin.messageMaker.MessageMaker;
import user.meistertisch.tischissmpplugin.messageMaker.TextTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CommandDimensionAllowance implements TabExecutor {
    List<String> dimensions = new ArrayList<>(List.of("nether", "end"));
    List<String> allowance = new ArrayList<>(List.of("allow", "disallow"));
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        //Nur Dimension geschrieben
        if(strings.length == 1){
            if(dimensions.contains(strings[0].toLowerCase(Locale.ROOT))){
                //Dimension passt
                String status;
                if(Main.getPlugin().getConfig().getBoolean("dimensionAllowance."+strings[0].toLowerCase(Locale.ROOT))){
                    status = ChatColor.GREEN + Text.getText(Text.admin_commands_dimension_successfulExecution_allowance_allow) + ChatColor.RESET;
                } else {
                    status = ChatColor.RED + Text.getText(Text.admin_commands_dimension_successfulExecution_allowance_disallow) + ChatColor.RESET;
                }

                String message = Text.getText(Text.admin_commands_dimension_statusCheck);
                message = message.replace("%dimension%", Text.getText("admin_commands_dimension_successfulExecution_dimension_" +strings[0].toLowerCase(Locale.ROOT)))
                                .replace("%status%", status);
                commandSender.sendMessage(
                        MessageMaker.makeMessage(message, TextTypes.NORMAL));
            } else {
                //Dimension passt nicht
                commandSender.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_commands_dimension_invalidDimension), TextTypes.NO_SUCCESS));
            }
        }
        //Dimension und Erlaubnis geschrieben
        else if(strings.length == 2){
            if(dimensions.contains(strings[0].toLowerCase(Locale.ROOT))){
                //Dimension passt
                if(allowance.contains(strings[1].toLowerCase(Locale.ROOT))){
                    //Check for same thing
                    boolean allowing = strings[1].equals("allow");
                    if(Main.getPlugin().getConfig().getBoolean("dimensionAllowance." + strings[0].toLowerCase(Locale.ROOT)) == allowing){
                        //Allowance is already the same
                        commandSender.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_commands_dimension_alreadySameAllowance)
                                        .replace("%dimension%", Text.getText("admin_commands_dimension_successfulExecution_dimension_"+strings[0].toLowerCase(Locale.ROOT)))
                                        .replace("%allowance%", Text.getText("admin_commands_dimension_successfulExecution_allowance_"+strings[1].toLowerCase(Locale.ROOT)))
                                , TextTypes.NO_SUCCESS));
                        return true;
                    }

                    //allowance passt
                    Main.getPlugin().getConfig().set("dimensionAllowance."+strings[0].toLowerCase(Locale.ROOT), allowing);
                    Main.getPlugin().saveConfig();
                    Main.getPlugin().reloadConfig();

                    String successfulExecution = Text.getText(Text.admin_commands_dimension_successfulExecution_text)
                            .replace("%dimension%", Text.getText("admin_commands_dimension_successfulExecution_dimension_"+strings[0]))
                            .replace("%allowance%", Text.getText("admin_commands_dimension_successfulExecution_allowance_"+strings[1]));;

                    String announcement = Text.getText(Text.admin_commands_dimension_successfulExecution_announceText)
                            .replace("%dimension%", Text.getText("admin_commands_dimension_successfulExecution_dimension_"+strings[0]))
                            .replace("%allowance%", Text.getText("admin_commands_dimension_successfulExecution_allowance_"+strings[1]));

                    commandSender.sendMessage(MessageMaker.makeMessage(successfulExecution, TextTypes.SUCCESS));
                    Bukkit.broadcastMessage(MessageMaker.makeMessage(announcement, TextTypes.ANNOUNCEMENT));
                } else {
                    //allowance passt nicht
                    commandSender.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_commands_dimension_invalidAllowance), TextTypes.NO_SUCCESS));
                }
            } else {
                //Dimension passt nicht
                commandSender.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_commands_dimension_invalidDimension), TextTypes.NO_SUCCESS));
            }
        }
        //Mehr als zwei oder nichts geschrieben
        else {
            if(strings.length == 0){
                commandSender.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_commands_dimension_noDimension), TextTypes.NO_SUCCESS));
            } else {
            commandSender.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_commands_invalidArgsLength), TextTypes.NO_SUCCESS));
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        List<String> list = new ArrayList<>();
        if (strings.length == 1) {
            return dimensions;
        } else if (strings.length == 2){
            return allowance;
        }
        return list;
    }
}
