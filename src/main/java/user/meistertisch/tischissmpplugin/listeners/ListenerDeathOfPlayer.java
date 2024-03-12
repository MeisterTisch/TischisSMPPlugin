package user.meistertisch.tischissmpplugin.listeners;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import user.meistertisch.tischissmpplugin.languages.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListenerDeathOfPlayer implements Listener {
    @EventHandler
    public void playerDied(PlayerDeathEvent event){
        if(event.getEntity().getKiller() == null || !(event.getEntity().getKiller().getType() == EntityType.PLAYER)){
            return;
        }

        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) head.getItemMeta();
        meta.setOwnerProfile(event.getEntity().getPlayerProfile());
        meta.setDisplayName(Text.getText(Text.headDrop_player).replace("%player%", event.getEntity().getDisplayName()));
        List<String> list = new ArrayList<>();
        list.add(Text.getText(Text.headDrop_player).replace("%player%", event.getEntity().getDisplayName()));
        list.add(Text.getText(Text.headDrop_killer).replace("%killer%", event.getEntity().getKiller().getDisplayName()));
        meta.setLore(list);
        head.setItemMeta(meta);

        event.getDrops().add(head);
    }
}
