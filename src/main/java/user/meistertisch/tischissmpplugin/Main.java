package user.meistertisch.tischissmpplugin;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import user.meistertisch.tischissmpplugin.forTesting.TestCommand;
import user.meistertisch.tischissmpplugin.languages.Languages;

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
            //LANGUAGE CHECK
        if(!checkLanguages()){
            language = Languages.ENGLISH;
            System.out.println("ERROR: Invalid Language. Setting to english!");
            //TODO: WRITE ERROR WITH PREFIX :)
        }
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

    public static Languages getLanguage(){
        return language;
    }

    //Methods for here
    private boolean checkLanguages(){
        boolean languageMatches = false;
        for(Languages language : Languages.values()){
            if(language.getConfigValue().equals(this.getConfig().getString("language"))) {
                languageMatches = true;
                Main.language = language;
                break;
            }
        }
        return languageMatches;
    }
}
