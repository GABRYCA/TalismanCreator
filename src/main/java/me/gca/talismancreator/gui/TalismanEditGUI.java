package me.gca.talismancreator.gui;

import com.cryptomorin.xseries.XMaterial;
import me.gca.talismancreator.events.GUIListener;
import me.gca.talismancreator.gui.util.SpigotGUIComponents;
import me.gca.talismancreator.managers.Talisman;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class TalismanEditGUI extends SpigotGUIComponents {

    public TalismanEditGUI(Player p, Talisman talisman){
        // Check conditions.
        if (p == null || talisman == null){
            return;
        }

        // Params
        int size = 9*3;
        GUIListener.getInstance().addTalismanEditing(p, talisman);

        // Create Buttons
        ItemStack talismanItem = createButton(talisman.getItemStack(), createLore("&8Click to open.", "", "&6Current item:", "&f- &6" + talisman.getItemStack().getType().toString()), "&6Manage Item");
        ItemStack talismanTitle = createButton(XMaterial.FEATHER.parseItem(), createLore("&8Click to edit.", "", "&6Current title:", "&f- &6" + talisman.getTitle()), "&6Manage Title");
        ItemStack talismanEffects = createButton(XMaterial.BREWING_STAND.parseItem(), createLore("&8Click to open."), "&6Manage Lore");

        // Create Inventory.
        Inventory inv = Bukkit.createInventory(null, 24, "&6Talisman Edit");

        // Add Buttons to Inventory.
        inv.setItem(size - 1, getCloseGUIButton());
        inv.setItem(10, talismanItem);
        inv.setItem(13, talismanTitle);
        inv.setItem(16, talismanEffects);

        // Open Inventory.
        openGUI(inv, p);
    }
}
