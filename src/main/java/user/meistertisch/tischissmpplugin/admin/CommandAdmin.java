package user.meistertisch.tischissmpplugin.admin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import user.meistertisch.tischissmpplugin.languages.Text;
import user.meistertisch.tischissmpplugin.messageMaker.MessageMaker;
import user.meistertisch.tischissmpplugin.messageMaker.TextTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CommandAdmin implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(strings.length == 1){
            //Add or remove
            if(strings[0].toLowerCase(Locale.ROOT).equals("add")){
                commandSender.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_commands_admin_noPlayerForAdding), TextTypes.NO_SUCCESS));
            } else if(strings[0].toLowerCase(Locale.ROOT).equals("remove")){
                commandSender.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_commands_admin_noPlayerForRemoving), TextTypes.NO_SUCCESS));
            } else {
                commandSender.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_commands_admin_invalidInput), TextTypes.NO_SUCCESS));
            }
        } else if(strings.length == 2){
            //TODO: Make the commands work
            if(strings[0].toLowerCase(Locale.ROOT).equals("add")){
                commandSender.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_commands_admin_playerAdded)
                        .replace("%player%", strings[1]), TextTypes.SUCCESS));

                commandSender.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_commands_admin_playerAlreadyAdmin)
                        .replace("%player%", strings[1]), TextTypes.NO_SUCCESS));
            } else if(strings[0].toLowerCase(Locale.ROOT).equals("remove")){
                commandSender.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_commands_admin_playerRemoved)
                        .replace("%player%", strings[1]), TextTypes.SUCCESS));

                commandSender.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_commands_admin_playerNotAdmin)
                        .replace("%player%", strings[1]), TextTypes.NO_SUCCESS));
            }
        } else {
            commandSender.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_commands_invalidArgsLength), TextTypes.NO_SUCCESS));
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if(strings.length == 1){
            return new ArrayList<>(List.of("add", "remove"));
        } else if(strings.length == 2){
            return null;
        }
        return new ArrayList<>();
    }
}
