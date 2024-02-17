package user.meistertisch.tischissmpplugin;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import user.meistertisch.tischissmpplugin.forTesting.TestCommand;
import user.meistertisch.tischissmpplugin.languages.Languages;
import user.meistertisch.tischissmpplugin.languages.Text;
import user.meistertisch.tischissmpplugin.listeners.ListenerChat;
import user.meistertisch.tischissmpplugin.listeners.ListenerDimensionAllowance;
import user.meistertisch.tischissmpplugin.messageMaker.MessageMaker;
import user.meistertisch.tischissmpplugin.messageMaker.TextTypes;
import user.meistertisch.tischissmpplugin.staff.CommandAnnouncement;
import user.meistertisch.tischissmpplugin.staff.CommandDimensionAllowance;
import user.meistertisch.tischissmpplugin.staff.CommandReloadConfig;

import java.util.ArrayList;
import java.util.List;

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
        checkLanguages();
            //ACCENT COLOR CHECK
        checkAccentColor1();
        checkAccentColor2();
            //DIMENSION ALLOWANCE CHECK
        checkDimensionAllowance();


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

    public static Languages getLanguage(){
        return language;
    }

    //Checking Methods
    private void checkLanguages(){
        boolean languageMatches = false;
        for(Languages language : Languages.values()){
            if(language.getConfigValue().equals(this.getConfig().getString("language"))) {
                languageMatches = true;
                Main.language = language;
                break;
            }
        }
        if(!languageMatches){
            language = Languages.ENGLISH;
            this.getConfig().set("language", "english");
            this.saveConfig();
            this.reloadConfig();

            //invalid language error
            System.out.println(MessageMaker.makeMessage(Text.getText(Text.language_invalidLanguage), TextTypes.ERROR));
        }
    }
    private void checkAccentColor1(){
        //Input is String
        if(this.getConfig().getString("color.color1") != null) {
            //String has only one character
            if(this.getConfig().getString("color.color1").toCharArray().length == 1){
                //Only Colors
                List<Character> validColorChars = new ArrayList<>();
                for (char c = '0'; c <= '9'; c++) {
                    validColorChars.add(c);
                }
                validColorChars.addAll(List.of('a', 'b', 'c', 'd', 'e', 'f', 'A', 'B', 'C', 'D', 'E', 'F'));
                for(char chara : this.getConfig().getString("color.color1").toCharArray()){
                    if(validColorChars.contains(chara))
                        return;
                }
            }
        }
        //Accent Color invalid
        this.getConfig().set("color.color1", "9");
        this.saveConfig();
        this.reloadConfig();
        System.out.println(MessageMaker.makeMessage(Text.getText(Text.accentColor_invalidAccentColor1), TextTypes.ERROR));
    }
    private void checkAccentColor2(){
        //Input is String
        if(this.getConfig().getString("color.color2") != null)
            return;
        //String has only one character
        if(this.getConfig().getString("color.color2").toCharArray().length == 1)
            return;
        //Only Colors
        List<Character> validColorChars = new ArrayList<>();
        for (char c = '0'; c <= '9'; c++) {
            validColorChars.add(c);
        }
        validColorChars.addAll(List.of('a', 'b', 'c', 'd', 'e', 'f', 'A', 'B', 'C', 'D', 'E', 'F'));
        for(char chara : this.getConfig().getString("color.color2").toCharArray()){
            if(validColorChars.contains(chara))
                return;
        }
        //Accent Color invalid
        this.getConfig().set("color.color2", 'b');
        this.saveConfig();
        this.reloadConfig();
        System.out.println(MessageMaker.makeMessage(Text.getText(Text.accentColor_invalidAccentColor2), TextTypes.ERROR));
    }
    private void checkDimensionAllowance() {
        if(!(this.getConfig().get("dimensionAllowance.nether") instanceof Boolean)){
            System.out.println(MessageMaker.makeMessage(Text.getText(Text.dimensionAllowance_invalidAllowance)
                    .replace("%dimension%", "Nether"), TextTypes.ERROR));
            this.getConfig().set("dimensionAllowance.nether", true);
            this.saveConfig();
            this.reloadConfig();
        }
        if(!(this.getConfig().get("dimensionAllowance.end") instanceof Boolean)){
            System.out.println(MessageMaker.makeMessage(Text.getText(Text.dimensionAllowance_invalidAllowance)
                    .replace("%dimension%", "End"), TextTypes.ERROR));
            this.getConfig().set("dimensionAllowance.end", true);
            this.saveConfig();
            this.reloadConfig();
        }
    }
}
