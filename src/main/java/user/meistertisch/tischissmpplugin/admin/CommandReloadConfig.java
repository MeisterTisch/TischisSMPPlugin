package user.meistertisch.tischissmpplugin.admin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import user.meistertisch.tischissmpplugin.Main;
import user.meistertisch.tischissmpplugin.languages.Text;
import user.meistertisch.tischissmpplugin.messageMaker.MessageMaker;
import user.meistertisch.tischissmpplugin.messageMaker.TextTypes;
import user.meistertisch.tischissmpplugin.players.FilePlayers;
import user.meistertisch.tischissmpplugin.start.ConfigChecker;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CommandReloadConfig implements TabExecutor {
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

        if(strings.length == 0){
            //keine args (good)
            if(new File(Main.getPlugin().getDataFolder(), "config.yml").exists()){
                Main.getPlugin().reloadConfig();
                ConfigChecker.checkEverything();
                commandSender.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_commands_reloadConfig_reloadSuccessful), TextTypes.SUCCESS));
            } else {
                Main.getPlugin().saveDefaultConfig();
                Main.getPlugin().reloadConfig();
                ConfigChecker.checkEverything();
                commandSender.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_commands_reloadConfig_fileNotFound), TextTypes.NO_SUCCESS));
            }
        } else {
            //some args (not good)
            commandSender.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_commands_invalidArgsLength), TextTypes.NO_SUCCESS));
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return new ArrayList<>();
    }
}
