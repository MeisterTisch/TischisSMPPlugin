package user.meistertisch.tischissmpplugin;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import user.meistertisch.tischissmpplugin.forTesting.TestCommand;
import user.meistertisch.tischissmpplugin.languages.Languages;
import user.meistertisch.tischissmpplugin.listeners.ListenerChat;
import user.meistertisch.tischissmpplugin.listeners.ListenerDimensionAllowance;
import user.meistertisch.tischissmpplugin.staff.CommandAnnouncement;
import user.meistertisch.tischissmpplugin.staff.CommandDimensionAllowance;
import user.meistertisch.tischissmpplugin.staff.CommandReloadConfig;
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
        this.saveDefaultConfig();

        //Loading Language Files
        plugin.saveResource("languages/english.yml", true);
        plugin.saveResource("languages/german.yml", true);

        //Some checks
        ConfigChecker.checkEverything();

        //Commands
        getCommand("test").setExecutor(new TestCommand());
        getCommand("announce").setExecutor(new CommandAnnouncement());
        getCommand("dimension").setExecutor(new CommandDimensionAllowance());
        getCommand("reloadconfig").setExecutor(new CommandReloadConfig());

        //Listeners
        pluginManager.registerEvents(new ListenerChat(), this);
        pluginManager.registerEvents(new ListenerDimensionAllowance(), this);

        //Misc

    }



    @Override
    public void onDisable() {
        // Plugin shutdown logic
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
