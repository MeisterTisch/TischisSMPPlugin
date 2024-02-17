package user.meistertisch.tischissmpplugin.staff;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import user.meistertisch.tischissmpplugin.Main;
import user.meistertisch.tischissmpplugin.languages.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class commandChatDisabling implements TabExecutor {
    //TODO: ONLY STAFF
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(strings.length == 1){
            //length passt
            if(strings[0].toLowerCase(Locale.ROOT).equals("enable")
                    || strings[0].toLowerCase(Locale.ROOT).equals("disable")){
                //input is right
                boolean isChatDisabled = Main.getPlugin().getConfig().getBoolean("chatDisabled");
//                if(!isChatDisabled == )
            } else {
                //input is wrong
            }
        } else if (strings.length == 0){
            //checking able oder not
        } else {
            //nichts passt

        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        List<String> list = new ArrayList<>();
        if(strings.length == 1){
            list.addAll(List.of("enable", "disable"));
        }
        return list;
    }
}
