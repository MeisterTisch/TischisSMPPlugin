package user.meistertisch.tischissmpplugin.start;

import user.meistertisch.tischissmpplugin.Main;

public class SetupAtFirstTimeLaunch {
    SetupAtFirstTimeLaunch(){
        if(!Main.getPlugin().getConfig().getBoolean("firstTimeSetupDone")){

        } else ; //TODO: Message
    }
}
