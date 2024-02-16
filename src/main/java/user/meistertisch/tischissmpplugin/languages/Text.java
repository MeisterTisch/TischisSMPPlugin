package user.meistertisch.tischissmpplugin.languages;

public enum Text {
    color_invalidAccentColor1("accentColor.invalidAccentColor1"),
    color_invalidAccentColor2("accentColor.invalidAccentColor2"),
    language_invalidLanguage("language.invalidLanguage"),
    staff_announcement("staff.announcement"),
    staff_commands_announcement_invalidMessageLength("staff.commands.announcement.invalidMessageLength"),
    staff_error("staff.error"),
    staff_important("staff.important"),
    testCommand("testCommand");

    private String languageFilePath;

    Text(String languageFilePath) {
        this.languageFilePath = languageFilePath;
    }

    public String getLanguageFilePath() {
        return languageFilePath;
    }

    public static String getText(Text text){
        return Languages.getLanguageFileConfig().getString(text.getLanguageFilePath());
    }
}
