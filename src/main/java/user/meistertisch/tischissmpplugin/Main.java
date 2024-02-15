package user.meistertisch.tischissmpplugin;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import user.meistertisch.tischissmpplugin.forTesting.TestCommand;

public final class Main extends JavaPlugin {
    private static Main plugin;
    PluginManager pluginManager;
    private static boolean colorsWork;
    private static String language;

    @Override
    public void onEnable() {
        //First
        plugin = this;
        pluginManager = Bukkit.getPluginManager();
        this.saveDefaultConfig();

        //Some checks
            //LANGUAGE CHECK

            //ACCENT COLOR CHECK
        colorsWork = this.getConfig().get("color.color1") instanceof Character && this.getConfig().get("color.color2") instanceof Character;


        //Commands
        getCommand("test").setExecutor(new TestCommand());

        //Listeners


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
}
