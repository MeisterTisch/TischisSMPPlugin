package user.meistertisch.tischissmpplugin.admin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import user.meistertisch.tischissmpplugin.languages.Text;
import user.meistertisch.tischissmpplugin.messageMaker.MessageMaker;
import user.meistertisch.tischissmpplugin.messageMaker.TextTypes;
import user.meistertisch.tischissmpplugin.players.FilePlayers;

import java.util.ArrayList;
import java.util.List;

public class CommandBypassSpawnProt implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player p){
            boolean isAdmin = FilePlayers.getConfig().getBoolean(p.getDisplayName() + ".isAdmin");
            if(!isAdmin){
                p.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_noAdmin), TextTypes.NO_SUCCESS));
                return true;
            }

            if(strings.length == 0){
                boolean isOn = FilePlayers.getConfig().getBoolean(p.getDisplayName() + ".isBypassingSpawnProt");
                if(isOn) {
                    p.sendMessage(MessageMaker.makeMessage(Text.getText(Text.spawn_bypass_turnedOff), TextTypes.SUCCESS));
                } else {
                    p.sendMessage(MessageMaker.makeMessage(Text.getText(Text.spawn_bypass_turnedOn), TextTypes.SUCCESS));
                }
                FilePlayers.getConfig().set(p.getDisplayName() + ".isBypassingSpawnProt", !isOn);
                FilePlayers.saveConfig();
            }
        } else {
            commandSender.sendMessage(MessageMaker.makeMessage(Text.getText(Text.commands_onlyPlayers), TextTypes.NO_SUCCESS));
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if(strings.length == 1) {
            return new ArrayList<>(List.of("on", "off"));
        }
        return null;
    }
}
