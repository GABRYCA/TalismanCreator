package me.gca.talismancreator.managers;

import com.cryptomorin.xseries.XMaterial;
import com.cryptomorin.xseries.XPotion;
import me.gca.talismancreator.TalismanCreator;

import java.util.ArrayList;
import java.util.List;

public class Talisman {

    private XMaterial xMaterial;
    private String title;
    private List<String> lore;
    private List<XPotion> effects;

    public Talisman(XMaterial xMaterial, String title, List<XPotion> effects){
        this.xMaterial = xMaterial;
        this.title = TalismanCreator.colorFormat(title);
        List<String> tempLore = new ArrayList<>();
        tempLore.add(TalismanCreator.colorFormat(TalismanCreator.getInstance().getConfig().getString("Talisman.DefaultLore")));
        this.lore = TalismanCreator.colorFormat(tempLore);
        this.effects = effects;
    }

    public Talisman(XMaterial xMaterial, String title, List<XPotion> effects, List<String> lore){
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

    public List<XPotion> getEffects() {
        return effects;
    }

    public void setEffects(List<XPotion> effects) {
        this.effects = effects;
    }

    @Override
    public String toString() {
        return "xMaterial: " + xMaterial +
        "\ntitle:" + title +
        "\nlore:" + lore +
        "\neffects:" + effects;
    }
}
