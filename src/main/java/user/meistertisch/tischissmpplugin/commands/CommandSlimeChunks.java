package user.meistertisch.tischissmpplugin.commands;

import org.bukkit.ChatColor;
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

public class CommandSlimeChunks implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player player){
            if(strings.length == 0){
                player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.command_invalidInput), TextTypes.NO_SUCCESS));
            } else if(strings.length == 1){
                if(strings[0].toLowerCase(Locale.ROOT).equals("list")){
                    List<String> list = FilePlayers.getConfig().getStringList(player.getDisplayName() + ".foundSlimechunks");
                    if(!list.isEmpty()){
                        StringBuilder message = new StringBuilder(Text.getText(Text.command_slimechunk_list));
                        for(String chunk : list){
                            message.append("\n- ").append(Text.getText(Text.command_slimechunk_foundSlimeChunk)
                                    .replace("%chunk%", chunk));
                        }
                        int left = Main.getPlugin().getConfig().getInt("slimechunk.maxTime") -
                                FilePlayers.getConfig().getInt(player.getDisplayName() + ".timesSlimechunkChecked");
                        String leftString = left + "";
                        message.append("\n").append(Text.getText(Text.command_slimechunk_leftUsages).replace("%leftUsages%", leftString));
                        player.sendMessage(MessageMaker.makeMessage(message.toString(), TextTypes.NORMAL));
                    } else {
                        player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.command_slimechunk_emptyList), TextTypes.NO_SUCCESS));
                    }
                } else if(strings[0].toLowerCase(Locale.ROOT).equals("check")){
                    player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.command_slimechunk_checkConfirm), TextTypes.NO_SUCCESS));
                } else if(strings[0].toLowerCase(Locale.ROOT).equals("solo")){
                    //Admin stuff, heres a check
                    boolean isAdmin = FilePlayers.getConfig().getBoolean(player.getDisplayName() + ".isAdmin");
                    if (!isAdmin) {
                        player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_noAdmin), TextTypes.NO_SUCCESS));
                        return true;
                    }
                    boolean solo = Main.getPlugin().getConfig().getBoolean("slimechunk.solo");
                    Main.getPlugin().getConfig().set("slimechunk.solo", !solo);
                    Main.getPlugin().saveConfig();
                    Main.getPlugin().reloadConfig();
                    if(solo){
                        player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_command_slimechunk_madeFalse), TextTypes.NO_SUCCESS));
                    } else {
                        player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_command_slimechunk_madeTrue), TextTypes.SUCCESS));
                    }
                } else {
                    player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.command_invalidInput), TextTypes.NO_SUCCESS));
                }
            } else if (strings.length == 2){
                if(strings[0].toLowerCase(Locale.ROOT).equals("check") && strings[1].toLowerCase(Locale.ROOT).equals("confirm")){
                    if(FilePlayers.getConfig().getInt(player.getDisplayName() + ".timesSlimechunkChecked") >=
                            Main.getPlugin().getConfig().getInt("slimechunk.maxTime")){
                        player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.command_slimechunk_noUsagesLeft), TextTypes.NO_SUCCESS));
                        return true;
                    }

                    List<String> foundChunks = new ArrayList<>();
                    int x = player.getLocation().getChunk().getX();
                    int z = player.getLocation().getChunk().getZ();
                    String chunk = "[%x%, %z%]";
                    if(!Main.getPlugin().getConfig().getBoolean("slimechunk.solo")){
                        //Multi
                        for (int i = -1; i <= 1; i++) {
                            for(int j = -1; j <= 1; j++){
                                if(player.getWorld().getChunkAt(x+i, z+j).isSlimeChunk()){
                                    int xNum = x + i;
                                    int zNum = z + j;
                                    String xFinal = xNum + "";
                                    String zFinal = zNum + "";
                                    String chunkFinal = chunk.replace("%x%", xFinal).replace("%z%", zFinal);
                                    foundChunks.add(chunkFinal);
                                }
                            }
                        }
                    } else {
                        //solo
                        if(player.getWorld().getChunkAt(x, z).isSlimeChunk()){
                            String xFinal = x + "";
                            String zFinal = z + "";
                            String chunkFinal = chunk.replace("%x%", xFinal).replace("%z%", zFinal);
                            foundChunks.add(chunkFinal);
                        }
                    }

                    if(!foundChunks.isEmpty()){
                        int count = FilePlayers.getConfig().getInt(player.getDisplayName() + ".timesSlimechunkChecked") + 1;
                        FilePlayers.getConfig().set(player.getDisplayName() + ".timesSlimechunkChecked", count);
                        String message = Text.getText(Text.command_slimechunk_checked);
                        for(String ch : foundChunks){
                            message = message + "\n- " + Text.getText(Text.command_slimechunk_foundSlimeChunk).replace("%chunk%", ch);
                        }
                        int left = Main.getPlugin().getConfig().getInt("slimechunk.maxTime") -
                                FilePlayers.getConfig().getInt(player.getDisplayName() + ".timesSlimechunkChecked");
                        String leftString = left + "";
                        message = message + "\n" + Text.getText(Text.command_slimechunk_leftUsages).replace("%leftUsages%", leftString);
                        player.sendMessage(MessageMaker.makeMessage(message, TextTypes.NORMAL));
                    } else {
                        String message = Text.getText(Text.command_slimechunk_noSlimeChunks);
                        int count = FilePlayers.getConfig().getInt(player.getDisplayName() + ".timesSlimechunkChecked") + 1;
                        FilePlayers.getConfig().set(player.getDisplayName() + ".timesSlimechunkChecked", count);
                        FilePlayers.saveConfig();
                        int left = Main.getPlugin().getConfig().getInt("slimechunk.maxTime") -
                                FilePlayers.getConfig().getInt(player.getDisplayName() + ".timesSlimechunkChecked");
                        String leftString = left + "";
                        message = message + "\n" + ChatColor.WHITE + Text.getText(Text.command_slimechunk_leftUsages).replace("%leftUsages%", leftString);
                        player.sendMessage(MessageMaker.makeMessage(message, TextTypes.NO_SUCCESS));
                    }
                    for(String ch : FilePlayers.getConfig().getStringList(player.getDisplayName() + ".foundSlimechunks")){
                        if(!foundChunks.contains(ch)){
                            foundChunks.add(ch);
                        }
                    }
                    FilePlayers.getConfig().set(player.getDisplayName() + ".foundSlimechunks", foundChunks);
                    FilePlayers.saveConfig();
                } else if(strings[0].toLowerCase(Locale.ROOT).equals("solo")){
                    //Admin stuff, heres a check
                    boolean isAdmin = FilePlayers.getConfig().getBoolean(player.getDisplayName() + ".isAdmin");
                    if (!isAdmin) {
                        player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_noAdmin), TextTypes.NO_SUCCESS));
                        return true;
                    }
                    if(strings[1].toLowerCase(Locale.ROOT).equals("on")){
                        if(!Main.getPlugin().getConfig().getBoolean("slimechunk.solo")){
                            Main.getPlugin().getConfig().set("slimechunk.solo", true);
                            Main.getPlugin().saveConfig();
                            Main.getPlugin().reloadConfig();
                            player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_command_slimechunk_madeTrue), TextTypes.SUCCESS));
                        } else {
                            player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_command_slimechunk_alreadyTrue), TextTypes.NO_SUCCESS));
                        }
                    } else if(strings[1].toLowerCase(Locale.ROOT).equals("off")){
                        if(Main.getPlugin().getConfig().getBoolean("slimechunk.solo")){
                            Main.getPlugin().getConfig().set("slimechunk.solo", false);
                            Main.getPlugin().saveConfig();
                            Main.getPlugin().reloadConfig();
                            player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_command_slimechunk_madeFalse), TextTypes.NO_SUCCESS));
                        } else {
                            player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.admin_command_slimechunk_alreadyFalse), TextTypes.NO_SUCCESS));
                        }
                    } else {
                        player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.command_invalidInput), TextTypes.NO_SUCCESS));
                    }
                } else {
                    player.sendMessage(MessageMaker.makeMessage(Text.getText(Text.command_invalidInput), TextTypes.NO_SUCCESS));
                }
            }
        } else {
            commandSender.sendMessage(MessageMaker.makeMessage(Text.getText(Text.commands_onlyPlayers), TextTypes.NO_SUCCESS));
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        List<String> list = new ArrayList<>();
        if(strings.length == 1){
            list.addAll(List.of("check", "list"));
            if(commandSender instanceof Player player && FilePlayers.getConfig().getBoolean(player.getDisplayName() + ".isAdmin")){
                list.add("solo");
            }
        }
        else if(strings.length == 2 && strings[0].toLowerCase(Locale.ROOT).equals("check")){
            list.add("confirm");
        } else if(commandSender instanceof Player player && FilePlayers.getConfig().getBoolean(player.getDisplayName() + ".isAdmin")
                && strings.length == 2 && strings[0].toLowerCase(Locale.ROOT).equals("solo")){
            list.addAll(List.of("true", "false"));
        }
        return list;
    }
}
