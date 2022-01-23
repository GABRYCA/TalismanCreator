package me.gca.talismancreator.gui;

import com.cryptomorin.xseries.XMaterial;
import me.gca.talismancreator.TalismanCreator;
import me.gca.talismancreator.gui.util.SpigotGUIComponents;
import me.gca.talismancreator.managers.Talisman;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class TalismanTalismansGUI extends SpigotGUIComponents {

    public TalismanTalismansGUI(Player p, int startingPoint){
        if (p == null){
            return;
        }

        if (TalismanCreator.getTalismansManager().getTalismans().isEmpty()){
            p.sendMessage(TalismanCreator.colorFormat(TalismanCreator.getPluginPrefix()  + " &6There aren't talismans to show, please use /talisman add to add one."));
            return;
        }

        // Params
        int size = 9*6;
        List<Talisman> talismans = TalismanCreator.getTalismansManager().getTalismans();

        // Create Buttons
        ItemStack previousPage = createButton(XMaterial.BOOK.parseMaterial(), 1, createLore("&8Previous page"), "&6Previous-Page " + (startingPoint - 45));
        ItemStack nextPage = createButton(XMaterial.BOOK.parseMaterial(), 1, createLore("&8Next page"), "&6Next-Page " + (startingPoint + 45));

        // Create Inventory.
        Inventory inv = Bukkit.createInventory(null, size, TalismanCreator.colorFormat("&6Talismans GUI"));

        int counter = 0;
        for (Talisman talisman : talismans){
            if (counter >= startingPoint){
                inv.addItem(createButton(talisman.getItemStack(), createLore("&8Click to choose"), "T:" + talisman.getTitle()));
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
