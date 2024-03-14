package user.meistertisch.tischissmpplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.util.BoundingBox;
import user.meistertisch.tischissmpplugin.Main;
import user.meistertisch.tischissmpplugin.languages.Text;
import user.meistertisch.tischissmpplugin.messageMaker.MessageMaker;
import user.meistertisch.tischissmpplugin.messageMaker.TextTypes;
import user.meistertisch.tischissmpplugin.players.FilePlayers;
import user.meistertisch.tischissmpplugin.players.teleportation.Teleportation;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CommandSpawn implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player player){
            if(strings.length == 0){
                if(Main.getPlugin().getConfig().getBoolean("spawn.status")){
                    if(Main.getPlugin().getConfig().isLocation("spawn.location") && Main.getPlugin().getConfig().get("spawn.location") != null){
                        if(Main.getPlugin().getConfig().getBoolean("spawn.mustBeNearby")){
                            BoundingBox bb = Main.getPlugin().getConfig().getLocation("spawn.location")
                                    .getBlock().getBoundingBox().expand(1000);
                            if(bb.overlaps(player.getBoundingBox())){
                                Teleportation.teleport(player, Main.getPlugin().getConfig().getLocation("spawn.location"));
                            } else {
                                player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.command_spawn_notInArea), TextTypes.NO_SUCCESS));
                            }
                        } else {
                            Teleportation.teleport(player, Main.getPlugin().getConfig().getLocation("spawn.location"));
                        }
                    } else {
                        player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.command_spawn_spawnNotSetYet), TextTypes.NO_SUCCESS));
                        for(Player pl : Bukkit.getOnlinePlayers()){
                            if(FilePlayers.getConfig().getBoolean(pl.getDisplayName() + ".isAdmin") && pl != player){
                                pl.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_command_spawn_notSetYet), TextTypes.NO_SUCCESS));
                            }
                        }
                    }
                } else {
                    player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.command_commandDisabled), TextTypes.NO_SUCCESS));
                }
            } else if(strings.length == 1){
                if(FilePlayers.getConfig().getBoolean(player.getDisplayName() + ".isAdmin")){
                    if(strings[0].toLowerCase(Locale.ROOT).equals("set")){
                        Location loc = new Location(player.getWorld(), player.getLocation().getBlockX() + 0.5,
                                player.getLocation().getBlockY() + 0.15, player.getLocation().getBlockZ() + 0.5, 0, 0);
                        Main.getPlugin().getConfig().set("spawn.location", loc);
                        Main.getPlugin().saveConfig();
                        Main.getPlugin().reloadConfig();
                        player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_command_spawn_set), TextTypes.SUCCESS));
                    } else {
                        player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.command_invalidInput), TextTypes.NO_SUCCESS));
                    }
                } else {
                    player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_noAdmin), TextTypes.NO_SUCCESS));
                }
            } else {
                player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.command_invalidArgsLength), TextTypes.NO_SUCCESS));
            }
        } else {
            commandSender.sendMessage(MessageMaker.makeMessage(Text.getText(Text.commands_onlyPlayers), TextTypes.NO_SUCCESS));
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player player && FilePlayers.getConfig().getBoolean(player.getDisplayName() + ".isAdmin")
        && strings.length == 1){
            return new ArrayList<>(List.of("set"));
        }
        return new ArrayList<>();
    }
}
