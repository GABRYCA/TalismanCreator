package me.gca.talismancreator.managers;

import me.gca.talismancreator.TalismanCreator;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class TalismansManager {

    private List<Talisman> talismans = new ArrayList<>();
    private FileConfiguration conf = TalismanCreator.getInstance().getConfig();
    private FileConfiguration messages = TalismanCreator.messagesConfig;

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
                        PotionEffect effect = new PotionEffect(type, -1, Integer.parseInt("Talismans." + key + ".Effects." + key));
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
        //TODO
    }

    /**
     * Add Talisman to cache and save it to config.
     * */
    public void addTalisman(Talisman talisman){
        talismans.add(talisman);
        int free = 0;
        while (conf.getConfigurationSection("Talismans." + free) != null){
            free++;
        }
        TalismanCreator.getInstance().getConfig().set("Talismans." + free + ".ItemStack", talisman.getItemStack().serialize());
        for (PotionEffect effect : talisman.getEffects()){
            TalismanCreator.getInstance().getConfig().set("Talismans." + free + ".Effects." + effect.getType().getName(), effect.getAmplifier());
        }
        TalismanCreator.getInstance().saveConfig();
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
                if (conf.getItemStack("Talismans." + key + ".ItemStack") == talisman.getItemStack()){
                    // Trovato.
                    TalismanCreator.getInstance().getConfig().set("Talismans." + key, null);
                    TalismanCreator.getInstance().saveConfig();
                }
            }
            TalismanCreator.getInstance().getLogger().info(TalismanCreator.colorFormat(messages.getString("Messages.Talisman_Remove_Success")));
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
                removeTalisman(talisman);
                return;
            }
        }
        // Not found.
        TalismanCreator.getInstance().getLogger().warning(TalismanCreator.colorFormat(messages.getString("Messages.Talisman_Not_Found")));
    }
}
