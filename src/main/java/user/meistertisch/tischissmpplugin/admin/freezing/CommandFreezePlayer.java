package user.meistertisch.tischissmpplugin.admin.freezing;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import user.meistertisch.tischissmpplugin.languages.Text;
import user.meistertisch.tischissmpplugin.messageMaker.MessageMaker;
import user.meistertisch.tischissmpplugin.messageMaker.TextTypes;
import user.meistertisch.tischissmpplugin.players.FilePlayers;

import java.util.List;

public class CommandFreezePlayer implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player p){
            boolean isAdmin = FilePlayers.getConfig().getBoolean(p.getDisplayName() + ".isAdmin");
            if(!isAdmin){
                p.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_noAdmin), TextTypes.NO_SUCCESS));
                return true;
            }
        }

        if (commandSender instanceof Player player) {
            if(strings.length == 1){
                Player target = Bukkit.getPlayer(strings[0]);
                if(target == null){
                    player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_command_chest_playerNotOnline)
                            .replace("%player%", strings[0]), TextTypes.NO_SUCCESS));
                    return true;
                }

                if(s.equals("freeze")){
                    if(FreezingPlayer.getPlayerList().contains(target)){
                        player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_command_freeze_alreadyFrozen)
                                .replace("%player%", target.getDisplayName()), TextTypes.NO_SUCCESS));
                        return true;
                    }

                    FreezingPlayer.addPlayer(target);
                    player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_command_freeze_frozen)
                            .replace("%player%", target.getDisplayName()), TextTypes.SUCCESS));
                    target.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_command_freeze_targetFrozen), TextTypes.NO_SUCCESS));
                    target.setFlying(true);
                } else if(s.equals("unfreeze")){
                    if(!FreezingPlayer.getPlayerList().contains(target)){
                        player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_command_freeze_notFrozen)
                                .replace("%player%", target.getDisplayName()), TextTypes.NO_SUCCESS));
                        return true;
                    }

                    FreezingPlayer.removePlayer(target);
                    player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_command_freeze_unfrozen)
                            .replace("%player%", target.getDisplayName()), TextTypes.SUCCESS));
                    target.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_command_freeze_targetUnfrozen), TextTypes.SUCCESS));
                    target.setFlying(false);
                }
            }

        } else {
            commandSender.sendMessage(MessageMaker.makeMessage(Text.getText(Text.commands_onlyPlayers), TextTypes.NO_SUCCESS));
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return null;
    }
}
