package user.meistertisch.tischissmpplugin.staff;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import user.meistertisch.tischissmpplugin.Main;
import user.meistertisch.tischissmpplugin.languages.Languages;
import user.meistertisch.tischissmpplugin.languages.Text;
import user.meistertisch.tischissmpplugin.messageMaker.MessageMaker;
import user.meistertisch.tischissmpplugin.messageMaker.TextTypes;
import user.meistertisch.tischissmpplugin.start.ConfigChecker;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CommandLanguage implements TabExecutor {
    //TODO: Staff only
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(strings.length == 0){
            commandSender.sendMessage(MessageMaker.makeMessage(Text.getText(Text.staff_commands_language_statusCheck), TextTypes.NORMAL));
        } else if(strings.length == 1){
            //Getting all possible languages
            List<String> langList = new ArrayList<>();
            for(Languages lang : Languages.values()){
                langList.add(lang.getConfigValue());
            }

            if(langList.contains(strings[0].toLowerCase(Locale.ROOT))){
                //gültige lang
                if(Main.getPlugin().getConfig().getString("language").equals(strings[0].toLowerCase(Locale.ROOT))){
                    //Lang dasselbe
                    commandSender.sendMessage(MessageMaker.makeMessage(Text.getText(Text.staff_commands_language_statusAlreadyThere), TextTypes.NO_SUCCESS));
                } else {
                    //Lang nicht dasselbe
                    Main.getPlugin().getConfig().set("language", strings[0].toLowerCase(Locale.ROOT));
                    Main.getPlugin().saveConfig();
                    Main.getPlugin().reloadConfig();
                    ConfigChecker.checkEverything();

                    commandSender.sendMessage(MessageMaker.makeMessage(Text.getText(Text.staff_commands_language_languageChanged), TextTypes.SUCCESS));
                }
            } else {
                //NICHT gültige lang
                commandSender.sendMessage(MessageMaker.makeMessage(Text.getText(Text.staff_commands_language_invalidLanguage), TextTypes.NO_SUCCESS));
            }
        } else {
            commandSender.sendMessage(MessageMaker.makeMessage(Text.getText(Text.staff_commands_invalidArgsLength), TextTypes.NO_SUCCESS));
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if(strings.length == 1){
            List<String> langList = new ArrayList<>();
            for(Languages lang : Languages.values()){
                langList.add(lang.getConfigValue());
            }
            return langList;
        }
        return new ArrayList<>();
    }
}
