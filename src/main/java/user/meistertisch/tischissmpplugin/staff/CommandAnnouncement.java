package user.meistertisch.tischissmpplugin.staff;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import user.meistertisch.tischissmpplugin.languages.Text;
import user.meistertisch.tischissmpplugin.messageMaker.MessageMaker;
import user.meistertisch.tischissmpplugin.messageMaker.TextTypes;

import java.util.Arrays;

public class CommandAnnouncement implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(strings.length > 0){
            String message = "";
            for(String string : strings){
                message = message + " " + string;
            }
            Bukkit.broadcastMessage(MessageMaker.makeMessage(message, TextTypes.ANNOUNCEMENT));
        } else commandSender.sendMessage(MessageMaker.makeMessage(Text.getText(Text.staff_commands_announcement_invalidMessageLength), TextTypes.ERROR));
        return true;
    }
}
