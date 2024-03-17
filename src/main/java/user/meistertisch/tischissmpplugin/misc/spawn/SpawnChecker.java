package user.meistertisch.tischissmpplugin.misc.spawn;

import user.meistertisch.tischissmpplugin.Main;
import user.meistertisch.tischissmpplugin.languages.Text;
import user.meistertisch.tischissmpplugin.messageMaker.MessageMaker;
import user.meistertisch.tischissmpplugin.messageMaker.TextTypes;

public class SpawnChecker {
    public static void checkForSpawn(){
        if(Main.getPlugin().getConfig().getLocation("spawn.location") == null
                || !Main.getPlugin().getConfig().isLocation("spawn.location")){
            System.out.println(MessageMaker.makeMessage(Text.getText(Text.admin_command_spawn_notSetYet), TextTypes.NO_SUCCESS));
        }
    }
}
