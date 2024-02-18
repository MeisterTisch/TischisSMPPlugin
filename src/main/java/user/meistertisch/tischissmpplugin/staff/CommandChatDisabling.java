package user.meistertisch.tischissmpplugin.staff;

import org.bukkit.ChatColor;
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

public class CommandChatDisabling implements TabExecutor {
    //TODO: ONLY STAFF
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(strings.length == 1){
            //length passt
            if(strings[0].toLowerCase(Locale.ROOT).equals("enable")
                    || strings[0].toLowerCase(Locale.ROOT).equals("disable")){
                //input is right
                boolean wantDisable = strings[0].toLowerCase(Locale.ROOT).equals("disable");
                if(wantDisable == Main.getPlugin().getConfig().getBoolean("chatDisabled")){
                   //already disabled or enabled
                   if(strings[0].toLowerCase(Locale.ROOT).equals("disable")){
                       //already disabled
                       commandSender.sendMessage(MessageMaker.makeMessage(Text.getText(Text.staff_commands_chatDisabling_statusAlreadyThere)
                                       .replace("%status%", Text.getText(Text.staff_commands_chatDisabling_status_disabled)), TextTypes.NO_SUCCESS));
                   } else {
                       //already enabled
                       commandSender.sendMessage(MessageMaker.makeMessage(Text.getText(Text.staff_commands_chatDisabling_statusAlreadyThere)
                               .replace("%status%", Text.getText(Text.staff_commands_chatDisabling_status_enabled)), TextTypes.NO_SUCCESS));
                   }
                } else {
                    //we can change
                    if(strings[0].toLowerCase(Locale.ROOT).equals("disable")){
                        //making disable
                        Main.getPlugin().getConfig().set("chatDisabled", true);
                        Main.getPlugin().saveConfig();
                        Main.getPlugin().reloadConfig();
                        commandSender.sendMessage(MessageMaker.makeMessage(Text.getText(Text.staff_commands_chatDisabling_disabled), TextTypes.SUCCESS));
                    } else {
                        //making enabled
                        Main.getPlugin().getConfig().set("chatDisabled", false);
                        Main.getPlugin().saveConfig();
                        Main.getPlugin().reloadConfig();
                        commandSender.sendMessage(MessageMaker.makeMessage(Text.getText(Text.staff_commands_chatDisabling_enabled), TextTypes.SUCCESS));
                    }
                }
            } else {
                //input is wrong
                commandSender.sendMessage(MessageMaker.makeMessage(Text.getText(Text.staff_commands_chatDisabling_invalidStatusInput), TextTypes.NO_SUCCESS));
            }
        } else if (strings.length == 0){
            //checking able oder not
            String message = Text.getText(Text.staff_commands_chatDisabling_statusAsking);
            if(Main.getPlugin().getConfig().getBoolean("chatDisabled")){
                message = message.replace("%status%", ChatColor.RED+Text.getText(Text.staff_commands_chatDisabling_status_disabled)+ChatColor.RESET);
            } else {
                message = message.replace("%status%", ChatColor.GREEN+Text.getText(Text.staff_commands_chatDisabling_status_enabled)+ChatColor.RESET);
            }
            commandSender.sendMessage(MessageMaker.makeMessage(message, TextTypes.NORMAL));
        } else {
            //nichts passt
            commandSender.sendMessage(MessageMaker.makeMessage(Text.getText(Text.staff_commands_invalidArgsLength), TextTypes.NO_SUCCESS));
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        List<String> list = new ArrayList<>();
        if(strings.length == 1){
            list.addAll(List.of("enable", "disable"));
        }
        return list;
    }
}
