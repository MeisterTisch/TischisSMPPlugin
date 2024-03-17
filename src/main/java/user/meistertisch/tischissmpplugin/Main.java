package user.meistertisch.tischissmpplugin;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import user.meistertisch.tischissmpplugin.admin.teams.CommandTeams;
import user.meistertisch.tischissmpplugin.admin.teams.FileTeams;
import user.meistertisch.tischissmpplugin.commands.CommandHome;
import user.meistertisch.tischissmpplugin.commands.CommandSign;
import user.meistertisch.tischissmpplugin.commands.CommandSlimeChunks;
import user.meistertisch.tischissmpplugin.commands.CommandSpawn;
import user.meistertisch.tischissmpplugin.commands.dm.CommandDirectMessage;
import user.meistertisch.tischissmpplugin.commands.dm.DirectMessageScheduler;
import user.meistertisch.tischissmpplugin.commands.tpr.CommandTeleportRequest;
import user.meistertisch.tischissmpplugin.commands.tpr.TPRScheduler;
import user.meistertisch.tischissmpplugin.forTesting.TestCommand;
import user.meistertisch.tischissmpplugin.languages.Languages;
import user.meistertisch.tischissmpplugin.listeners.ListenerChat;
import user.meistertisch.tischissmpplugin.listeners.ListenerDeathOfPlayer;
import user.meistertisch.tischissmpplugin.listeners.ListenerDimensionAllowance;
import user.meistertisch.tischissmpplugin.admin.*;
import user.meistertisch.tischissmpplugin.admin.CommandPrefix;
import user.meistertisch.tischissmpplugin.listeners.ListenerJoinAndLeave;
import user.meistertisch.tischissmpplugin.misc.spawn.ListenerSpawn;
import user.meistertisch.tischissmpplugin.misc.spawn.SpawnChecker;
import user.meistertisch.tischissmpplugin.players.elytra.ElytraFlyAway;
import user.meistertisch.tischissmpplugin.players.FilePlayers;
import user.meistertisch.tischissmpplugin.globalListeners.ListenerMove;
import user.meistertisch.tischissmpplugin.players.elytra.ListenerForElytra;
import user.meistertisch.tischissmpplugin.players.teleportation.Teleportation;
import user.meistertisch.tischissmpplugin.start.ConfigChecker;

public final class Main extends JavaPlugin {
    private static Main plugin;
    PluginManager pluginManager;
    private static boolean colorsWork;
    private static Languages language;

    @Override
    public void onEnable() {
        //First
        plugin = this;
        pluginManager = Bukkit.getPluginManager();

        //Setups and so on
            //Files
        this.saveDefaultConfig();
        this.saveResource("languages/english.yml", true);
        this.saveResource("languages/german.yml", true);
        FilePlayers.setup();
        FileTeams.setup();
            //Schedulers
        Teleportation.setup();
        TPRScheduler.setup();
        DirectMessageScheduler.setup();
        ElytraFlyAway.setup();

        //Some checks
        ConfigChecker.checkEverything();
        SpawnChecker.checkForSpawn();

        //Commands
        getCommand("test").setExecutor(new TestCommand());
        getCommand("announce").setExecutor(new CommandAnnouncement());
        getCommand("dimension").setExecutor(new CommandDimensionAllowance());
        getCommand("reloadconfig").setExecutor(new CommandReloadConfig());
        getCommand("chat").setExecutor(new CommandChatDisabling());
        getCommand("prefix").setExecutor(new CommandPrefix());
        getCommand("language").setExecutor(new CommandLanguage());
        getCommand("admin").setExecutor(new CommandAdmin());
        getCommand("home").setExecutor(new CommandHome());
        getCommand("tpr").setExecutor(new CommandTeleportRequest());
        getCommand("dm").setExecutor(new CommandDirectMessage());
        getCommand("teams").setExecutor(new CommandTeams());
        getCommand("sign").setExecutor(new CommandSign());
        getCommand("slimechunk").setExecutor(new CommandSlimeChunks());
        getCommand("spawn").setExecutor(new CommandSpawn());
        getCommand("start").setExecutor(new CommandStart());

        //Listeners
        pluginManager.registerEvents(new ListenerChat(), this);
        pluginManager.registerEvents(new ListenerDimensionAllowance(), this);
        pluginManager.registerEvents(new ListenerJoinAndLeave(), this);
        pluginManager.registerEvents(new ListenerMove(), this);
        pluginManager.registerEvents(new ListenerDeathOfPlayer(), this);
        pluginManager.registerEvents(new ListenerForElytra(), this);
        pluginManager.registerEvents(new ListenerSpawn(), this);

        //Misc

    }



    @Override
    public void onDisable() {
        //Stopping
        Teleportation.stopScheduler();
        TPRScheduler.stopScheduler();
        DirectMessageScheduler.stopScheduler();
        ElytraFlyAway.stop();
    }

    //Some static Getters
    public static Main getPlugin() {
        return plugin;
    }

    public static boolean getColorsWork() {
        return colorsWork;
    }

    //for language variable
    public static Languages getLanguage(){
        return language;
    }
    public static void setLanguage(Languages language) {
        Main.language = language;
    }
}
