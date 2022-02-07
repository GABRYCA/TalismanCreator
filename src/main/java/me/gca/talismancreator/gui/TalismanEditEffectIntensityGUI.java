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
import org.bukkit.potion.PotionEffectType;

public class TalismanEditEffectIntensityGUI extends SpigotGUIComponents {

    public TalismanEditEffectIntensityGUI(Player p, Talisman talisman, PotionEffectType effectType, int intensity){

        if (p == null || talisman == null){
            return;
        }

        if (intensity <= 0){
            p.sendMessage(TalismanCreator.colorFormat(TalismanCreator.getPluginPrefix() + " &6Error: You can't set an intensity lower than 1."));
            return;
        }

        // Params
        int size = 9*2;
        Listeners.getInstance().addTalismanEditing(p, talisman);

        // Create Inventory.
        Inventory inv = Bukkit.createInventory(null, size, TalismanCreator.colorFormat("&6Talisman Edit Intensity"));

        ItemStack decreaseButton = createButton(XMaterial.REDSTONE_BLOCK.parseItem(), createLore("&8Click to decrease"), effectType.getName() + " " + intensity + " - " + "1");
        ItemStack increaseButton = createButton(XMaterial.EMERALD_BLOCK.parseItem(), createLore("&8Click to increase"), effectType.getName() + " " + intensity + " + " + "1");
        ItemStack confirmButton = createButton(XMaterial.POTION.parseItem(), createLore("&8Click to confirm"), effectType.getName() + " " + intensity);

        // Add Buttons to Inventory.
        inv.setItem(0, decreaseButton);
        inv.setItem(4, confirmButton);
        inv.setItem(8, increaseButton);
        inv.setItem(size - 1, getCloseGUIButton());

        // Open Inventory.
        openGUI(inv, p);
    }

}
