package user.meistertisch.tischissmpplugin.languages;

public enum Text {
    accentColor_invalidAccentColor1("accentColor.invalidAccentColor1"),
    accentColor_invalidAccentColor2("accentColor.invalidAccentColor2"),
    announcement("announcement"),
    dimensionAllowance_invalidAllowance("dimensionAllowance.invalidAllowance"),
    dimensionAllowance_netherNotAllowed("dimensionAllowance.netherNotAllowed"),
    dimensionAllowance_endNotAllowed("dimensionAllowance.endNotAllowed"),
    error("error"),
    important("important"),
    language_invalidLanguage("language.invalidLanguage"),
    staff_commands_announcement_invalidMessageLength("staff.commands.announcement.invalidMessageLength"),
    staff_commands_dimension_invalidDimension("staff.commands.dimension.invalidDimension"),
    staff_commands_dimension_noDimension("staff.commands.dimension.noDimension"),
    staff_commands_dimension_invalidAllowance("staff.commands.dimension.invalidAllowance"),
    staff_commands_dimension_noAllowance("staff.commands.dimension.noAllowance"),
    staff_commands_invalidArgsLength("staff.commands.invalidArgsLength"),
    staff_commands_dimension_alreadyAllowed("staff.commands.dimension.alreadyAllowed"),
    staff_commands_dimension_alreadyDisallowed("staff.commands.dimension.alreadyDisallowed"),
    staff_commands_dimension_successfulExecution_text("staff.commands.dimension.successfulExecution.text"),
    staff_commands_dimension_successfulExecution_announceText("staff.commands.dimension.successfulExecution.announceText"),
    staff_commands_dimension_successfulExecution_dimension_nether("staff.commands.dimension.successfulExecution.dimension.nether"),
    staff_commands_dimension_successfulExecution_dimension_end("staff.commands.dimension.successfulExecution.dimension.end"),
    staff_commands_dimension_successfulExecution_allowance_allow("staff.commands.dimension.successfulExecution.allowance.allow"),
    staff_commands_dimension_successfulExecution_allowance_disallow("staff.commands.dimension.successfulExecution.allowance.disallow"),
    staff_commands_reloadConfig_reloadSuccessful("staff.commands.reloadConfig.reloadSuccessful"),
    getStaff_commands_reloadConfig_reloadUnsuccessful("staff.commands.reloadConfig.reloadUnsuccessful"),
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
    public static String getText(String enumName){
        return Languages.getLanguageFileConfig().getString(Text.valueOf(enumName).getLanguageFilePath());
    }
}
