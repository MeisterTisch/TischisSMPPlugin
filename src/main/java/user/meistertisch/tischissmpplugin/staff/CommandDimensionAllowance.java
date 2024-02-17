package user.meistertisch.tischissmpplugin.staff;

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
                commandSender.sendMessage(MessageMaker.makeMessage(Text.getText(Text.staff_commands_dimension_noAllowance), TextTypes.ERROR));
            } else {
                //Dimension passt nicht
                commandSender.sendMessage(MessageMaker.makeMessage(Text.getText(Text.staff_commands_dimension_invalidDimension), TextTypes.ERROR));
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
                        String message;
                        if(allowing){
                            message = Text.getText(Text.staff_commands_dimension_alreadyAllowed)
                                    .replace("%dimension%", Text.getText("staff_commands_dimension_successfulExecution_dimension_"+strings[0]));
                        } else {
                            message = Text.getText(Text.staff_commands_dimension_alreadyDisallowed)
                                    .replace("%dimension%", Text.getText("staff_commands_dimension_successfulExecution_dimension_"+strings[0]));
                        }
                        commandSender.sendMessage(MessageMaker.makeMessage(message, TextTypes.IMPORTANT));
                        return true;
                    }

                    //allowance passt
                    Main.getPlugin().getConfig().set("dimensionAllowance."+strings[0].toLowerCase(Locale.ROOT), allowing);
                    Main.getPlugin().saveConfig();
                    Main.getPlugin().reloadConfig();

                    String successfulExecution = Text.getText(Text.staff_commands_dimension_successfulExecution_text)
                            .replace("%dimension%", Text.getText("staff_commands_dimension_successfulExecution_dimension_"+strings[0]))
                            .replace("%allowance%", Text.getText("staff_commands_dimension_successfulExecution_allowance_"+strings[1]));;

                    String announcement = Text.getText(Text.staff_commands_dimension_successfulExecution_announceText)
                            .replace("%dimension%", Text.getText("staff_commands_dimension_successfulExecution_dimension_"+strings[0]))
                            .replace("%allowance%", Text.getText("staff_commands_dimension_successfulExecution_allowance_"+strings[1]));

                    commandSender.sendMessage(MessageMaker.makeMessage(successfulExecution, TextTypes.NORMAL));
                    Bukkit.broadcastMessage(MessageMaker.makeMessage(announcement, TextTypes.ANNOUNCEMENT));
                } else {
                    //allowance passt nicht
                    commandSender.sendMessage(MessageMaker.makeMessage(Text.getText(Text.staff_commands_dimension_invalidAllowance), TextTypes.ERROR));
                }
            } else {
                //Dimension passt nicht
                commandSender.sendMessage(MessageMaker.makeMessage(Text.getText(Text.staff_commands_dimension_invalidDimension), TextTypes.ERROR));
            }
        }
        //Mehr als zwei oder nichts geschrieben
        else {
            if(strings.length == 0){
                commandSender.sendMessage(MessageMaker.makeMessage(Text.getText(Text.staff_commands_dimension_noDimension), TextTypes.ERROR));
            } else {
            commandSender.sendMessage(MessageMaker.makeMessage(Text.getText(Text.staff_commands_invalidArgsLength), TextTypes.ERROR));
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
