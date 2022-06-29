package me.gca.talismancreator.managers.heads;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import me.gca.talismancreator.TalismanCreator;
import me.gca.talismancreator.managers.heads.database.Category;
import org.apache.commons.lang3.Validate;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * Credits to HeadDB by TheSilentPro.
 *
 * @author TheSilentPro
 *
 * https://github.com/TheSilentPro/HeadDB
 * */
public class Head {

    private String name;
    private UUID uuid;
    private String value;
    private Category category;
    private int id;
    private List<String> tags;
    private ItemStack itemStack;

    public Head() {}

    public Head(int id) {
        this.id = id;
    }

    public ItemStack getItemStack() {
        if (itemStack == null) {
            Validate.notNull(name, "name must not be null!");
            Validate.notNull(uuid, "uuid must not be null!");
            Validate.notNull(value, "value must not be null!");

            ItemStack item = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta meta = (SkullMeta) item.getItemMeta();
            meta.setDisplayName(TalismanCreator.colorFormat(category != null ? category.getColor() + name : "&8" + name));
            // set skull owner
            GameProfile profile = new GameProfile(uuid, name);
            profile.getProperties().put("textures", new Property("textures", value));
            Field profileField;
            try {
                profileField = meta.getClass().getDeclaredField("profile");
                profileField.setAccessible(true);
                profileField.set(meta, profile);
            } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException ex) {
                TalismanCreator.getInstance().getLogger().warning("Could not set skull owner for " + uuid.toString() + " | Stack Trace:");
                ex.printStackTrace();
            }

            meta.setLore(Arrays.asList(
                    TalismanCreator.colorFormat("&cID: " + id),
                    TalismanCreator.colorFormat("&e" + buildTagLore(tags)),
                    "",
                    TalismanCreator.colorFormat("&8Right-Click to add/remove from favorites.")
            ));

            item.setItemMeta(meta);
            itemStack = item;
        }

        return itemStack;
    }

    public String getName() {
        return name;
    }

    public UUID getUniqueId() {
        return uuid;
    }

    public String getValue() {
        return value;
    }

    public Category getCategory() {
        return category;
    }

    public int getId() {
        return id;
    }

    public List<String> getTags() {
        return tags;
    }

    public Head name(String name) {
        this.name = name;
        return this;
    }

    public Head uniqueId(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public Head value(String value) {
        this.value = value;
        return this;
    }

    public Head category(Category category) {
        this.category = category;
        return this;
    }

    public Head id(int id) {
        this.id = id;
        return this;
    }

    public Head tags(String tags) {
        this.tags = Arrays.asList(tags.split(","));
        return this;
    }

    private String buildTagLore(List<String> tags) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < tags.size(); i++) {
            builder.append(tags.get(i));
            if (i != tags.size() - 1) {
                builder.append(",");
            }
        }

        return builder.toString();
    }

}