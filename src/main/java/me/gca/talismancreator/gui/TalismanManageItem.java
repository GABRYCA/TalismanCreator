package me.gca.talismancreator.gui;

import com.cryptomorin.xseries.SkullUtils;
import com.cryptomorin.xseries.XMaterial;
import me.gca.talismancreator.TalismanCreator;
import me.gca.talismancreator.events.Listeners;
import me.gca.talismancreator.gui.util.SpigotGUIComponents;
import me.gca.talismancreator.managers.Talisman;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class TalismanManageItem extends SpigotGUIComponents {

    public TalismanManageItem(Player p, Talisman talisman){
        // Check conditions.
        if (p == null || talisman == null){
            return;
        }

        // Params
        int size = 9*3;
        Listeners.getInstance().addTalismanEditing(p, talisman);

        // Create Buttons
        ItemStack talismanItem = createButton(XMaterial.TOTEM_OF_UNDYING.parseItem(), createLore("&8Click to choose."), "&6Choose from Items");
        ItemStack talismanHead = createButton(SkullUtils.getSkull(p.getUniqueId()), createLore("&8Click to choose."), "&6Choose from Heads");

        // Create Inventory.
        Inventory inv = Bukkit.createInventory(null, size, TalismanCreator.colorFormat("&6Talisman Manage Item"));

        // Add Buttons to Inventory.
        inv.setItem(size - 1, getCloseGUIButton());
        inv.setItem(11, talismanItem);
        inv.setItem(15, talismanHead);

        // Open Inventory.
        openGUI(inv, p);
    }

}
