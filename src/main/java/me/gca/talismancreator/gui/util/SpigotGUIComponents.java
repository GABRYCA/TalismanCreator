package me.gca.talismancreator.gui.util;

import com.cryptomorin.xseries.XMaterial;
import me.gca.talismancreator.TalismanCreator;
import me.gca.talismancreator.events.Listeners;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class SpigotGUIComponents {

    /**
     * Create a button for the GUI using Material.
     *
     * @param id
     * @param amount
     * @param lore
     * @param display
     * */
    protected ItemStack createButton(Material id, int amount, List<String> lore, String display) {

        if (id == null){
            id = XMaterial.BARRIER.parseMaterial();
        }

        ItemStack item = new ItemStack(id, amount);
        ItemMeta meta = item.getItemMeta();
        return getItemStack(item, TalismanCreator.colorFormat(lore), TalismanCreator.colorFormat(display), meta);
    }

    /**
     * Create a button for the GUI using ItemStack.
     *
     * @param item
     * @param lore
     * @param display
     * */
    protected ItemStack createButton(ItemStack item, List<String> lore, String display) {

        if (item == null){
            item = XMaterial.BARRIER.parseItem();
        }

        ItemMeta meta = null;
        if (item != null) {
            meta = item.getItemMeta();
        }

        if (meta == null){
            meta = XMaterial.BARRIER.parseItem().getItemMeta();
        }

        return getItemStack(item, TalismanCreator.colorFormat(lore), TalismanCreator.colorFormat(display), meta);
    }

    /**
     * Get ItemStack of an Item.
     *
     * @param item
     * @param lore
     * @param display
     * @param meta
     * */
    private ItemStack getItemStack(ItemStack item, List<String> lore, String display, ItemMeta meta) {
        if (meta != null) {
            meta.setDisplayName(TalismanCreator.colorFormat(display));
            try {
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            } catch (NoClassDefFoundError ignored){}
            meta.setLore(TalismanCreator.colorFormat(lore));
            item.setItemMeta(meta);
        }

        return item;
    }



    /**
     * Create a Lore for an Item in the GUI.
     *
     * @param lores
     * */
    protected List<String> createLore(String... lores) {
        List<String> results = new ArrayList<>();
        for (String lore : lores) {
            results.add(TalismanCreator.colorFormat(lore) );
        }
        return results;
    }

    /**
     * Close GUI Button.
     * */
    public ItemStack getCloseGUIButton(){
        List<String> lore = createLore("Click to close.");
        return createButton(XMaterial.RED_STAINED_GLASS_PANE.parseItem(), lore, "&cClose");
    }

    /**
     * Open GUI
     * */
    public void openGUI(Inventory inv, Player p){
        p.openInventory(inv);
        Listeners.getInstance().addToActivePlayersGUI(p);
    }
}
