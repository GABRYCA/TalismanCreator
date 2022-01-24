package me.gca.talismancreator.managers.heads;

import me.gca.talismancreator.TalismanCreator;
import me.gca.talismancreator.managers.heads.database.Category;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Credits to HeadDB by TheSilentPro.
 *
 * @author TheSilentPro
 *
 * https://github.com/TheSilentPro/HeadDB
 * */
public class LocalHead extends Head {

    private UUID uuid;
    private String name;

    public LocalHead(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public ItemStack getItemStack() {
        Validate.notNull(uuid, "uuid must not be null!");

        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setOwningPlayer(Bukkit.getOfflinePlayer(uuid));
        meta.setDisplayName(TalismanCreator.colorFormat("&e" + name));
        List<String> lore = new ArrayList<>();
        lore.add(TalismanCreator.colorFormat("&7UUID: " + uuid.toString()));
        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }

    @Override
    public UUID getUniqueId() {
        return uuid;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getValue() {
        return null;
    }

    @Override
    public Category getCategory() {
        return null;
    }

    @Override
    public int getId() {
        return -1;
    }

    @Override
    public List<String> getTags() {
        return null;
    }

    @Override
    public LocalHead uniqueId(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    @Override
    public LocalHead name(String name) {
        this.name = name;
        return this;
    }

}
