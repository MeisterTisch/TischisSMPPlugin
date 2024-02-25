package user.meistertisch.tischissmpplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import user.meistertisch.tischissmpplugin.Main;
import user.meistertisch.tischissmpplugin.languages.Text;
import user.meistertisch.tischissmpplugin.messageMaker.MessageMaker;
import user.meistertisch.tischissmpplugin.messageMaker.TextTypes;
import user.meistertisch.tischissmpplugin.players.FilePlayers;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CommandHome implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender instanceof Player player)){
            commandSender.sendMessage(MessageMaker.makeMessage(Text.getText(Text.commands_onlyPlayers), TextTypes.NO_SUCCESS));
            return true;
        }

        if(strings.length == 0){
            //args sind nichts da
            player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.command_invalidArgsLength), TextTypes.NO_SUCCESS));
        } else {
            //args nur eins da wtf
            switch(strings[0].toLowerCase(Locale.ROOT)){
                case "create" -> {
                    if(strings.length == 1){
                        //NO ARGS OTHER THAN CREATE
                       player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.command_home_creation_noName), TextTypes.NO_SUCCESS));
                    } else if(strings.length == 2){
                        //PERFECT ARGS :)
                        //Check for amount
                        if(Main.getPlugin().getConfig().getInt("homes.maxNumber") <= FilePlayers.getConfig().getInt(player.getDisplayName() + ".homes.amount")){
                            player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.command_home_creation_maxAmountReached)
                                    .replace("%max%","" + Main.getPlugin().getConfig().getInt("homes.maxNumber"))
                                    .replace("%amount%","" + FilePlayers.getConfig().getInt(player.getDisplayName() + ".homes.amount"))
                                    , TextTypes.NO_SUCCESS));
                        } else {
                            String homeName = strings[1];

                            for (int i = 1; i <= FilePlayers.getConfig().getInt(player.getDisplayName() + ".homes.amount"); i++) {
                                if(homeName.equals(FilePlayers.getConfig().getString(player.getDisplayName() + ".homes.home" + i + ".name"))) {
                                    player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.command_home_creation_sameName)
                                                    .replace("%home%", strings[1]), TextTypes.NO_SUCCESS));
                                    return true;
                                }
                            }
                            int nextHome = FilePlayers.getConfig().getInt(player.getDisplayName() + ".homes.amount") + 1;
                            Location loc = player.getLocation();

                            FilePlayers.getConfig().set(player.getDisplayName()+".homes.amount", nextHome);
                            FilePlayers.getConfig().set(player.getDisplayName()+".homes.home" + nextHome + ".name", strings[1]);
                            FilePlayers.getConfig().set(player.getDisplayName()+".homes.home" + nextHome + ".loc", loc);
                            FilePlayers.saveConfig();

                            player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.command_home_creation_created)
                                    .replace("%home%", strings[1]), TextTypes.SUCCESS));
                        }
                    } else {
                        //TOO MUCH ARGS
                        player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.command_invalidArgsLength), TextTypes.NO_SUCCESS));
                    }
                }
                case "remove", "delete" -> {
                    if(strings.length == 1){
                        player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.command_home_deletion_noName), TextTypes.NO_SUCCESS));
                    } else if(strings.length == 2 || strings.length == 3){
                        List<String> homes = new ArrayList<>();
                        for (int i = 1; i <= FilePlayers.getConfig().getInt(player.getDisplayName() + ".homes.amount") ; i++) {
                            homes.add(FilePlayers.getConfig().getString(player.getDisplayName() + ".homes.home" + i + ".name"));
                        }
                        if(homes.contains(strings[1])){
                            //Home existiert passt
                            String home = strings[1];
                            if(strings.length == 2){
                                //ohne confirm
                                player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.command_home_deletion_confirm)
                                        .replace("%home%", home), TextTypes.NO_SUCCESS));
                            } else {
                                if(strings[2].toLowerCase(Locale.ROOT).equals("confirm")){
                                    //confirm geschrieben
                                    player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.command_home_deletion_deleted)
                                            .replace("%home%", home), TextTypes.NO_SUCCESS));

                                    int homeNumber = 0;
                                    for (int i = 1; i <= FilePlayers.getConfig().getInt(player.getDisplayName() + ".homes.amount") ; i++) {
                                        if(home.equals(FilePlayers.getConfig().getString(player.getDisplayName() + ".homes.home" + i + ".name"))){
                                            homeNumber = i;
                                            break;
                                        }
                                    }

                                    for (int i = homeNumber; i <= FilePlayers.getConfig().getInt(player.getDisplayName() + ".homes.amount") ; i++) {
                                        int neuI = i + 1;
                                        FilePlayers.getConfig().set(player.getDisplayName() + ".homes.home" + i + ".name",
                                                FilePlayers.getConfig().getString(player.getDisplayName() + ".homes.home" + neuI + ".name"));
                                        FilePlayers.getConfig().set(player.getDisplayName() + ".homes.home" + i + ".loc",
                                                FilePlayers.getConfig().getLocation(player.getDisplayName() + ".homes.home" + neuI + ".loc"));
                                    }

                                    FilePlayers.getConfig().set(player.getDisplayName() + ".homes.home" + FilePlayers.getConfig().getInt(player.getDisplayName() + ".homes.amount"),
                                            null);
                                    FilePlayers.getConfig().set(player.getDisplayName() + ".homes.amount",
                                            FilePlayers.getConfig().getInt(player.getDisplayName() + ".homes.amount") - 1);
                                    FilePlayers.saveConfig();
                                } else {
                                    player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.command_home_deletion_confirm)
                                            .replace("%home%", home), TextTypes.NO_SUCCESS));
                                }
                            }
                        } else {
                            //Nicht da nichts gut
                            player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.command_home_deletion_invalidHome), TextTypes.NO_SUCCESS));
                            player.sendMessage(homes.toString());
                        }
                    } else {
                        player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.command_invalidArgsLength), TextTypes.NO_SUCCESS));
                    }
                }
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        List<String> list = new ArrayList<>();
        String player;

        if(!(commandSender instanceof Player sender)){
            return list;
        } else {
            player = sender.getDisplayName();
        }

        if(strings.length == 1){
            list.addAll(List.of("create", "remove", "delete", "rename", "tp", "teleport"));
        } else if(strings.length == 2){
            if(strings[0].toLowerCase(Locale.ROOT).equals("remove") || strings[0].toLowerCase(Locale.ROOT).equals("rename")
            || strings[0].toLowerCase(Locale.ROOT).equals("tp") || strings[0].toLowerCase(Locale.ROOT).equals("teleport")){
                for (int i = 1; i <= FilePlayers.getConfig().getInt(player + ".homes.amount"); i++) {
                    list.add(FilePlayers.getConfig().getString(player + ".homes.home" + i + ".name"));
                }
            }
        }
        return list;
    }
}
