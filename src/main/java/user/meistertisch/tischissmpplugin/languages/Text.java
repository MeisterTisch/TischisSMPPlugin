package user.meistertisch.tischissmpplugin.languages;

public enum Text {
    accentColor_invalidAccentColor1("accentColor.invalidAccentColor1"),
    accentColor_invalidAccentColor2("accentColor.invalidAccentColor2"),
    announcement("announcement"),
    ban_withoutDuration("ban.withoutDuration"),
    ban_withDuration("ban.withDuration"),
    command_home_creation_created("commands.home.creation.created"),
    command_home_creation_noName("commands.home.creation.noName"),
    command_home_creation_sameName("commands.home.creation.sameName"),
    command_home_teleportation_teleported("commands.home.teleportation.teleported"),
    command_home__teleportation_invalidHome("commands.home.teleportation.invalidHome"),
    command_home_deletion_deleted("commands.home.deletion.deleted"),
    command_home_deletion_invalidHome("commands.home.deletion.invalidHome"),
    command_home_deletion_noName("commands.home.deletion.noName"),
    command_home_renaming_renamed("commands.home.renaming.renamed"),
    command_home_renaming_noHome("commands.home.renaming.noHome"),
    command_home_renaming_invalidHome("commands.home.renaming.invalidHome"),
    command_tpr_targetNotAccepting("commands.tpr.targetNotAccepting"),
    command_tpr_noOpenRequest("commands.tpr.noOpenRequest"),
    teleportation_tpr_target_canceled("teleportation.tpr.target.canceled"),
    teleportation_tpr_target_successful("teleportation.tpr.target.successful"),
    command_tpr_cancelFirst("commands.tpr.cancelFirst"),
    command_tpr_acceptOrRejectFirst("commands.tpr.acceptOrRejectFirst"),
    command_tpr_requestCanceled("commands.tpr.requestCanceled"),
    command_tpr_requestCanceledTarget("commands.tpr.requestCanceledTarget"),
    command_tpr_senderIsTarget("commands.tpr.senderIsTarget"),
    command_dm_noPlayer("commands.dm.noPlayer"),
    command_dm_playerOffline("commands.dm.playerOffline"),
    command_dm_noOneToReply("commands.dm.noOneToReply"),
    command_dm_noMessage("commands.dm.noMessage"),
    command_dm_youSending("commands.dm.youSending"),
    command_dm_youReceiving("commands.dm.youReceiving"),
    command_dm_senderIsTarget("commands.dm:senderIsTarget"),
    command_invalidInput("commands.invalidInput"),
    command_tpr_toggle_alreadyOn("commands.tpr.toggle.alreadyOn"),
    command_tpr_toggle_alreadyOff("commands.tpr.toggle.alreadyOff"),
    command_tpr_rejectedToTarget("commands.tpr.rejectedToTarget"),
    command_tpr_rejectedToSender("commands.tpr.rejectedToSender"),
    command_tpr_acceptedToTarget("commands.tpr.acceptedToTarget"),
    command_tpr_acceptedToSender("commands.tpr.acceptedToSender"),
    command_tpr_requestExpiredSender("commands.tpr.requestExpiredSender"),
    command_tpr_requestExpiredTarget("commands.tpr.requestExpiredTarget"),
    command_tpr_to_msgSentSender("commands.tpr.to.msgSentSender"),
    command_tpr_to_msgSentTarget("commands.tpr.to.msgSentTarget"),
    command_tpr_here_msgSentSender("commands.tpr.here.msgSentSender"),
    command_tpr_here_msgSentTarget("commands.tpr.here.msgSentTarget"),
    command_tpr_toggle_on("commands.tpr.toggle.makingOn"),
    command_tpr_toggle_off("commands.tpr.toggle.makingOff"),
    command_tpr_noPlayerSelected("commands.tpr.noPlayerSelected"),
    command_tpr_playerOffline("commands.tpr.playerOffline"),
    command_home_renaming_noName("commands.home.renaming.noName"),
    command_home_teleport_noHome("commands.home.teleport.noHome"),
    command_home_teleport_invalidHome("commands.home.teleport.invalidHome"),
    command_home_list("commands.home.list"),
    admin_noAdmin("admin.noAdmin"),
    command_invalidArgsLength("commands.invalidArgsLength"),
    command_home_creation_maxAmountReached("commands.home.creation.maxAmountReached"),
    command_home_deletion_confirm("commands.home.deletion.confirm"),
    chatDisabled_invalidInput("chatDisabled.invalidInput"),
    chatDisabled_chatIsDisabled("chatDisabling.chatIsDisabled"),
    dimensionAllowance_invalidAllowance("dimensionAllowance.invalidAllowance"),
    dimensionAllowance_netherNotAllowed("dimensionAllowance.netherNotAllowed"),
    dimensionAllowance_endNotAllowed("dimensionAllowance.endNotAllowed"),
    error("error"),
    important("important"),
    language_invalidLanguage("language.invalidLanguage"),
    admin_commands_announcement_invalidMessageLength("admin.commands.announcement.invalidMessageLength"),
    admin_commands_ban_playerAlreadyBanned("admin.commands.ban.playerAlreadyBanned"),
    admin_commands_ban_playerBanned("admin.commands.ban.playerBanned"),
    admin_commands_ban_playerNotBanned("admin.commands.ban.playerNotBanned"),
    admin_commands_ban_playerUnbanned("admin.commands.ban.playerUnbanned"),
    admin_commands_ban_noPlayer("admin.commands.ban.noPlayer"),
    admin_commands_ban_noReason("admin.commands.ban.noReason"),
    admin_commands_ban_withReason("admin.commands.ban.withReason"),
    admin_commands_dimension_invalidDimension("admin.commands.dimension.invalidDimension"),
    admin_commands_dimension_noDimension("admin.commands.dimension.noDimension"),
    admin_commands_dimension_invalidAllowance("admin.commands.dimension.invalidAllowance"),
    admin_commands_dimension_noAllowance("admin.commands.dimension.noAllowance"),
    admin_commands_invalidArgsLength("admin.commands.invalidArgsLength"),
    commands_onlyPlayers("commands.onlyPlayers"),
    admin_commands_dimension_alreadySameAllowance("admin.commands.dimension.alreadySameAllowance"),
    admin_commands_dimension_statusCheck("admin.commands.dimension.statusCheck"),
    admin_commands_dimension_successfulExecution_text("admin.commands.dimension.successfulExecution.text"),
    admin_commands_dimension_successfulExecution_announceText("admin.commands.dimension.successfulExecution.announceText"),
    admin_commands_dimension_successfulExecution_dimension_nether("admin.commands.dimension.successfulExecution.dimension.nether"),
    admin_commands_dimension_successfulExecution_dimension_end("admin.commands.dimension.successfulExecution.dimension.end"),
    admin_commands_dimension_successfulExecution_allowance_allow("admin.commands.dimension.successfulExecution.allowance.allow"),
    admin_commands_dimension_successfulExecution_allowance_disallow("admin.commands.dimension.successfulExecution.allowance.disallow"),
    admin_commands_reloadConfig_reloadSuccessful("admin.commands.reloadConfig.reloadSuccessful"),
    admin_commands_reloadConfig_reloadUnsuccessful("admin.commands.reloadConfig.reloadUnsuccessful"),
    admin_commands_reloadConfig_fileNotFound("admin.commands.reloadConfig.fileNotFound"),
    admin_commands_chatDisabling_statusAsking("admin.commands.chatDisabling.statusAsking"),
    admin_commands_chatDisabling_enabled("admin.commands.chatDisabling.enabled"),
    admin_commands_chatDisabling_disabled("admin.commands.chatDisabling.disabled"),
    admin_commands_chatDisabling_status_enabled("admin.commands.chatDisabling.status.enabled"),
    admin_commands_chatDisabling_status_disabled("admin.commands.chatDisabling.status.disabled"),
    admin_commands_chatDisabling_statusAlreadyThere("admin.commands.chatDisabling.statusAlreadyThere"),
    admin_commands_chatDisabling_invalidStatusInput("admin.commands.chatDisabling.invalidStatusInput"),
    admin_commands_prefix_changeInConfig("admin.commands.prefix.changeInConfig"),
    admin_commands_language_invalidLanguage("admin.commands.language.invalidLanguage"),
    admin_commands_language_languageChanged("admin.commands.language.languageChanged"),
    admin_commands_language_statusCheck("admin.commands.language.statusCheck"),
    admin_commands_language_statusAlreadyThere("admin.commands.language.statusAlreadyThere"),
    prefix_noSpaceAtTheEnd("prefix.noSpaceAtTheEnd"),
    homes_invalidStatus("homes.invalidStatus"),
    homes_invalidNumber("homes.invalidNumber"),
    admin_commands_admin_playerAdded("admin.commands.admin.playerAdded"),
    admin_commands_admin_playerRemoved("admin.commands.admin.playerRemoved"),
    admin_commands_admin_playerAlreadyAdmin("admin.commands.admin.playerAlreadyAdmin"),
    admin_commands_admin_playerNotAdmin("admin.commands.admin.playerNotAdmin"),
    admin_commands_admin_target_addedToAdmin("admin.commands.admin.target.addedToAdmin"),
    admin_commands_admin_target_removedFromAdmin("admin.commands.admin.target.removedFromAdmin"),
    admin_commands_admin_target_sameAsSender("admin.commands.admin.target.sameAsSender"),
    admin_commands_admin_noPlayerForAdding("admin.commands.admin.noPlayerForAddind"),
    admin_commands_admin_noPlayerForRemoving("admin.commands.admin.noPlayerForRemoving"),
    admin_commands_admin_invalidInput("admin.commands.admin.invalidInput"),
    command_home_renaming_sameName("commands.home.renaming.sameName"),
    teleportation_successful("teleportation.successful"),
    teleportation_canceled("teleportation.canceled"),
    teleportation_dontMove("teleportation.dontMove"),
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
