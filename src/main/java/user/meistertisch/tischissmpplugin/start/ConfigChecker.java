package user.meistertisch.tischissmpplugin.start;

import user.meistertisch.tischissmpplugin.Main;
import user.meistertisch.tischissmpplugin.languages.Languages;
import user.meistertisch.tischissmpplugin.languages.Text;
import user.meistertisch.tischissmpplugin.messageMaker.MessageMaker;
import user.meistertisch.tischissmpplugin.messageMaker.TextTypes;

import java.util.ArrayList;
import java.util.List;

public class ConfigChecker {
    //Checking Methods
    private static void checkLanguages(){
        boolean languageMatches = false;
        for(Languages language : Languages.values()){
            if(language.getConfigValue().equals(Main.getPlugin().getConfig().getString("language"))) {
                languageMatches = true;
                Main.setLanguage(language);
                break;
            }
        }
        if(!languageMatches){
            Main.setLanguage(Languages.ENGLISH);
            Main.getPlugin().getConfig().set("language", "english");
            Main.getPlugin().saveConfig();
            Main.getPlugin().reloadConfig();

            //invalid language error
            System.out.println(MessageMaker.makeMessage(Text.getText(Text.language_invalidLanguage), TextTypes.ERROR));
        }
    }
    private static void checkAccentColor1(){
        //Input is String
        if(Main.getPlugin().getConfig().getString("color.color1") != null) {
            //String has only one character
            if(Main.getPlugin().getConfig().getString("color.color1").toCharArray().length == 1){
                //Only Colors
                List<Character> validColorChars = new ArrayList<>();
                for (char c = '0'; c <= '9'; c++) {
                    validColorChars.add(c);
                }
                validColorChars.addAll(List.of('a', 'b', 'c', 'd', 'e', 'f', 'A', 'B', 'C', 'D', 'E', 'F'));
                for(char chara : Main.getPlugin().getConfig().getString("color.color1").toCharArray()){
                    if(validColorChars.contains(chara))
                        return;
                }
            }
        }
        //Accent Color invalid
        Main.getPlugin().getConfig().set("color.color1", "9");
        Main.getPlugin().saveConfig();
        Main.getPlugin().reloadConfig();
        System.out.println(MessageMaker.makeMessage(Text.getText(Text.accentColor_invalidAccentColor1), TextTypes.ERROR));
    }
    private static void checkAccentColor2(){
        //Input is String
        if(Main.getPlugin().getConfig().getString("color.color2") != null)
            return;
        //String has only one character
        if(Main.getPlugin().getConfig().getString("color.color2").toCharArray().length == 1)
            return;
        //Only Colors
        List<Character> validColorChars = new ArrayList<>();
        for (char c = '0'; c <= '9'; c++) {
            validColorChars.add(c);
        }
        validColorChars.addAll(List.of('a', 'b', 'c', 'd', 'e', 'f', 'A', 'B', 'C', 'D', 'E', 'F'));
        for(char chara : Main.getPlugin().getConfig().getString("color.color2").toCharArray()){
            if(validColorChars.contains(chara))
                return;
        }
        //Accent Color invalid
        Main.getPlugin().getConfig().set("color.color2", 'b');
        Main.getPlugin().saveConfig();
        Main.getPlugin().reloadConfig();
        System.out.println(MessageMaker.makeMessage(Text.getText(Text.accentColor_invalidAccentColor2), TextTypes.ERROR));
    }
    private static void checkDimensionAllowance() {
        if(!(Main.getPlugin().getConfig().get("dimensionAllowance.nether") instanceof Boolean)){
            System.out.println(MessageMaker.makeMessage(Text.getText(Text.dimensionAllowance_invalidAllowance)
                    .replace("%dimension%", "Nether"), TextTypes.ERROR));
            Main.getPlugin().getConfig().set("dimensionAllowance.nether", true);
            Main.getPlugin().saveConfig();
            Main.getPlugin().reloadConfig();
        }
        if(!(Main.getPlugin().getConfig().get("dimensionAllowance.end") instanceof Boolean)){
            System.out.println(MessageMaker.makeMessage(Text.getText(Text.dimensionAllowance_invalidAllowance)
                    .replace("%dimension%", "End"), TextTypes.ERROR));
            Main.getPlugin().getConfig().set("dimensionAllowance.end", true);
            Main.getPlugin().saveConfig();
            Main.getPlugin().reloadConfig();
        }
    }
    private static void checkChatDisabling(){
        if(!(Main.getPlugin().getConfig().get("chatDisabled") instanceof Boolean)){
            System.out.println(MessageMaker.makeMessage(Text.getText(Text.chatDisabled_invalidInput)
                    .replace("%dimension%", "Nether"), TextTypes.ERROR));
            Main.getPlugin().getConfig().set("chatDisabled", false);
            Main.getPlugin().saveConfig();
            Main.getPlugin().reloadConfig();
        }
    }

    private static void checkPrefix(){
        String prefix = Main.getPlugin().getConfig().getString("prefix");
        if(!String.valueOf(prefix.charAt(prefix.length() - 1)).equals(" ")){
            System.out.println(MessageMaker.makeMessage(Text.getText(Text.prefix_noSpaceAtTheEnd), TextTypes.ERROR));
            Main.getPlugin().getConfig().set("prefix", Main.getPlugin().getConfig().getString("prefix") + " ");
            Main.getPlugin().saveConfig();
            Main.getPlugin().reloadConfig();
        }
    }

    private static void checkHomes(){
        if(!Main.getPlugin().getConfig().isBoolean("homes.status")){
            System.out.println(MessageMaker.makeMessage(Text.getText(Text.homes_invalidStatus), TextTypes.ERROR));
            Main.getPlugin().getConfig().set("homes.status", true);
            Main.getPlugin().saveConfig();
            Main.getPlugin().reloadConfig();
        }
        if(!Main.getPlugin().getConfig().isInt("homes.maxNumber")){
            System.out.println(MessageMaker.makeMessage(Text.getText(Text.homes_invalidNumber), TextTypes.ERROR));
            Main.getPlugin().getConfig().set("homes.maxNumber", 5);
            Main.getPlugin().saveConfig();
            Main.getPlugin().reloadConfig();
        }
    }

    //OVERALL CHECKER
    public static void checkEverything(){
        //LANGUAGE CHECK
        checkLanguages();
        //ACCENT COLOR CHECK
        checkAccentColor1();
        checkAccentColor2();
        //DIMENSION ALLOWANCE CHECK
        checkDimensionAllowance();
        //CHAT DISABLEMENT CHECK
        checkChatDisabling();
        //PREFIX CHECKER FOR SPACE AFTER PREFIX
        checkPrefix();
        //CHECK FOR HOMES
        checkHomes();
    }
}
