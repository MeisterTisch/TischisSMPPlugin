package user.meistertisch.tischissmpplugin.commands;

import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.LlamaSpit;
import org.bukkit.entity.Player;
import user.meistertisch.tischissmpplugin.languages.Text;
import user.meistertisch.tischissmpplugin.messageMaker.MessageMaker;
import user.meistertisch.tischissmpplugin.messageMaker.TextTypes;

import java.util.ArrayList;
import java.util.List;

public class CommandSpit implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player player){
            player.getWorld().spawn(player.getEyeLocation(), LlamaSpit.class,
                    llamaSpit -> llamaSpit.setVelocity(player.getEyeLocation().getDirection().multiply(0.75)));
            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_LLAMA_SPIT, SoundCategory.PLAYERS, 1, 1);
        } else {
            commandSender.sendMessage(MessageMaker.makeMessage(Text.getText(Text.commands_onlyPlayers), TextTypes.NO_SUCCESS));
        }
        return true;
    }
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return new ArrayList<>();
    }
}
