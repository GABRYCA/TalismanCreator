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

public class TalismanItemsGUI extends SpigotGUIComponents {

    public TalismanItemsGUI(Player p, Talisman talisman, int startingPoint){
        if (p == null || talisman == null){
            return;
        }

        // Params
        int size = 9*6;
        GUIListener.getInstance().addTalismanEditing(p, talisman);

        // Create Buttons
        ItemStack previousPage = createButton(XMaterial.BOOK.parseMaterial(), 1, createLore("&8Previous page"), "&6Previous-page " + (startingPoint - 45));
        ItemStack nextPage = createButton(XMaterial.BOOK.parseMaterial(), 1, createLore("&8Next page"), "&6Next-page " + (startingPoint + 45));

        // Create Inventory.
        Inventory inv = Bukkit.createInventory(null, size, TalismanCreator.colorFormat("&6Talisman Items"));

        int counter = 0;
        for (XMaterial xMaterial : XMaterial.values()){
            if (counter > startingPoint){
                inv.addItem(createButton(xMaterial.parseItem(), createLore("&8Click to choose"), xMaterial.name()));
            }
            if (counter == startingPoint + 45){
                break;
            }
            counter++;
        }

        // Add Buttons to Inventory.
        inv.setItem(size - 1, getCloseGUIButton());
        if (startingPoint != 0){
            inv.setItem(48, previousPage);
        }
        if (counter == startingPoint + 45){
            inv.setItem(50, nextPage);
        }

        // Open Inventory.
        openGUI(inv, p);
    }
}
