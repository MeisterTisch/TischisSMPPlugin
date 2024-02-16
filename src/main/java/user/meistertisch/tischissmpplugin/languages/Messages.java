package user.meistertisch.tischissmpplugin.languages;

import user.meistertisch.tischissmpplugin.Main;

public enum Messages {
    testCommand("testCommand"),
    message2("message.2");

    private String languageFilePath;

    Messages(String languageFilePath) {
        this.languageFilePath = languageFilePath;
    }

    public String getLanguageFilePath() {
        return languageFilePath;
    }

    public static String getMessage(Messages message){
        return Languages.getLanguageFileConfig().getString(message.getLanguageFilePath());
    }
}
