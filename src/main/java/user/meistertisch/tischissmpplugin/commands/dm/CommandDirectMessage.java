package user.meistertisch.tischissmpplugin.commands.dm;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import user.meistertisch.tischissmpplugin.languages.Text;
import user.meistertisch.tischissmpplugin.messageMaker.MessageMaker;
import user.meistertisch.tischissmpplugin.messageMaker.TextTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CommandDirectMessage implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player player){
            if (s.toLowerCase(Locale.ROOT).equals("dm") || s.toLowerCase(Locale.ROOT).equals("msg") || s.toLowerCase(Locale.ROOT).equals("message")) {
                if (strings.length == 0) {
                    //nothing
                    player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.command_dm_noPlayer), TextTypes.NO_SUCCESS));
                } else if (strings.length == 1) {
                    //only Player
                    player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.command_dm_noMessage), TextTypes.NO_SUCCESS));
                } else {
                    if(Bukkit.getPlayer(strings[0]) != null){
                        //Check for not self writing
                        if(Bukkit.getPlayer(strings[0]) == player){
                            player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.command_dm_senderIsTarget), TextTypes.NO_SUCCESS));
                            return true;
                        }

                        StringBuilder messageBuilder = new StringBuilder();
                        for (int i = 1; i < strings.length; i++) {
                            messageBuilder.append(" ").append(strings[i]);
                        }
                        String message = messageBuilder.toString();
                        player.sendMessage(MessageMaker.makeMessage(message, TextTypes.DM)
                                .replace("%sender%", Text.getText(Text.command_dm_youSending))
                                .replace("%target%", strings[0]));
                        Bukkit.getPlayer(strings[0]).sendMessage(MessageMaker.makeMessage(message, TextTypes.DM)
                                .replace("%sender%", player.getDisplayName())
                                .replace("%target%", Text.getText(Text.command_dm_youReceiving)));
                        DirectMessageScheduler.addToList(player, Bukkit.getPlayer(strings[0]));
                    } else {
                        player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.command_dm_playerOffline)
                                .replace("%player%", strings[0]), TextTypes.NO_SUCCESS));
                    }
                }
            } else {
                if (strings.length == 0) {
                    //no message
                    player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.command_dm_noMessage), TextTypes.NO_SUCCESS));
                } else {
                    //Check for 1. can reply to someone, 2.Player online
                    Player target = DirectMessageScheduler.getLastTarget(player);
                    if(target != null){
                        if(target.isOnline()){
                            StringBuilder messageBuilder = new StringBuilder();
                            for (String string : strings) {
                                messageBuilder.append(" ").append(string);
                            }
                            String message = messageBuilder.toString();
                            player.sendMessage(MessageMaker.makeMessage(message, TextTypes.DM)
                                    .replace("%sender%", Text.getText(Text.command_dm_youSending))
                                    .replace("%target%", target.getDisplayName()));
                            DirectMessageScheduler.getLastTarget(player).sendMessage(MessageMaker.makeMessage(message, TextTypes.DM)
                                    .replace("%sender%", player.getDisplayName())
                                    .replace("%target%", Text.getText(Text.command_dm_youReceiving)));
                            DirectMessageScheduler.addToList(player, Bukkit.getPlayer(target.getDisplayName()));
                        } else {
                            player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.command_dm_playerOffline)
                                    .replace("%player%", target.getDisplayName()), TextTypes.NO_SUCCESS));
                        }
                    } else {
                        player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.command_dm_noOneToReply), TextTypes.NO_SUCCESS));
                    }
                }
            }
        } else {
            commandSender.sendMessage(MessageMaker.makeMessage(Text.getText(Text.commands_onlyPlayers), TextTypes.NO_SUCCESS));
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        List<String> list = new ArrayList<>();
        if ((s.toLowerCase(Locale.ROOT).equals("dm") || s.toLowerCase(Locale.ROOT).equals("msg") || s.toLowerCase(Locale.ROOT).equals("message"))
                && strings.length == 1) {
            return null;
        }
        return list;
    }
}
