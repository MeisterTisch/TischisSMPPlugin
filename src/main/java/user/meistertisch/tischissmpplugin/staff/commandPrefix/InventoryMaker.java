package user.meistertisch.tischissmpplugin.staff.commandPrefix;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import user.meistertisch.tischissmpplugin.Main;
import user.meistertisch.tischissmpplugin.languages.Text;
import user.meistertisch.tischissmpplugin.messageMaker.ColorSolver;
public class InventoryMaker {
    //TODO: BUKKIT RUNNABLE FOR ONLY ONE CHANGING
    //TODO: Change only taken when user takes new nametag in his inv
    private static Inventory inv = Bukkit.createInventory(null, InventoryType.ANVIL, Text.getText(Text.staff_commands_prefix_anvilTitle));

    public static Inventory getAnvilInventory() {
        ItemStack nameTag = new ItemStack(Material.NAME_TAG);
        ItemMeta nameTagMeta = nameTag.getItemMeta();
        nameTagMeta.setDisplayName(Main.getPlugin().getConfig().getString("prefix"));
        nameTag.setItemMeta(nameTagMeta);

        inv.setItem(0, nameTag);
        return inv;
    }
}
