package user.meistertisch.tischissmpplugin.staff;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import user.meistertisch.tischissmpplugin.languages.Text;
import user.meistertisch.tischissmpplugin.messageMaker.MessageMaker;
import user.meistertisch.tischissmpplugin.messageMaker.TextTypes;
import user.meistertisch.tischissmpplugin.staff.commandPrefix.InventoryMaker;

import java.util.ArrayList;
import java.util.List;

public class CommandPrefix implements TabExecutor {
    //TODO: ONLY STAFF
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(strings.length == 0){
            //args passen
            if(commandSender instanceof Player player){
                //player passt, open Inv
                Inventory oldInv = player.getInventory();
                player.openInventory(InventoryMaker.getAnvilInventory());
            }
        } else {
            //invalid args length
            commandSender.sendMessage(MessageMaker.makeMessage(Text.getText(Text.staff_commands_invalidArgsLength), TextTypes.NO_SUCCESS));
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return new ArrayList<>();
    }
}
