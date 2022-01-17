package me.gca.talismancreator.gui;

import com.cryptomorin.xseries.XMaterial;
import me.gca.talismancreator.TalismanCreator;
import me.gca.talismancreator.events.GUIListener;
import me.gca.talismancreator.gui.util.SpigotGUIComponents;
import me.gca.talismancreator.managers.Talisman;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class TalismanManageLore extends SpigotGUIComponents {

    public TalismanManageLore(Player p, Talisman talisman){

        // Check conditions.
        if (p == null || talisman == null){
            return;
        }

        // Params
        int size = 9*6;
        GUIListener.getInstance().addTalismanEditing(p, talisman);

        // Create Buttons
        ItemStack legendButton = createButton(XMaterial.BOOK.parseItem(), createLore("&8 - Click to edit", "&8 - Right Click to delete", " ", "&cBE CAREFUL, You can't move lores!", "&8If you want to add a line between", "&8The already existing ones, you need", "&8To delete them in order."), "&6Legend:");
        ItemStack addLineButton = createButton(XMaterial.FEATHER.parseItem(), createLore("&8Click to add"), "&6Add Line");

        // Create Inventory.
        Inventory inv = Bukkit.createInventory(null, size, TalismanCreator.colorFormat("&6Talisman Manage Lores"));

        // Add lore buttons.
        for (String lore : talisman.getLore()){
            inv.addItem(createButton(XMaterial.PAPER.parseItem(), createLore("&8 - Left click to edit", "&8 - Right click to delete"), "&5" + lore));
        }

        // Add Buttons to Inventory.
        inv.setItem(size - 1, getCloseGUIButton());
        inv.setItem(49, legendButton);
        inv.setItem(51, addLineButton);

        // Open Inventory.
        openGUI(inv, p);

    }

}
