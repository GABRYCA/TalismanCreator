package me.gca.talismancreator.gui;

import com.cryptomorin.xseries.XMaterial;
import me.gca.talismancreator.TalismanCreator;
import me.gca.talismancreator.events.GUIListener;
import me.gca.talismancreator.gui.util.SpigotGUIComponents;
import me.gca.talismancreator.managers.Talisman;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class TalismanRemoveEffectsGUI extends SpigotGUIComponents {

    FileConfiguration messages = TalismanCreator.getMessagesConfig();

    public TalismanRemoveEffectsGUI(Player p, Talisman talisman){
        if (p == null || talisman == null){
            return;
        }

        // Params
        int size = 9*6;
        GUIListener.getInstance().addTalismanEditing(p, talisman);

        // Create Inventory.
        Inventory inv = Bukkit.createInventory(null, size, TalismanCreator.colorFormat("&6Talisman Remove Effect"));

        if (talisman.getEffects().isEmpty()){
            p.sendMessage(TalismanCreator.colorFormat("&6" + messages.getString("Messages.Talisman_Effects_None")));
            return;
        }

        for (PotionEffect effect : talisman.getEffects()){
            PotionEffectType effectType = effect.getType();
            inv.addItem(createButton(XMaterial.POTION.parseItem(), createLore("&8Click to remove"), "&6" + effectType.getName()));
        }

        // Add Buttons to Inventory.
        inv.setItem(size - 1, getCloseGUIButton());

        // Open Inventory.
        openGUI(inv, p);
    }

}
