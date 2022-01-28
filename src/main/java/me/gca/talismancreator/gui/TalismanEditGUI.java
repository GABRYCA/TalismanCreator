package me.gca.talismancreator.gui;

import com.cryptomorin.xseries.XMaterial;
import me.gca.talismancreator.TalismanCreator;
import me.gca.talismancreator.events.Listeners;
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
        Listeners.getInstance().addTalismanEditing(p, talisman);

        // Create Buttons
        ItemStack talismanItem = createButton(talisman.getItemStack(), createLore("&8Click to open.", "", "&6Current item:", "&f- &6" + talisman.getItemStack().getType().toString()), "&6Manage Item");
        ItemStack talismanEffects = createButton(XMaterial.BREWING_STAND.parseItem(), createLore("&8Click to open."), "&6Manage Effects");
        ItemStack talismanLore = createButton(XMaterial.BOOK.parseItem(), createLore("&8Click to open."), "&6Manage Lore");

        // Create Inventory.
        Inventory inv = Bukkit.createInventory(null, size, TalismanCreator.colorFormat("&6Talisman Edit"));

        // Add Buttons to Inventory.
        inv.setItem(size - 1, getCloseGUIButton());
        inv.setItem(10, talismanItem);
        inv.setItem(13, talismanEffects);
        inv.setItem(16, talismanLore);

        // Open Inventory.
        openGUI(inv, p);
    }
}
