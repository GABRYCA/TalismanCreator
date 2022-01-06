package me.gca.talismancreator.managers;

import com.cryptomorin.xseries.XMaterial;
import me.gca.talismancreator.TalismanCreator;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Talisman {

    private XMaterial xMaterial;
    private String title;
    private List<String> lore;
    private List<PotionEffect> effects;

    public Talisman(XMaterial xMaterial, String title, List<PotionEffect> effects){
        this.xMaterial = xMaterial;
        this.title = TalismanCreator.colorFormat(title);
        List<String> tempLore = new ArrayList<>();
        tempLore.add(TalismanCreator.colorFormat(TalismanCreator.getInstance().getConfig().getString("Talisman.DefaultLore")));
        this.lore = TalismanCreator.colorFormat(tempLore);
        this.effects = effects;
    }

    public Talisman(ItemStack itemStack, List<PotionEffect> effects){
        this.xMaterial = XMaterial.matchXMaterial(itemStack);
        if (itemStack.hasItemMeta()){
            if (itemStack.getItemMeta().hasLore()) {
                this.lore = itemStack.getItemMeta().getLore();
            } else {
                this.lore = Collections.singletonList(TalismanCreator.getInstance().getConfig().getString(TalismanCreator.colorFormat("Talisman.DefaultLore")));
            }
            if (itemStack.getItemMeta().hasDisplayName()) {
                this.title = itemStack.getItemMeta().getDisplayName();
            } else {
                this.title = TalismanCreator.colorFormat("Talisman - " + itemStack.getType().name());
            }
        } else {
            this.lore = Collections.singletonList(TalismanCreator.getInstance().getConfig().getString(TalismanCreator.colorFormat("Talisman.DefaultLore")));
            this.title = TalismanCreator.colorFormat("Talisman - " + itemStack.getType().name());
        }
        this.effects = effects;
    }

    public Talisman(XMaterial xMaterial, String title, List<PotionEffect> effects, List<String> lore){
        this.xMaterial = xMaterial;
        this.title = TalismanCreator.colorFormat(title);
        this.lore = TalismanCreator.colorFormat(lore);
        this.effects = effects;
    }

    public Talisman(XMaterial xMaterial, String title){
        this.xMaterial = xMaterial;
        this.title = TalismanCreator.colorFormat(title);
        List<String> tempLore = new ArrayList<>();
        tempLore.add(TalismanCreator.colorFormat(TalismanCreator.getInstance().getConfig().getString("Talisman.DefaultLore")));
        this.lore = TalismanCreator.colorFormat(tempLore);
    }

    public XMaterial getxMaterial() {
        return xMaterial;
    }

    public void setxMaterial(XMaterial xMaterial) {
        this.xMaterial = xMaterial;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = TalismanCreator.colorFormat(title);
    }

    public List<String> getLore() {
        return lore;
    }

    public void setLore(List<String> lore) {
        this.lore = TalismanCreator.colorFormat(lore);
    }

    public List<PotionEffect> getEffects() {
        return effects;
    }

    public void setEffects(List<PotionEffect> effects) {
        this.effects = effects;
    }

    public ItemStack getItemStack(){
        ItemStack itemStack = xMaterial.parseItem();
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(title);
        if (!lore.isEmpty()) {
            itemMeta.setLore(lore);
        } else {
            List<String> tempLore = new ArrayList<>();
            tempLore.add(TalismanCreator.colorFormat(TalismanCreator.getInstance().getConfig().getString("Talisman.DefaultLore")));
            itemMeta.setLore(tempLore);
        }
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
