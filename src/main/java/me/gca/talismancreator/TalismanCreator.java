package me.gca.talismancreator;

import me.gca.talismancreator.events.EventsListeners;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class TalismanCreator extends JavaPlugin {

    public static TalismanCreator instance;

    public static TalismanCreator getInstance(){
        return instance;
    }

    public static String colorFormat(String string){
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new EventsListeners(), this);
        System.out.println(ChatColor.GREEN  + "[TalismanCreator] Enabled with success!");
        this.saveDefaultConfig();
        instance = this;
        this.saveConfig();
    }

    @Override
    public void onDisable() {
        System.out.println(ChatColor.RED  + "[TalismanCreator] Disabled with success!");
    }
}
