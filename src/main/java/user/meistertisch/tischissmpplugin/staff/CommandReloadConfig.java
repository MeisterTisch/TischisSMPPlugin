package user.meistertisch.tischissmpplugin.staff;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import user.meistertisch.tischissmpplugin.Main;
import user.meistertisch.tischissmpplugin.languages.Text;
import user.meistertisch.tischissmpplugin.messageMaker.MessageMaker;
import user.meistertisch.tischissmpplugin.messageMaker.TextTypes;

import java.util.ArrayList;
import java.util.List;

public class CommandReloadConfig implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(strings.length == 0){
            //keine args (good)
            Main.getPlugin().reloadConfig();
            commandSender.sendMessage(MessageMaker.makeMessage(Text.getText(Text.staff_commands_reloadConfig_reloadSuccessful), TextTypes.NORMAL));
        } else {
            //some args (not good)
            commandSender.sendMessage(MessageMaker.makeMessage(Text.getText(Text.staff_commands_invalidArgsLength), TextTypes.ERROR));
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return new ArrayList<>();
    }
}
