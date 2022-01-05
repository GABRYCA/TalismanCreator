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
        // Check if there're Talismans in the config.
        if (conf.getConfigurationSection("Talismans") == null){
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
        }
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
        //TODO
    }

}
