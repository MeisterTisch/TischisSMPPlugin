package user.meistertisch.tischissmpplugin.languages;

import org.bukkit.configuration.file.YamlConfiguration;
import user.meistertisch.tischissmpplugin.Main;

import java.io.File;

public enum Languages {
    GERMAN("german"),
    ENGLISH("english");

    String configValue;

    Languages(String configValue) {
        this.configValue = configValue;
    }

    public String getConfigValue() {
        return configValue;
    }

    public static YamlConfiguration getLanguageFileConfig(){
        switch (Main.getLanguage()) {
            case GERMAN -> {
                return YamlConfiguration.loadConfiguration(new File(Main.getPlugin().getDataFolder()+"/languages/", "german.yml"));
            }
            case ENGLISH -> {
                return YamlConfiguration.loadConfiguration(new File(Main.getPlugin().getDataFolder()+"/languages/", "english.yml"));
            }
            default -> {
                return null;
            }
        }
    }
}
