package user.meistertisch.tischissmpplugin.commands.tpr;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import user.meistertisch.tischissmpplugin.languages.Text;
import user.meistertisch.tischissmpplugin.messageMaker.MessageMaker;
import user.meistertisch.tischissmpplugin.messageMaker.TextTypes;
import user.meistertisch.tischissmpplugin.players.FilePlayers;
import user.meistertisch.tischissmpplugin.players.teleportation.Teleportation;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CommandTeleportRequest implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player player){
            if(strings.length == 1){
                if(List.of("to", "here").contains(strings[0].toLowerCase(Locale.ROOT))){
                    player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.command_tpr_noPlayerSelected), TextTypes.NO_SUCCESS));
                } else if(List.of("accept", "reject", "cancel").contains(strings[0].toLowerCase(Locale.ROOT))){
                    //TODO
                    if(strings[0].toLowerCase(Locale.ROOT).equals("cancel")){
                        for(TeleportationRequest request : TPRScheduler.getCountdown().keySet()){
                            if(request.getSender() == player){
                                //CAN CANCEL
                                TPRScheduler.removeRequest(player);
                                return true;
                            }
                        }
                        //NO REQUEST THERE
                        player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.command_tpr_noOpenRequest), TextTypes.NO_SUCCESS));
                    } else if(strings[0].toLowerCase(Locale.ROOT).equals("accept")){
                        for(TeleportationRequest request : TPRScheduler.getCountdown().keySet()){
                            if(request.getTarget() == player){
                                //CAN ACCEPT
                                request.getSender().sendMessage(MessageMaker.makeMessage(Text.getText(Text.command_tpr_acceptedToSender)
                                        .replace("%player%", player.getDisplayName()), TextTypes.SUCCESS));
                                player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.command_tpr_acceptedToTarget), TextTypes.SUCCESS));
                                if(!request.isReversed()){
                                    Teleportation.teleport(request.getSender(), request.getTarget().getLocation());
                                } else {
                                    Teleportation.teleport(request.getTarget(), request.getSender().getLocation());
                                }
                                return true;
                            }
                        }
                        //No request
                        player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.command_tpr_noOpenRequest), TextTypes.NO_SUCCESS));
                    } else if(strings[0].toLowerCase(Locale.ROOT).equals("reject")){
                        for(TeleportationRequest request : TPRScheduler.getCountdown().keySet()){
                            if(request.getTarget() == player){
                                //CAN REJECT
                                request.getSender().sendMessage(MessageMaker.makeMessage(Text.getText(Text.command_tpr_rejectedToSender)
                                        .replace("%player%", player.getDisplayName()), TextTypes.SUCCESS));
                                player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.command_tpr_rejectedToTarget), TextTypes.NO_SUCCESS));
                                TPRScheduler.removeRequest(request.getSender());
                                return true;
                            }
                        }
                        //No request
                        player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.command_tpr_noOpenRequest), TextTypes.NO_SUCCESS));
                    }
                } else if(strings[0].toLowerCase(Locale.ROOT).equals("toggle")){
                    boolean isRejecting = FilePlayers.getConfig().getBoolean(player.getDisplayName() + ".isRejectingTPR");
                    FilePlayers.getConfig().set(player.getDisplayName() + ".isRejectingTPR", !isRejecting);
                    if(isRejecting){
                        player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.command_tpr_toggle_off), TextTypes.NO_SUCCESS));
                    } else {
                        player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.command_tpr_toggle_on), TextTypes.SUCCESS));
                    }
                } else {
                    player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.command_invalidInput), TextTypes.NO_SUCCESS));
                }
            } else if(strings.length == 2){
                if(strings[0].toLowerCase(Locale.ROOT).equals("to")){
                    //Sender wants to target
                    if(Bukkit.getPlayer(strings[1]) != null){
                        Player target = Bukkit.getPlayer(strings[1]);
                        TPRScheduler.request(player, target, false);
                        player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.command_tpr_to_msgSentSender)
                                .replace("%player%", target.getDisplayName()), TextTypes.SUCCESS));
                        target.sendMessage(MessageMaker.makeMessage(Text.getText(Text.command_tpr_to_msgSentSender)
                                .replace("%sender%", player.getDisplayName()), TextTypes.SUCCESS));
                    } else {
                        //player offline
                        player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.command_tpr_playerOffline)
                                .replace("%player%", strings[1]), TextTypes.NO_SUCCESS));
                    }
                } else if(strings[0].toLowerCase(Locale.ROOT).equals("here")){
                    //Sender wants target to sender
                    if(Bukkit.getPlayer(strings[1]) != null){
                        Player target = Bukkit.getPlayer(strings[1]);
                        TPRScheduler.request(player, target, true);
                        player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.command_tpr_here_msgSentSender)
                                .replace("%player%", target.getDisplayName()), TextTypes.SUCCESS));
                        target.sendMessage(MessageMaker.makeMessage(Text.getText(Text.command_tpr_here_msgSentSender)
                                .replace("%sender%", player.getDisplayName()), TextTypes.SUCCESS));
                    } else {
                        //player offline
                        player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.command_tpr_playerOffline)
                                .replace("%player%", strings[1]), TextTypes.NO_SUCCESS));
                    }
                } else if(strings[0].toLowerCase(Locale.ROOT).equals("toggle")){
                    if(strings[1].toLowerCase(Locale.ROOT).equals("on")){
                        if(!FilePlayers.getConfig().getBoolean(player.getDisplayName() + ".isRejectingTPR")){
                            player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.command_tpr_toggle_alreadyOn), TextTypes.NO_SUCCESS));
                        } else {
                            FilePlayers.getConfig().set(player.getDisplayName() + ".isRejectingTPR", false);
                            FilePlayers.saveConfig();
                            player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.command_tpr_toggle_on), TextTypes.SUCCESS));
                        }
                    } else if(strings[1].toLowerCase(Locale.ROOT).equals("off")){
                        if(FilePlayers.getConfig().getBoolean(player.getDisplayName() + ".isRejectingTPR")){
                            player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.command_tpr_toggle_alreadyOff), TextTypes.NO_SUCCESS));
                        } else {
                            FilePlayers.getConfig().set(player.getDisplayName() + ".isRejectingTPR", true);
                            FilePlayers.saveConfig();
                            player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.command_tpr_toggle_off), TextTypes.SUCCESS));
                        }
                    } else {
                        player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.command_invalidInput), TextTypes.NO_SUCCESS));
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

        if(strings.length == 1){
            list.addAll(List.of("to", "here", "toggle", "accept", "reject", "cancel"));
        } else if(strings.length == 2){
            if(strings[0].toLowerCase(Locale.ROOT).equals("toggle")){
                list.addAll(List.of("on", "off"));
            } else if(strings[0].toLowerCase(Locale.ROOT).equals("here") || strings[0].toLowerCase(Locale.ROOT).equals("to")){
                return null;
            }
        }

        return list;
    }
}
