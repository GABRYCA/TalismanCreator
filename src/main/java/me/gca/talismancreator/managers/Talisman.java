package me.gca.talismancreator.managers;

import com.cryptomorin.xseries.SkullUtils;
import com.cryptomorin.xseries.XMaterial;
import me.gca.talismancreator.TalismanCreator;
import me.gca.talismancreator.managers.heads.Head;
import me.gca.talismancreator.managers.heads.HeadAPI;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class Talisman {

    private XMaterial xMaterial;
    private String title;
    private List<String> lore;
    private List<PotionEffect> effects;
    private String skullUUID;
    private boolean isSkull = false;

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

    public Talisman(String title, String skullUUID, List<String> lore, List<PotionEffect> effects){
        this.title = TalismanCreator.colorFormat(title);
        this.isSkull = true;
        this.skullUUID = skullUUID;
        this.lore = TalismanCreator.colorFormat(lore);
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
        ItemStack itemStack;
        if (isSkull){
            Head head = HeadAPI.getHeadByUniqueId(UUID.fromString(skullUUID));
            itemStack = head.getItemStack();
            SkullMeta meta = (SkullMeta) itemStack.getItemMeta();
            if (meta != null) {
                meta.setDisplayName(title);
            }
            if (meta != null) {
                meta.setLore(lore);
            }
            itemStack.setItemMeta(meta);
        } else {
            itemStack = xMaterial.parseItem();
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
        }
        return itemStack;
    }

    public boolean isSkull() {
        return isSkull;
    }

    public String getSkullUUID() {
        return skullUUID;
    }

    public void setSkullUUID(String skullUUID) {
        this.skullUUID = skullUUID;
    }

    public void setSkull(boolean skull) {
        isSkull = skull;
    }
}
