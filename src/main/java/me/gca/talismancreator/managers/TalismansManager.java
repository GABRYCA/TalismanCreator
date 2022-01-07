package me.gca.talismancreator.managers;

import me.gca.talismancreator.TalismanCreator;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class TalismansManager {

    private List<Talisman> talismans = new ArrayList<>();
    private FileConfiguration conf = TalismanCreator.getInstance().getConfig();
    private final FileConfiguration messages = TalismanCreator.getMessagesConfig();

    public TalismansManager(){
        int counter = 0;
        // Check if there're Talismans in the config.
        if (conf.getConfigurationSection("Talismans") == null){
            TalismanCreator.getInstance().getLogger().info("Loaded " + counter + " Talismans.");
            return;
        }
        // For each Talisman read.
        for (String key : conf.getConfigurationSection("Talismans").getKeys(false)){
            // Temporary Effects loaded.
            List<PotionEffect> potionEffects = new ArrayList<>();
            // Check if null.
            if (conf.getConfigurationSection("Talismans." + key + ".Effects") != null){
                // Read each effect and validate it.
                for (String pKey : conf.getConfigurationSection("Talismans." + key + ".Effects").getKeys(false)){
                    PotionEffectType type = PotionEffectType.getByName(pKey);
                    // Type mustn't be null, otherwise it is invalid.
                    if (type != null){
                        PotionEffect effect = new PotionEffect(type, Integer.MAX_VALUE, conf.getInt("Talismans." + key + ".Effects." + pKey));
                        potionEffects.add(effect);
                    }
                }
            }
            // Add new Talisman to Cached list.
            talismans.add(new Talisman(conf.getItemStack("Talismans." + key + ".ItemStack"), potionEffects));
            counter++;
        }
        // Just a message.
        TalismanCreator.getInstance().getLogger().info("Loaded " + counter + " Talismans.");
    }

    /**
     * Apply Talismans in Player Inventory.
     * */
    public void applyTalismansToPlayer(Player p){
        Inventory inv = p.getInventory();
        // For each Talisman available
        for (Talisman talisman : talismans){
            // Check if the ItemStack of the Talisman is in the Player Inventory.
            if (inv.containsAtLeast(talisman.getItemStack(), 1)){
                // For each effect of the Talisman effect.
                for (PotionEffect effect : talisman.getEffects()){
                    // Check if Player already has a similar effect.
                    if (p.hasPotionEffect(effect.getType())){
                        PotionEffect pPlayer = p.getPotionEffect(effect.getType());
                        if (pPlayer == null){
                            // Apply effect.
                            p.addPotionEffect(effect);
                            TalismanCreator.getInstance().getLogger().info(TalismanCreator.colorFormat(messages.getString("Messages.Talisman_Effect_Applied_Success")));
                        // If the Talisman has a stronger effect than the already applied one, then apply it.
                        } else if (pPlayer.getAmplifier() < effect.getAmplifier()){
                            // Apply effect.
                            p.addPotionEffect(effect);
                            TalismanCreator.getInstance().getLogger().info(TalismanCreator.colorFormat(messages.getString("Messages.Talisman_Effect_Applied_Success")));
                        }
                    } else {
                        // Apply effect.
                        p.addPotionEffect(effect);
                        TalismanCreator.getInstance().getLogger().info(TalismanCreator.colorFormat(messages.getString("Messages.Talisman_Effect_Applied_Success")));
                    }
                }
            }
        }
    }

    /**
     * Add Talisman to cache and save it to config.
     * */
    public void addTalisman(Talisman talisman){
        if (talismans.contains(talisman)){
            // Already existing Talisman.
            TalismanCreator.getInstance().getLogger().warning(TalismanCreator.colorFormat(messages.getString("Messages.Talisman_Already_Existing")));
            return;
        }
        for(Talisman talisman1 : talismans){
            if (talisman1.getTitle().equalsIgnoreCase(talisman.getTitle())){
                // Same title as already existing Talisman.
                TalismanCreator.getInstance().getLogger().warning(TalismanCreator.colorFormat(messages.getString("Messages.Talisman_Same_Title")));
                return;
            }
        }
        talismans.add(talisman);
        int free = 0;
        while (conf.getConfigurationSection("Talismans." + free) != null){
            free++;
        }
        TalismanCreator.getInstance().getConfig().set("Talismans." + free + ".ItemStack", talisman.getItemStack());
        for (PotionEffect effect : talisman.getEffects()){
            TalismanCreator.getInstance().getConfig().set("Talismans." + free + ".Effects." + effect.getType().getName(), effect.getAmplifier());
        }
        TalismanCreator.getInstance().saveConfig();
        TalismanCreator.getInstance().getLogger().info(TalismanCreator.colorFormat(messages.getString("Messages.Talisman_Add_Success")));
        this.conf = TalismanCreator.getInstance().getConfig();
    }

    /**
     * Remove Talisman from Cache and config.
     * */
    public void removeTalisman(Talisman talisman){
        if (talismans.contains(talisman)){
            talismans.remove(talisman);
            // Search in the config for the Talisman identified by his ItemStack.
            for (String key : conf.getConfigurationSection("Talismans").getKeys(false)){
                ItemStack itemStack = conf.getItemStack("Talismans." + key + ".ItemStack");
                if (itemStack != null && itemStack.isSimilar(talisman.getItemStack())) {
                    // Found it.
                    TalismanCreator.getInstance().getConfig().set("Talismans." + key, null);
                    TalismanCreator.getInstance().saveConfig();
                    TalismanCreator.getInstance().getLogger().info(TalismanCreator.colorFormat(messages.getString("Messages.Talisman_Remove_Success")));
                    this.conf = TalismanCreator.getInstance().getConfig();
                    return;
                }
            }
            return;
        }
        // Not found.
        TalismanCreator.getInstance().getLogger().warning(TalismanCreator.colorFormat(messages.getString("Messages.Talisman_Not_Found")));
    }

    /**
     * Remove Talisman from cache and config by name.
     * */
    public void removeTalisman(String title){
        for (Talisman talisman : talismans){
            if (talisman.getTitle().equalsIgnoreCase(title)){
                // Found it.
                removeTalisman(talisman);
                return;
            }
        }
        // Not found.
        TalismanCreator.getInstance().getLogger().warning(TalismanCreator.colorFormat(messages.getString("Messages.Talisman_Not_Found")));
    }

    /**
     * Edit old Talisman.
     * */
    public void editTalisman(Talisman oldTalisman, Talisman newTalisman){
        if (!talismans.contains(oldTalisman)){
            // There isn't such a Talisman.
            TalismanCreator.getInstance().getLogger().warning(TalismanCreator.colorFormat(messages.getString("Messages.Talisman_Not_Found")));
            return;
        }
        talismans.remove(oldTalisman);
        removeTalisman(oldTalisman);
        talismans.add(newTalisman);
        addTalisman(newTalisman);
        TalismanCreator.getInstance().getLogger().info(TalismanCreator.colorFormat(messages.getString("Messages.Talisman_Edited_Success")));
    }

    /**
     * Validate ItemStack to check if it's an existing Talisman.
     * */
    public boolean isTalisman(ItemStack itemStack){
        for (Talisman talisman : talismans){
            if (talisman.getItemStack().isSimilar(itemStack)){
                return true;
            }
        }
        return false;
    }

    /**
     * Get Talisman by name - title.
     * */
    public Talisman getTalisman(String title){
        for (Talisman talisman : talismans){
            if (talisman.getTitle().equalsIgnoreCase(title)){
                return talisman;
            }
        }
        // Not found.
        return null;
    }
}
