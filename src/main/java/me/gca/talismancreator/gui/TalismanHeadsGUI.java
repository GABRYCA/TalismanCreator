package me.gca.talismancreator.gui;

import com.cryptomorin.xseries.XMaterial;
import me.gca.talismancreator.TalismanCreator;
import me.gca.talismancreator.events.Listeners;
import me.gca.talismancreator.gui.util.SpigotGUIComponents;
import me.gca.talismancreator.managers.Talisman;
import me.gca.talismancreator.managers.heads.Head;
import me.gca.talismancreator.managers.heads.HeadAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class TalismanHeadsGUI extends SpigotGUIComponents {

    public TalismanHeadsGUI(Player p, Talisman talisman, int startingPoint){
        if (p == null || talisman == null){
            return;
        }

        // Params
        int size = 9*6;
        Listeners.getInstance().addTalismanEditing(p, talisman);

        // Create Buttons
        ItemStack previousPage = createButton(XMaterial.BOOK.parseMaterial(), 1, createLore("&8Previous page"), "&6Previous-Page " + (startingPoint - 45));
        ItemStack nextPage = createButton(XMaterial.BOOK.parseMaterial(), 1, createLore("&8Next page"), "&6Next-Page " + (startingPoint + 45));

        // Create Inventory.
        Inventory inv = Bukkit.createInventory(null, size, TalismanCreator.colorFormat("&6Talisman Heads"));


        int counter = 0;
        for (Head head : HeadAPI.getDatabase().getHeads()){
            if (counter > startingPoint){
                inv.addItem(createButton(head.getItemStack(), createLore("&8Click to choose"), "&6" + head.getUniqueId().toString()));
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
