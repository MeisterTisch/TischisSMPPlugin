package user.meistertisch.tischissmpplugin;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import user.meistertisch.tischissmpplugin.commands.CommandHome;
import user.meistertisch.tischissmpplugin.forTesting.TestCommand;
import user.meistertisch.tischissmpplugin.languages.Languages;
import user.meistertisch.tischissmpplugin.listeners.ListenerChat;
import user.meistertisch.tischissmpplugin.listeners.ListenerDimensionAllowance;
import user.meistertisch.tischissmpplugin.admin.*;
import user.meistertisch.tischissmpplugin.admin.CommandPrefix;
import user.meistertisch.tischissmpplugin.listeners.ListenerJoinAndLeave;
import user.meistertisch.tischissmpplugin.players.FilePlayers;
import user.meistertisch.tischissmpplugin.players.teleportation.ListenerMove;
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

        //Loading Files and other different things
        this.saveDefaultConfig();
        this.saveResource("languages/english.yml", true);
        this.saveResource("languages/german.yml", true);
        this.saveResource("bannedPlayers.yml", false);
        FilePlayers.setup();
        Teleportation.setup();

        //Some checks
        ConfigChecker.checkEverything();

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

        //Listeners
        pluginManager.registerEvents(new ListenerChat(), this);
        pluginManager.registerEvents(new ListenerDimensionAllowance(), this);
        pluginManager.registerEvents(new ListenerJoinAndLeave(), this);
        pluginManager.registerEvents(new ListenerMove(), this);

        //Misc

    }



    @Override
    public void onDisable() {
        //Stopping
        Teleportation.stopScheduler();
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
