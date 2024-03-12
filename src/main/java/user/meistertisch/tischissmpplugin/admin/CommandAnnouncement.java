package user.meistertisch.tischissmpplugin.admin;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import user.meistertisch.tischissmpplugin.languages.Text;
import user.meistertisch.tischissmpplugin.messageMaker.MessageMaker;
import user.meistertisch.tischissmpplugin.messageMaker.TextTypes;
import user.meistertisch.tischissmpplugin.players.FilePlayers;

public class CommandAnnouncement implements CommandExecutor {
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

        if(strings.length > 0){
            StringBuilder message = new StringBuilder();
            for(String string : strings){
                message.append(" ").append(string);
            }
            Bukkit.broadcastMessage(MessageMaker.makeMessage(message.toString(), TextTypes.ANNOUNCEMENT));
            for(Player player : Bukkit.getOnlinePlayers()){
                player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 1,1,1);
            }
        } else commandSender.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_commands_announcement_invalidMessageLength), TextTypes.NO_SUCCESS));
        return true;
    }
}
