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

public class TalismanManageEffectsGUI extends SpigotGUIComponents {

    public TalismanManageEffectsGUI(Player p, Talisman talisman){
        // Check conditions.
        if (p == null || talisman == null){
            return;
        }

        // Params
        int size = 9*3;
        Listeners.getInstance().addTalismanEditing(p, talisman);

        // Create Buttons
        ItemStack removeEffectButton = createButton(XMaterial.BREWING_STAND.parseItem(), createLore("&8Click to manage"), "&6Remove effects");
        ItemStack addEffectButton = createButton(XMaterial.BREAD.parseItem(), createLore("&8Click to manage"), "&6Add effect");
        ItemStack editIntensityButton = createButton(XMaterial.REDSTONE.parseItem(), createLore("&8Click to manage."), "&6Edit effects intensity");

        // Create Inventory.
        Inventory inv = Bukkit.createInventory(null, size, TalismanCreator.colorFormat("&6Talisman Manage Effects"));

        // Add Buttons to Inventory.
        inv.setItem(size - 1, getCloseGUIButton());
        inv.setItem(10, removeEffectButton);
        inv.setItem(13, addEffectButton);
        inv.setItem(16, editIntensityButton);

        // Open Inventory.
        openGUI(inv, p);
    }
}
