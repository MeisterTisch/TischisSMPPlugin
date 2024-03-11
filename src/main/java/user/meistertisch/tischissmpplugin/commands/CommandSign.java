package user.meistertisch.tischissmpplugin.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import user.meistertisch.tischissmpplugin.languages.Text;
import user.meistertisch.tischissmpplugin.messageMaker.MessageMaker;
import user.meistertisch.tischissmpplugin.messageMaker.TextTypes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommandSign implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player player){
            if(strings.length == 0){
                if(!(player.getInventory().getItemInMainHand().getType() == Material.AIR)){
                    ItemStack item = player.getInventory().getItemInMainHand();
                    ItemMeta meta = item.getItemMeta();
                    meta.setLore(Collections.singletonList(Text.getText(Text.command_sign_text).replace("%player%", player.getDisplayName())));
                    item.setItemMeta(meta);
                    player.getInventory().setItemInMainHand(item);
                    player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.command_sign_signed), TextTypes.SUCCESS));
                } else {
                    player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.command_sign_noItem), TextTypes.NO_SUCCESS));
                }
            } else {
                player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.command_invalidArgsLength), TextTypes.NO_SUCCESS));
            }
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
