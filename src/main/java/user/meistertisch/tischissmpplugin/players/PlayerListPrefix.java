package user.meistertisch.tischissmpplugin.players;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import user.meistertisch.tischissmpplugin.admin.teams.FileTeams;
import user.meistertisch.tischissmpplugin.messageMaker.ColorSolver;
import user.meistertisch.tischissmpplugin.players.FilePlayers;

public class PlayerListPrefix {
    public static void makePrefix(Player player){
        //For Teams
        player.setPlayerListName(getListName(player));
    }

    public static String givePrefix(Player player){
        //For Teams
        return getListName(player);
    }

    private static String getListName(Player player) {
        String listName = player.getDisplayName();

        if(FilePlayers.getConfig().getString(player.getDisplayName() + ".team") != null){
            String teamName = FilePlayers.getConfig().getString(player.getDisplayName() + ".team");
            if(teamName != null){
                String color = "";
                String effect = "";
                for(String c: FileTeams.getConfig().getStringList("takenColors")){
                    if(FileTeams.getConfig().getString(c + ".name").equals(teamName)){
                        color = c;
                        effect = FileTeams.getConfig().getString(c + ".effect");
                        break;
                    }
                }
                if(effect == null || effect.equals("none")){
                    listName = ColorSolver.giveColorByName(color) + "" + ChatColor.BOLD + "["
                            + teamName + ChatColor.RESET + ColorSolver.giveColorByName(color) + "" + ChatColor.BOLD + "]" + " " + ChatColor.RESET + listName;
                } else {
                    listName = ColorSolver.giveColorByName(color) + "" + ChatColor.BOLD + "["  + ColorSolver.giveEffectByName(effect)
                            + teamName + ChatColor.RESET + ColorSolver.giveColorByName(color) + "" + ChatColor.BOLD + "]" + " " + ChatColor.RESET + listName;
                }

            }
        }
        return listName;
    }
    public static void removePrefix(Player player){
        player.setPlayerListName(player.getDisplayName());
    }
}
