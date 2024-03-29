package user.meistertisch.tischissmpplugin;

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

public class CommandMute implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player p){
            boolean isAdmin = FilePlayers.getConfig().getBoolean(p.getDisplayName() + ".isAdmin");
            if(!isAdmin){
                p.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_noAdmin), TextTypes.NO_SUCCESS));
                return true;
            }
        }

        if(commandSender instanceof Player player){
            if(strings.length == 1){
                Player target = Bukkit.getPlayer(strings[0]);
                if(target == null){
                    player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_command_chest_playerNotOnline)
                            .replace("%player%", strings[0]), TextTypes.NO_SUCCESS));
                }
                if(s.equals("mute")){
                    if(FilePlayers.getConfig().getBoolean(target.getDisplayName() + ".isMuted")){
                        player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_command_mute_alreadyMuted)
                                .replace("%player%", target.getDisplayName()), TextTypes.NO_SUCCESS));
                        return true;
                    }
                    FilePlayers.getConfig().set(target.getDisplayName() + ".isMuted", true);
                    FilePlayers.saveConfig();
                    player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_command_mute_muted).replace("%player%", target.getDisplayName()),
                            TextTypes.SUCCESS));
                    target.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_command_mute_targetMuted), TextTypes.NO_SUCCESS));
                } else if (s.equals("unmute")){
                    if(!FilePlayers.getConfig().getBoolean(target.getDisplayName() + ".isMuted")){
                        player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_command_mute_notMuted)
                                .replace("%player%", target.getDisplayName()), TextTypes.NO_SUCCESS));
                        return true;
                    }
                    FilePlayers.getConfig().set(target.getDisplayName() + ".isMuted", false);
                    FilePlayers.saveConfig();
                    player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_command_mute_unmuted).replace("%player%", target.getDisplayName()),
                            TextTypes.SUCCESS));
                    target.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_command_mute_targetUnmuted), TextTypes.SUCCESS));
                }
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return null;
    }
}
