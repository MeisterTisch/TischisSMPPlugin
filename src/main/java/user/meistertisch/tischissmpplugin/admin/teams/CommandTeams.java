package user.meistertisch.tischissmpplugin.admin.teams;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import user.meistertisch.tischissmpplugin.languages.Text;
import user.meistertisch.tischissmpplugin.messageMaker.MessageMaker;
import user.meistertisch.tischissmpplugin.messageMaker.TextTypes;
import user.meistertisch.tischissmpplugin.misc.PlayerListPrefix;
import user.meistertisch.tischissmpplugin.players.FilePlayers;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CommandTeams implements TabExecutor {
    private final List<String> colorsList = new ArrayList<>(List.of("black", "dark_blue", "dark_green", "dark_aqua", "dark_red", "dark_purple", "gold",
            "gray", "dark_gray", "blue", "green", "aqua", "red", "light_purple", "yellow", "white"));
    private final List<String> effectList = new ArrayList<>(List.of("strikethrough", "underlined", "italic", "none"));

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player player) {
            if (strings.length == 0) {
                player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_command_teams_noAction), TextTypes.NO_SUCCESS));
            } else {
                if (strings[0].toLowerCase(Locale.ROOT).equals("chat")) {
                    //Teamchat
                    if (FilePlayers.getConfig().getString(player.getDisplayName() + ".team") == null) {
                        player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.command_teams_chatNoTeam), TextTypes.NO_SUCCESS));
                        return true;
                    }

                    if (strings.length == 1) {
                        boolean isInTeamChat = FilePlayers.getConfig().getBoolean(player.getDisplayName() + ".isInTeamChat");
                        FilePlayers.getConfig().set(player.getDisplayName() + ".isInTeamChat", !isInTeamChat);
                        FilePlayers.saveConfig();
                        if (!isInTeamChat) {
                            player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.command_teams_chatOn), TextTypes.SUCCESS));
                        } else {
                            player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.command_teams_chatOff), TextTypes.NO_SUCCESS));
                        }
                    } else if (strings.length == 2) {
                        boolean isInTeamChat = FilePlayers.getConfig().getBoolean(player.getDisplayName() + ".isInTeamChat");
                        if (strings[1].toLowerCase(Locale.ROOT).equals("on")) {
                            if (!isInTeamChat) {
                                FilePlayers.getConfig().set(player.getDisplayName() + ".isInTeamChat", true);
                                FilePlayers.saveConfig();
                                player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.command_teams_chatOn), TextTypes.SUCCESS));
                            } else {
                                player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.command_teams_alreadyOn), TextTypes.NO_SUCCESS));
                            }
                        } else if (strings[1].toLowerCase(Locale.ROOT).equals("off")) {
                            if (isInTeamChat) {
                                FilePlayers.getConfig().set(player.getDisplayName() + ".isInTeamChat", false);
                                FilePlayers.saveConfig();
                                player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.command_teams_chatOff), TextTypes.NO_SUCCESS));
                            } else {
                                player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.command_teams_alreadyOff), TextTypes.NO_SUCCESS));
                            }
                        } else {
                            player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.command_invalidInput), TextTypes.NO_SUCCESS));
                        }
                    } else {
                        player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_commands_invalidArgsLength), TextTypes.NO_SUCCESS));
                    }
                    return true;
                } else {
                    //Admin stuff, heres a check
                    boolean isAdmin = FilePlayers.getConfig().getBoolean(player.getDisplayName() + ".isAdmin");
                    if (!isAdmin) {
                        player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_noAdmin), TextTypes.NO_SUCCESS));
                        return true;
                    }
                    switch (strings[0].toLowerCase(Locale.ROOT)) {
                        case "create" -> {
                            if (strings.length == 1) {
                                player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_command_teams_noName), TextTypes.NO_SUCCESS));
                            } else if (strings.length == 2) {
                                player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_command_teams_createAndEdit_noColor), TextTypes.NO_SUCCESS));
                            } else if (strings.length == 3 || strings.length == 4) {
                                String team = strings[1];
                                String color = strings[2].toLowerCase(Locale.ROOT);
                                String effect = "";
                                if (strings.length == 4) {
                                    effect = strings[3].toLowerCase(Locale.ROOT);
                                    //check for valid effect
                                    if (!effectList.contains(effect)) {
                                        player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_command_teams_createAndEdit_invalidEffect)
                                                .replace("%effect%", effect), TextTypes.NO_SUCCESS));
                                        return true;
                                    }
                                } else {
                                    effect = "none";
                                }

                                if (colorsList.contains(color)) {
                                    //Valid Color
                                    if (!FileTeams.getConfig().getStringList("takenColors").contains(color)) {
                                        //Color not taken
                                        if (!FileTeams.getConfig().getStringList("takenNames").contains(team)) {
                                            //name not taken
                                            FileTeams.getConfig().set(color + ".name", team);
                                            FileTeams.getConfig().set(color + ".effect", effect);
                                            List<String> takenColors = FileTeams.getConfig().getStringList("takenColors");
                                            takenColors.add(color);
                                            FileTeams.getConfig().set("takenColors", takenColors);
                                            List<String> takenNames = FileTeams.getConfig().getStringList("takenNames");
                                            takenNames.add(team);
                                            FileTeams.getConfig().set("takenNames", takenNames);
                                            FileTeams.saveConfig();
                                            if (effect.equals("none")) {
                                                player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_command_teams_createAndEdit_createdWithoutEffect)
                                                        .replace("%team%", team).replace("%color%", color), TextTypes.SUCCESS));
                                            } else {
                                                player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_command_teams_createAndEdit_createdWithEffect)
                                                        .replace("%team%", team).replace("%color%", color).replace("%effect%", effect), TextTypes.SUCCESS));
                                            }
                                        } else {
                                            //name taken
                                            player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_command_teams_createAndEdit_nameTaken)
                                                    .replace("%name%", team), TextTypes.NO_SUCCESS));
                                        }
                                    } else {
                                        //color Taken
                                        player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_command_teams_createAndEdit_colorTaken)
                                                .replace("%color%", color), TextTypes.NO_SUCCESS));
                                    }
                                } else {
                                    //invalid Color
                                    player.sendMessage(colorsList.toString());
                                    player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_command_teams_createAndEdit_invalidColor)
                                            .replace("%color%", color), TextTypes.NO_SUCCESS));
                                }
                            } else {
                                player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.command_invalidArgsLength), TextTypes.NO_SUCCESS));
                            }
                        }
                        case "delete" -> {
                            if (strings.length == 2 || strings.length == 3) {
                                String team = strings[1];
                                List<String> teams = FileTeams.getConfig().getStringList("takenNames");
                                if (teams.contains(team)) {
                                    //team exists
                                    if (strings.length == 2) {
                                        player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_command_teams_confirmDeletion)
                                                .replace("%team%", team), TextTypes.NO_SUCCESS));
                                    } else {
                                        List<String> playerList = new ArrayList<>();
                                        String c = "";
                                        for (String color : FileTeams.getConfig().getStringList("takenColors")) {
                                            if (FileTeams.getConfig().getString(color + ".name").equals(team)) {
                                                playerList.addAll(FileTeams.getConfig().getStringList(color + ".players"));
                                                FileTeams.getConfig().set(color, null);
                                                c = color;
                                            }
                                        }
                                        List<String> takenColors = FileTeams.getConfig().getStringList("takenColors");
                                        takenColors.remove(c);
                                        FileTeams.getConfig().set("takenColors", takenColors);
                                        List<String> takenNames = FileTeams.getConfig().getStringList("takenNames");
                                        takenNames.remove(team);
                                        FileTeams.getConfig().set("takenNames", takenNames);
                                        FileTeams.saveConfig();
                                        player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_command_teams_deleted)
                                                .replace("%team%", team), TextTypes.SUCCESS));

                                        for (String pName : playerList) {
                                            FilePlayers.getConfig().set(pName + ".team", null);
                                            Player p = Bukkit.getPlayer(pName);
                                            if (p != null) {
                                                p.setPlayerListName(p.getDisplayName());
                                                if (p != player) {
                                                    p.sendMessage(MessageMaker.makeMessage(Text.getText(Text.command_teams_deleted), TextTypes.NO_SUCCESS));
                                                }
                                            }
                                        }
                                        FilePlayers.saveConfig();
                                    }
                                } else {
                                    //team does not exist
                                    player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_command_teams_invalidTeam)
                                            .replace("%team%", team), TextTypes.NO_SUCCESS));
                                }
                            } else {
                                player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.command_invalidArgsLength), TextTypes.NO_SUCCESS));
                            }
                        }
                        case "add" -> {
                            if (strings.length == 2) {
                                player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_command_teams_noTeam), TextTypes.NO_SUCCESS));
                            } else if (strings.length == 3) {
                                String target = strings[1];
                                String team = strings[2];
                                if (FilePlayers.getConfig().getString(target + ".team") == null) {
                                    //player is in no team
                                    if (FileTeams.getConfig().getStringList("takenNames").contains(team)) {
                                        //team exists
                                        List<String> playerList = new ArrayList<>();
                                        for (String c : FileTeams.getConfig().getStringList("takenColors")) {
                                            if (FileTeams.getConfig().getString(c + ".name").equals(team)) {
                                                playerList = FileTeams.getConfig().getStringList(c + ".players");
                                                List<String> list = FileTeams.getConfig().getStringList(c + ".players");
                                                list.add(target);
                                                FileTeams.getConfig().set(c + ".players", list);
                                                FileTeams.saveConfig();
                                                break;
                                            }
                                        }
                                        FilePlayers.getConfig().set(target + ".team", team);
                                        FilePlayers.saveConfig();
                                        Player t = Bukkit.getPlayer(target);
                                        if (t != null && t != player) {
                                            t.sendMessage(MessageMaker.makeMessage(Text.getText(Text.command_teams_add_target)
                                                    .replace("%team%", team), TextTypes.SUCCESS));
                                            PlayerListPrefix.makePrefix(t);
                                            player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_command_teams_add_playerAddedToTeam)
                                                    .replace("%player%", target).replace("%team%", team), TextTypes.SUCCESS));
                                        } else if (t != null) {
                                            player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_command_teams_add_selfJoin)
                                                    .replace("%team%", team), TextTypes.SUCCESS));
                                            PlayerListPrefix.makePrefix(player);
                                        }
                                        for (String pName : playerList) {
                                            Player p = Bukkit.getPlayer(pName);
                                            if (p != null) {
                                                if (p != player || p != t) {
                                                    p.sendMessage(MessageMaker.makeMessage(Text.getText(Text.command_teams_add_team)
                                                            .replace("%player%", target), TextTypes.SUCCESS));
                                                }
                                            }
                                        }
                                    } else {
                                        //team does not exist
                                        player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_command_teams_invalidTeam)
                                                .replace("%team%", team), TextTypes.NO_SUCCESS));
                                    }
                                } else {
                                    //player is in team
                                    if (target.equals(player.getDisplayName())) {
                                        player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_command_teams_add_selfAlreadyInTeam)
                                                        .replace("%team%", FilePlayers.getConfig().getString(player.getDisplayName() + ".team")),
                                                TextTypes.NO_SUCCESS));
                                    } else {
                                        player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_command_teams_add_playerAlreadyInTeam)
                                                        .replace("%player%", target).replace("%team%", FilePlayers.getConfig().getString(target + ".team")),
                                                TextTypes.NO_SUCCESS));
                                    }
                                }
                            } else {
                                player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_commands_invalidArgsLength), TextTypes.NO_SUCCESS));
                            }
                        }
                        case "join" -> {
                            if (strings.length == 1) {
                                player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_command_teams_noTeam), TextTypes.NO_SUCCESS));
                            } else if (strings.length == 2) {
                                String team = strings[1];
                                if (FilePlayers.getConfig().getString(player.getDisplayName() + ".team") == null) {
                                    //Player in no Team
                                    if (FileTeams.getConfig().getStringList("takenNames").contains(team)) {
                                        for (String c : FileTeams.getConfig().getStringList("takenColors")) {
                                            if (FileTeams.getConfig().getString(c + ".name").equals(team)) {
                                                List<String> list = FileTeams.getConfig().getStringList(c + ".players");
                                                list.add(player.getDisplayName());
                                                FileTeams.getConfig().set(c + ".players", list);
                                                FileTeams.saveConfig();
                                                FilePlayers.getConfig().set(player.getDisplayName() + ".team", team);
                                                FilePlayers.saveConfig();
                                                PlayerListPrefix.makePrefix(player);

                                                for (String pl : FileTeams.getConfig().getStringList(c + ".players")) {
                                                    if (Bukkit.getPlayer(pl) != null && !pl.equals(player.getDisplayName())) {
                                                        Bukkit.getPlayer(pl).sendMessage(MessageMaker.makeMessage(Text.getText(Text.command_teams_add_team)
                                                                .replace("%player%", player.getDisplayName()), TextTypes.SUCCESS));
                                                    }
                                                }
                                                break;
                                            }
                                        }
                                        player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_command_teams_add_selfJoin)
                                                .replace("%team%", team), TextTypes.SUCCESS));
                                    } else {
                                        //Team existiert nicht
                                        player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_command_teams_invalidTeam)
                                                .replace("%team%", team), TextTypes.NO_SUCCESS));
                                    }
                                } else {
                                    //player in Team
                                    player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_command_teams_add_selfAlreadyInTeam)
                                            .replace("%team%", FilePlayers.getConfig().getString(player.getDisplayName() + ".team")), TextTypes.NO_SUCCESS));
                                }
                            } else {
                                player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_commands_invalidArgsLength), TextTypes.NO_SUCCESS));
                            }
                        }
                        case "remove" -> {
                            if (strings.length == 2) {
                                String target = strings[1];
                                if (FilePlayers.getConfig().getString(target + ".team") != null) {
                                    //target is in a team
                                    String team = FilePlayers.getConfig().getString(target + ".team");
                                    for (String c : FileTeams.getConfig().getStringList("takenColors")) {
                                        if (FileTeams.getConfig().getString(c + ".name").equals(team)) {
                                            List<String> list = FileTeams.getConfig().getStringList(c + ".players");
                                            list.remove(target);
                                            FileTeams.getConfig().set(c + ".players", list);
                                            FilePlayers.getConfig().set(target + ".team", null);
                                            FilePlayers.getConfig().set(target + ".isInTeamChat", false);
                                            FileTeams.saveConfig();
                                            FilePlayers.saveConfig();

                                            if (Bukkit.getPlayer(target) != null && !target.equals(player.getDisplayName())) {
                                                PlayerListPrefix.removePrefix(Bukkit.getPlayer(target));
                                                Bukkit.getPlayer(target).sendMessage(MessageMaker.makeMessage(Text.getText(Text.command_teams_remove_target)
                                                        .replace("%team%", team), TextTypes.NO_SUCCESS));
                                                player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_command_teams_remove_playerRemovedFromTeam)
                                                        .replace("%player%", target).replace("%team%", team), TextTypes.SUCCESS));
                                            } else if (target.equals(player.getDisplayName())) {
                                                PlayerListPrefix.removePrefix(player);
                                                Bukkit.getPlayer(target).sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_command_teams_remove_selfLeave)
                                                        .replace("%team%", team), TextTypes.NO_SUCCESS));
                                            }
                                            for (String pl : FileTeams.getConfig().getStringList(c + ".players")) {
                                                if (Bukkit.getPlayer(pl) != null && !pl.equals(player.getDisplayName())) {
                                                    Bukkit.getPlayer(pl).sendMessage(MessageMaker.makeMessage(Text.getText(Text.command_teams_remove_team)
                                                            .replace("%player%", target), TextTypes.NO_SUCCESS));
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    if (target.equals(player.getDisplayName())) {
                                        player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_command_teams_remove_selfInNoTeam), TextTypes.NO_SUCCESS));
                                    } else {
                                        player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_command_teams_remove_playerInNoTeam)
                                                .replace("%player%", target), TextTypes.NO_SUCCESS));
                                    }
                                }
                            } else {
                                //invalid Args length
                                player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_commands_invalidArgsLength), TextTypes.NO_SUCCESS));
                            }
                        }
                        case "leave" -> {
                            if (strings.length == 1) {
                                if (FilePlayers.getConfig().getString(player.getDisplayName() + ".team") != null) {
                                    String team = FilePlayers.getConfig().getString(player.getDisplayName() + ".team");
                                    for (String c : FileTeams.getConfig().getStringList("takenColors")) {
                                        if (FileTeams.getConfig().getString(c + ".name").equals(team)) {
                                            List<String> list = FileTeams.getConfig().getStringList(c + ".players");
                                            list.remove(player.getDisplayName());
                                            FileTeams.getConfig().set(c + ".players", list);
                                            FilePlayers.getConfig().set(player.getDisplayName() + ".team", null);
                                            FilePlayers.getConfig().set(player.getDisplayName() + ".isInTeamChat", false);
                                            FileTeams.saveConfig();
                                            FilePlayers.saveConfig();
                                            PlayerListPrefix.removePrefix(player);

                                            player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_command_teams_remove_selfLeave)
                                                    .replace("%team%", team), TextTypes.NO_SUCCESS));
                                            for (String pl : FileTeams.getConfig().getStringList(c + ".players")) {
                                                if (Bukkit.getPlayer(pl) != null && !pl.equals(player.getDisplayName())) {
                                                    Bukkit.getPlayer(pl).sendMessage(MessageMaker.makeMessage(Text.getText(Text.command_teams_remove_team)
                                                            .replace("%player%", player.getDisplayName()), TextTypes.NO_SUCCESS));
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_command_teams_remove_selfInNoTeam), TextTypes.NO_SUCCESS));
                                }
                            } else {
                                player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_commands_invalidArgsLength), TextTypes.NO_SUCCESS));
                            }
                        }
                        case "edit" -> {
                            if (strings.length == 1) {
                                player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_command_teams_noAction), TextTypes.NO_SUCCESS));
                            } else if (strings.length == 2) {
                                player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_command_teams_noTeam), TextTypes.NO_SUCCESS));
                            } else if (strings.length == 3) {
                                player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_command_teams_noValue), TextTypes.NO_SUCCESS));
                            } else if (strings.length == 4) {
                                String team = strings[1];
                                String editing = strings[2].toLowerCase(Locale.ROOT);
                                String value = strings[3];
                                String teamColor = "";

                                if (FileTeams.getConfig().getStringList("takenNames").contains(team)) {
                                    String color = "";
                                    for (String c : FileTeams.getConfig().getStringList("takenColors")) {
                                        if (FileTeams.getConfig().getString(c + ".name").equals(team)) {
                                            color = c;
                                            break;
                                        }
                                    }

                                    if (List.of("color", "name", "effect").contains(editing)) {
                                        switch (editing) {
                                            case "color" -> {
                                                value = value.toLowerCase(Locale.ROOT);
                                                if (colorsList.contains(value)) {
                                                    //valid color
                                                    if (!FileTeams.getConfig().getStringList("takenColors").contains(value)) {
                                                        //color not taken
                                                        FileTeams.getConfig().set(value + ".name", FileTeams.getConfig().getString(color + ".name"));
                                                        FileTeams.getConfig().set(value + ".effect", FileTeams.getConfig().getString(color + ".effect"));
                                                        FileTeams.getConfig().set(value + ".players", FileTeams.getConfig().getStringList(color + ".players"));
                                                        FileTeams.getConfig().set(color, null);
                                                        List<String> list = FileTeams.getConfig().getStringList("takenColors");
                                                        list.remove(color);
                                                        list.add(value);
                                                        FileTeams.getConfig().set("takenColors", list);
                                                        FileTeams.saveConfig();
                                                        for (String pl : FileTeams.getConfig().getStringList(value + ".players")) {
                                                            if (Bukkit.getPlayer(pl) != null && !pl.equals(player.getDisplayName())) {
                                                                Bukkit.getPlayer(pl).sendMessage(MessageMaker.makeMessage(Text.getText(Text.command_teams_edit_color)
                                                                        .replace("%color%", value), TextTypes.SUCCESS));
                                                            }
                                                        }
                                                        player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_command_teams_createAndEdit_changedColor)
                                                                .replace("%newColor%", value).replace("%team%", team).replace("%oldColor%", color), TextTypes.SUCCESS));
                                                        teamColor = value;
                                                    } else {
                                                        //color taken
                                                        player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_command_teams_createAndEdit_sameColor)
                                                                .replace("%team%", FileTeams.getConfig().getString(value + ".name")), TextTypes.NO_SUCCESS));
                                                    }
                                                } else {
                                                    //invalid color
                                                    player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_command_teams_createAndEdit_invalidColor)
                                                            .replace("%color%", value), TextTypes.NO_SUCCESS));
                                                }
                                            }
                                            case "name" -> {
                                                if (!FileTeams.getConfig().getStringList("takenNames").contains(value)) {
                                                    //name not taken
                                                    FileTeams.getConfig().set(color + ".name", value);
                                                    List<String> list = FileTeams.getConfig().getStringList("takenNames");
                                                    list.remove(team);
                                                    list.add(value);
                                                    FileTeams.getConfig().set("takenNames", list);
                                                    FileTeams.saveConfig();
                                                    for (String pl : FileTeams.getConfig().getStringList(color + ".players")) {
                                                        FilePlayers.getConfig().set(pl + ".team", value);
                                                        if (Bukkit.getPlayer(pl) != null && !pl.equals(player.getDisplayName())) {
                                                            Bukkit.getPlayer(pl).sendMessage(MessageMaker.makeMessage(Text.getText(Text.command_teams_edit_name)
                                                                    .replace("%name%", value), TextTypes.SUCCESS));
                                                        }
                                                    }
                                                    FilePlayers.saveConfig();
                                                    player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_command_teams_createAndEdit_changedName)
                                                            .replace("%newTeam%", value).replace("%oldTeam%", team), TextTypes.SUCCESS));
                                                    teamColor = color;
                                                } else {
                                                    //name taken
                                                    player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_command_teams_createAndEdit_sameName)
                                                            .replace("%team%", value), TextTypes.NO_SUCCESS));
                                                }
                                            }
                                            case "effect" -> {
                                                value = value.toLowerCase(Locale.ROOT);
                                                if (effectList.contains(value)) {
                                                    //valid effect
                                                    if (!FileTeams.getConfig().getString(color + ".effect").equals(value)) {
                                                        //not same effect
                                                        String oldEffect = FileTeams.getConfig().getString(color + ".effect");
                                                        FileTeams.getConfig().set(color + ".effect", value);
                                                        FileTeams.saveConfig();
                                                        for (String pl : FileTeams.getConfig().getStringList(color + ".players")) {
                                                            if (Bukkit.getPlayer(pl) != null && !pl.equals(player.getDisplayName())) {
                                                                if (!value.equals("none")) {
                                                                    Bukkit.getPlayer(pl).sendMessage(MessageMaker.makeMessage(Text.getText(Text.command_teams_edit_effect)
                                                                            .replace("%effect%", value), TextTypes.SUCCESS));
                                                                } else {
                                                                    Bukkit.getPlayer(pl).sendMessage(MessageMaker.makeMessage(Text.getText(Text.command_teams_edit_noEffect), TextTypes.SUCCESS));
                                                                }
                                                            }
                                                        }
                                                        player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_command_teams_createAndEdit_changedEffect)
                                                                .replace("%newEffect%", value).replace("%team%", team).replace("%oldEffect%", oldEffect), TextTypes.SUCCESS));
                                                        teamColor = color;
                                                    } else {
                                                        //same effect
                                                        player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_command_teams_createAndEdit_sameEffect)
                                                                .replace("%team%", team), TextTypes.NO_SUCCESS));
                                                    }
                                                } else {
                                                    //invalid effect
                                                    player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_command_teams_createAndEdit_invalidEffect)
                                                            .replace("%effect%", value), TextTypes.NO_SUCCESS));
                                                }
                                            }
                                        }
                                    } else {
                                        player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.command_invalidInput), TextTypes.NO_SUCCESS));
                                    }
                                } else {
                                    player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_command_teams_invalidTeam)
                                            .replace("%team%", team), TextTypes.NO_SUCCESS));
                                }

                                for (String pl : FileTeams.getConfig().getStringList(teamColor + ".players")) {
                                    if (Bukkit.getPlayer(pl) != null) {
                                        PlayerListPrefix.makePrefix(Bukkit.getPlayer(pl));
                                    }
                                }
                            } else {
                                player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_command_teams_noValue), TextTypes.NO_SUCCESS));
                            }
                        }
                    }
                }
            }
        } else {
            switch (strings[0].toLowerCase(Locale.ROOT)) {
                case "create" -> {
                    if (strings.length == 1) {
                        commandSender.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_command_teams_noName), TextTypes.NO_SUCCESS));
                    } else if (strings.length == 2) {
                        commandSender.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_command_teams_createAndEdit_noColor), TextTypes.NO_SUCCESS));
                    } else if (strings.length == 3 || strings.length == 4) {
                        String team = strings[1];
                        String color = strings[2].toLowerCase(Locale.ROOT);
                        String effect = "";
                        if (strings.length == 4) {
                            effect = strings[3].toLowerCase(Locale.ROOT);
                            //check for valid effect
                            if (!effectList.contains(effect)) {
                                commandSender.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_command_teams_createAndEdit_invalidEffect)
                                        .replace("%effect%", effect), TextTypes.NO_SUCCESS));
                                return true;
                            }
                        } else {
                            effect = "none";
                        }

                        if (colorsList.contains(color)) {
                            //Valid Color
                            if (!FileTeams.getConfig().getStringList("takenColors").contains(color)) {
                                //Color not taken
                                if (!FileTeams.getConfig().getStringList("takenNames").contains(team)) {
                                    //name not taken
                                    FileTeams.getConfig().set(color + ".name", team);
                                    FileTeams.getConfig().set(color + ".effect", effect);
                                    List<String> takenColors = FileTeams.getConfig().getStringList("takenColors");
                                    takenColors.add(color);
                                    FileTeams.getConfig().set("takenColors", takenColors);
                                    List<String> takenNames = FileTeams.getConfig().getStringList("takenNames");
                                    takenNames.add(team);
                                    FileTeams.getConfig().set("takenNames", takenNames);
                                    FileTeams.saveConfig();
                                    if (effect.equals("none")) {
                                        commandSender.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_command_teams_createAndEdit_createdWithoutEffect)
                                                .replace("%team%", team).replace("%color%", color), TextTypes.SUCCESS));
                                    } else {
                                        commandSender.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_command_teams_createAndEdit_createdWithEffect)
                                                .replace("%team%", team).replace("%color%", color).replace("%effect%", effect), TextTypes.SUCCESS));
                                    }
                                } else {
                                    //name taken
                                    commandSender.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_command_teams_createAndEdit_nameTaken)
                                            .replace("%name%", team), TextTypes.NO_SUCCESS));
                                }
                            } else {
                                //color Taken
                                commandSender.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_command_teams_createAndEdit_colorTaken)
                                        .replace("%color%", color), TextTypes.NO_SUCCESS));
                            }
                        } else {
                            //invalid Color
                            commandSender.sendMessage(colorsList.toString());
                            commandSender.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_command_teams_createAndEdit_invalidColor)
                                    .replace("%color%", color), TextTypes.NO_SUCCESS));
                        }
                    } else {
                        commandSender.sendMessage(MessageMaker.makeMessage(Text.getText(Text.command_invalidArgsLength), TextTypes.NO_SUCCESS));
                    }
                }
                case "delete" -> {
                    if (strings.length == 2 || strings.length == 3) {
                        String team = strings[1];
                        List<String> teams = FileTeams.getConfig().getStringList("takenNames");
                        if (teams.contains(team)) {
                            //team exists
                            if (strings.length == 2) {
                                commandSender.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_command_teams_confirmDeletion)
                                        .replace("%team%", team), TextTypes.NO_SUCCESS));
                            } else {
                                List<String> playerList = new ArrayList<>();
                                String c = "";
                                for (String color : FileTeams.getConfig().getStringList("takenColors")) {
                                    if (FileTeams.getConfig().getString(color + ".name").equals(team)) {
                                        playerList.addAll(FileTeams.getConfig().getStringList(color + ".players"));
                                        FileTeams.getConfig().set(color, null);
                                        c = color;
                                    }
                                }
                                List<String> takenColors = FileTeams.getConfig().getStringList("takenColors");
                                takenColors.remove(c);
                                FileTeams.getConfig().set("takenColors", takenColors);
                                List<String> takenNames = FileTeams.getConfig().getStringList("takenNames");
                                takenNames.remove(team);
                                FileTeams.getConfig().set("takenNames", takenNames);
                                FileTeams.saveConfig();
                                commandSender.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_command_teams_deleted)
                                        .replace("%team%", team), TextTypes.SUCCESS));

                                for (String pName : playerList) {
                                    FilePlayers.getConfig().set(pName + ".team", null);
                                    Player p = Bukkit.getPlayer(pName);
                                    if (p != null) {
                                        p.setPlayerListName(p.getDisplayName());
                                        p.sendMessage(MessageMaker.makeMessage(Text.getText(Text.command_teams_deleted), TextTypes.NO_SUCCESS));
                                    }
                                }
                                FilePlayers.saveConfig();
                            }
                        } else {
                            //team does not exist
                            commandSender.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_command_teams_invalidTeam)
                                    .replace("%team%", team), TextTypes.NO_SUCCESS));
                        }
                    } else {
                        commandSender.sendMessage(MessageMaker.makeMessage(Text.getText(Text.command_invalidArgsLength), TextTypes.NO_SUCCESS));
                    }
                }
                case "add" -> {
                    if (strings.length == 2) {
                        commandSender.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_command_teams_noTeam), TextTypes.NO_SUCCESS));
                    } else if (strings.length == 3) {
                        String target = strings[1];
                        String team = strings[2];
                        if (FilePlayers.getConfig().getString(target + ".team") == null) {
                            //player is in no team
                            if (FileTeams.getConfig().getStringList("takenNames").contains(team)) {
                                //team exists
                                List<String> playerList = new ArrayList<>();
                                for (String c : FileTeams.getConfig().getStringList("takenColors")) {
                                    if (FileTeams.getConfig().getString(c + ".name").equals(team)) {
                                        playerList = FileTeams.getConfig().getStringList(c + ".players");
                                        List<String> list = FileTeams.getConfig().getStringList(c + ".players");
                                        list.add(target);
                                        FileTeams.getConfig().set(c + ".players", list);
                                        FileTeams.saveConfig();
                                        break;
                                    }
                                }
                                FilePlayers.getConfig().set(target + ".team", team);
                                FilePlayers.saveConfig();
                                Player t = Bukkit.getPlayer(target);
                                if (t != null) {
                                    t.sendMessage(MessageMaker.makeMessage(Text.getText(Text.command_teams_add_target)
                                            .replace("%team%", team), TextTypes.SUCCESS));
                                    PlayerListPrefix.makePrefix(t);
                                    commandSender.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_command_teams_add_playerAddedToTeam)
                                            .replace("%player%", target).replace("%team%", team), TextTypes.SUCCESS));
                                }
                                for (String pName : playerList) {
                                    Player p = Bukkit.getPlayer(pName);
                                    if (p != null) {
                                        p.sendMessage(MessageMaker.makeMessage(Text.getText(Text.command_teams_add_team)
                                                .replace("%player%", target), TextTypes.SUCCESS));
                                    }
                                }
                            } else {
                                //team does not exist
                                commandSender.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_command_teams_invalidTeam)
                                        .replace("%team%", team), TextTypes.NO_SUCCESS));
                            }
                        } else {
                            commandSender.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_command_teams_add_playerAlreadyInTeam)
                                                .replace("%player%", target).replace("%team%", FilePlayers.getConfig().getString(target + ".team")),
                                        TextTypes.NO_SUCCESS));

                        }
                    } else {
                        commandSender.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_commands_invalidArgsLength), TextTypes.NO_SUCCESS));
                    }
                }
                case "remove" -> {
                    if (strings.length == 2) {
                        String target = strings[1];
                        if (FilePlayers.getConfig().getString(target + ".team") != null) {
                            //target is in a team
                            String team = FilePlayers.getConfig().getString(target + ".team");
                            for (String c : FileTeams.getConfig().getStringList("takenColors")) {
                                if (FileTeams.getConfig().getString(c + ".name").equals(team)) {
                                    List<String> list = FileTeams.getConfig().getStringList(c + ".players");
                                    list.remove(target);
                                    FileTeams.getConfig().set(c + ".players", list);
                                    FilePlayers.getConfig().set(target + ".team", null);
                                    FilePlayers.getConfig().set(target + ".isInTeamChat", false);
                                    FileTeams.saveConfig();
                                    FilePlayers.saveConfig();

                                    if (Bukkit.getPlayer(target) != null) {
                                        PlayerListPrefix.removePrefix(Bukkit.getPlayer(target));
                                        Bukkit.getPlayer(target).sendMessage(MessageMaker.makeMessage(Text.getText(Text.command_teams_remove_target)
                                                .replace("%team%", team), TextTypes.NO_SUCCESS));
                                        commandSender.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_command_teams_remove_playerRemovedFromTeam)
                                                .replace("%player%", target).replace("%team%", team), TextTypes.SUCCESS));
                                    }
                                    for (String pl : FileTeams.getConfig().getStringList(c + ".players")) {
                                        if (Bukkit.getPlayer(pl) != null) {
                                            Bukkit.getPlayer(pl).sendMessage(MessageMaker.makeMessage(Text.getText(Text.command_teams_remove_team)
                                                    .replace("%player%", target), TextTypes.NO_SUCCESS));
                                        }
                                    }
                                }
                            }
                        } else {
                            commandSender.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_command_teams_remove_playerInNoTeam)
                                        .replace("%player%", target), TextTypes.NO_SUCCESS));
                        }
                    } else {
                        //invalid Args length
                        commandSender.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_commands_invalidArgsLength), TextTypes.NO_SUCCESS));
                    }
                }
                case "edit" -> {
                    if (strings.length == 1) {
                        commandSender.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_command_teams_noAction), TextTypes.NO_SUCCESS));
                    } else if (strings.length == 2) {
                        commandSender.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_command_teams_noTeam), TextTypes.NO_SUCCESS));
                    } else if (strings.length == 3) {
                        commandSender.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_command_teams_noValue), TextTypes.NO_SUCCESS));
                    } else if (strings.length == 4) {
                        String team = strings[1];
                        String editing = strings[2].toLowerCase(Locale.ROOT);
                        String value = strings[3];
                        String teamColor = "";

                        if (FileTeams.getConfig().getStringList("takenNames").contains(team)) {
                            String color = "";
                            for (String c : FileTeams.getConfig().getStringList("takenColors")) {
                                if (FileTeams.getConfig().getString(c + ".name").equals(team)) {
                                    color = c;
                                    break;
                                }
                            }

                            if (List.of("color", "name", "effect").contains(editing)) {
                                switch (editing) {
                                    case "color" -> {
                                        value = value.toLowerCase(Locale.ROOT);
                                        if (colorsList.contains(value)) {
                                            //valid color
                                            if (!FileTeams.getConfig().getStringList("takenColors").contains(value)) {
                                                //color not taken
                                                FileTeams.getConfig().set(value + ".name", FileTeams.getConfig().getString(color + ".name"));
                                                FileTeams.getConfig().set(value + ".effect", FileTeams.getConfig().getString(color + ".effect"));
                                                FileTeams.getConfig().set(value + ".players", FileTeams.getConfig().getStringList(color + ".players"));
                                                FileTeams.getConfig().set(color, null);
                                                List<String> list = FileTeams.getConfig().getStringList("takenColors");
                                                list.remove(color);
                                                list.add(value);
                                                FileTeams.getConfig().set("takenColors", list);
                                                FileTeams.saveConfig();
                                                for (String pl : FileTeams.getConfig().getStringList(value + ".players")) {
                                                    if (Bukkit.getPlayer(pl) != null) {
                                                        Bukkit.getPlayer(pl).sendMessage(MessageMaker.makeMessage(Text.getText(Text.command_teams_edit_color)
                                                                .replace("%color%", value), TextTypes.SUCCESS));
                                                    }
                                                }
                                                commandSender.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_command_teams_createAndEdit_changedColor)
                                                        .replace("%newColor%", value).replace("%team%", team).replace("%oldColor%", color), TextTypes.SUCCESS));
                                                teamColor = value;
                                            } else {
                                                //color taken
                                                commandSender.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_command_teams_createAndEdit_sameColor)
                                                        .replace("%team%", FileTeams.getConfig().getString(value + ".name")), TextTypes.NO_SUCCESS));
                                            }
                                        } else {
                                            //invalid color
                                            commandSender.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_command_teams_createAndEdit_invalidColor)
                                                    .replace("%color%", value), TextTypes.NO_SUCCESS));
                                        }
                                    }
                                    case "name" -> {
                                        if (!FileTeams.getConfig().getStringList("takenNames").contains(value)) {
                                            //name not taken
                                            FileTeams.getConfig().set(color + ".name", value);
                                            List<String> list = FileTeams.getConfig().getStringList("takenNames");
                                            list.remove(team);
                                            list.add(value);
                                            FileTeams.getConfig().set("takenNames", list);
                                            FileTeams.saveConfig();
                                            for (String pl : FileTeams.getConfig().getStringList(color + ".players")) {
                                                FilePlayers.getConfig().set(pl + ".team", value);
                                                if (Bukkit.getPlayer(pl) != null) {
                                                    Bukkit.getPlayer(pl).sendMessage(MessageMaker.makeMessage(Text.getText(Text.command_teams_edit_name)
                                                            .replace("%name%", value), TextTypes.SUCCESS));
                                                }
                                            }
                                            FilePlayers.saveConfig();
                                            commandSender.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_command_teams_createAndEdit_changedName)
                                                    .replace("%newTeam%", value).replace("%oldTeam%", team), TextTypes.SUCCESS));
                                            teamColor = color;
                                        } else {
                                            //name taken
                                            commandSender.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_command_teams_createAndEdit_sameName)
                                                    .replace("%team%", value), TextTypes.NO_SUCCESS));
                                        }
                                    }
                                    case "effect" -> {
                                        value = value.toLowerCase(Locale.ROOT);
                                        if (effectList.contains(value)) {
                                            //valid effect
                                            if (!FileTeams.getConfig().getString(color + ".effect").equals(value)) {
                                                //not same effect
                                                String oldEffect = FileTeams.getConfig().getString(color + ".effect");
                                                FileTeams.getConfig().set(color + ".effect", value);
                                                FileTeams.saveConfig();
                                                for (String pl : FileTeams.getConfig().getStringList(color + ".players")) {
                                                    if (Bukkit.getPlayer(pl) != null) {
                                                        if (!value.equals("none")) {
                                                            Bukkit.getPlayer(pl).sendMessage(MessageMaker.makeMessage(Text.getText(Text.command_teams_edit_effect)
                                                                    .replace("%effect%", value), TextTypes.SUCCESS));
                                                        } else {
                                                            Bukkit.getPlayer(pl).sendMessage(MessageMaker.makeMessage(Text.getText(Text.command_teams_edit_noEffect), TextTypes.SUCCESS));
                                                        }
                                                    }
                                                }
                                                commandSender.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_command_teams_createAndEdit_changedEffect)
                                                        .replace("%newEffect%", value).replace("%team%", team).replace("%oldEffect%", oldEffect), TextTypes.SUCCESS));
                                                teamColor = color;
                                            } else {
                                                //same effect
                                                commandSender.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_command_teams_createAndEdit_sameEffect)
                                                        .replace("%team%", team), TextTypes.NO_SUCCESS));
                                            }
                                        } else {
                                            //invalid effect
                                            commandSender.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_command_teams_createAndEdit_invalidEffect)
                                                    .replace("%effect%", value), TextTypes.NO_SUCCESS));
                                        }
                                    }
                                }
                            } else {
                                commandSender.sendMessage(MessageMaker.makeMessage(Text.getText(Text.command_invalidInput), TextTypes.NO_SUCCESS));
                            }
                        } else {
                            commandSender.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_command_teams_invalidTeam)
                                    .replace("%team%", team), TextTypes.NO_SUCCESS));
                        }

                        for (String pl : FileTeams.getConfig().getStringList(teamColor + ".players")) {
                            if (Bukkit.getPlayer(pl) != null) {
                                PlayerListPrefix.makePrefix(Bukkit.getPlayer(pl));
                            }
                        }
                    } else {
                        commandSender.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_command_teams_noValue), TextTypes.NO_SUCCESS));
                    }
                }
                default -> commandSender.sendMessage(MessageMaker.makeMessage(Text.getText(Text.commands_onlyPlayers), TextTypes.NO_SUCCESS));
            }
        }
        if (commandSender instanceof Player p) {
            boolean isAdmin = FilePlayers.getConfig().getBoolean(p.getDisplayName() + ".isAdmin");
            if (!isAdmin) {
                p.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_noAdmin), TextTypes.NO_SUCCESS));
                return true;
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        List<String> list = new ArrayList<>();
        if (strings.length == 1) {
            if (commandSender instanceof Player player && !FilePlayers.getConfig().getBoolean(player.getDisplayName() + ".isAdmin")) {
                list.add("chat");
            } else {
                list.addAll(List.of("create", "delete", "add", "remove", "edit", "chat", "join", "leave"));
            }

        } else if (strings.length == 2) {
            switch (strings[0].toLowerCase(Locale.ROOT)) {
                case "delete", "edit", "join" -> {
                    list.addAll(FileTeams.getConfig().getStringList("takenNames"));
                }
                case "chat" -> {
                    list.addAll(List.of("on", "off"));
                }
                case "add", "remove" -> {
                    return null;
                }
            }
        } else if (strings.length == 3) {
            switch (strings[0].toLowerCase(Locale.ROOT)) {
                case "create" -> {
                    list.addAll(colorsList);
                    list.removeAll(FileTeams.getConfig().getStringList("takenColors"));
                }
                case "delete" -> {
                    list.add("confirm");
                }
                case "add" -> {
                    list.addAll(FileTeams.getConfig().getStringList("takenNames"));
                }
                case "edit" -> {
                    list.addAll(List.of("color", "name", "effect"));
                }
            }
        } else if (strings.length == 4) {
            if (strings[0].toLowerCase(Locale.ROOT).equals("create")) {
                list.addAll(effectList);
                list.remove("none");
            } else if (strings[0].toLowerCase(Locale.ROOT).equals("edit")) {
                switch (strings[2].toLowerCase(Locale.ROOT)) {
                    case "color" -> {
                        list.addAll(colorsList);
                        list.removeAll(FileTeams.getConfig().getStringList("takenColors"));
                    }
                    case "effect" -> {
                        list.addAll(effectList);
                    }
                }
            }
        }
        return list;
    }
}
