package user.meistertisch.tischissmpplugin.admin;

import org.bukkit.Bukkit;
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
import java.util.Locale;

public class CommandAdmin implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        //CHECK FOR ADMIN
        if(commandSender instanceof Player p){
            boolean isAdmin = FilePlayers.getConfig().getBoolean(p.getDisplayName() + ".isAdmin");
            if(!isAdmin){
                p.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_noAdmin), TextTypes.NO_SUCCESS));
                return true;
            }
        }

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
            if(commandSender instanceof Player sender){
                if(sender.getDisplayName().equals(strings[1])){
                    commandSender.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_commands_admin_target_sameAsSender), TextTypes.NO_SUCCESS));
                    return true;
                }
            }
            if(strings[0].toLowerCase(Locale.ROOT).equals("add")){
                String player = strings[1];
                if(FilePlayers.getConfig().getBoolean(player + ".isAdmin")){
                    commandSender.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_commands_admin_playerAlreadyAdmin)
                            .replace("%player%", player), TextTypes.NO_SUCCESS));
                } else {
                    FilePlayers.getConfig().set(player + ".isAdmin", true);
                    FilePlayers.saveConfig();
                    commandSender.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_commands_admin_playerAdded)
                            .replace("%player%", player), TextTypes.SUCCESS));
                    Player target = Bukkit.getPlayer(player);
                    if(target != null){
                        target.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_commands_admin_target_addedToAdmin), TextTypes.SUCCESS));
                    }
                }
            } else if(strings[0].toLowerCase(Locale.ROOT).equals("remove")){
                String player = strings[1];
                if(!FilePlayers.getConfig().getBoolean(player + ".isAdmin")){
                    commandSender.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_commands_admin_playerNotAdmin)
                            .replace("%player%", player), TextTypes.NO_SUCCESS));
                } else {
                    FilePlayers.getConfig().set(player + ".isAdmin", false);
                    FilePlayers.saveConfig();
                    commandSender.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_commands_admin_playerRemoved)
                            .replace("%player%", player), TextTypes.SUCCESS));
                    Player target = Bukkit.getPlayer(player);
                    if(target != null){
                        target.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_commands_admin_target_removedFromAdmin), TextTypes.NO_SUCCESS));
                    }
                }
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
