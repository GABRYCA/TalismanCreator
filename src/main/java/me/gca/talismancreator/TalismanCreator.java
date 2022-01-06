package me.gca.talismancreator;

import me.gca.talismancreator.events.EventsListeners;
import me.gca.talismancreator.managers.TalismansManager;
import me.gca.talismancreator.messages.MessagesConfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class TalismanCreator extends JavaPlugin {

    public static TalismanCreator instance;
    public static MessagesConfig mConfigInstance;
    public static FileConfiguration messagesConfig;
    public static TalismansManager talismansManager;

    public static TalismanCreator getInstance(){
        return instance;
    }

    public static String colorFormat(String string){
        return ChatColor.translateAlternateColorCodes('&', string);
    }
    public static List<String> colorFormat(List<String> strings) {
        List<String> tempString = new ArrayList<>();
        for (String i : strings){
            tempString.add(colorFormat(i));
        }
        return tempString;
    }
    public static TalismansManager getTalismansManager(){
        return talismansManager;
    }

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new EventsListeners(), this);
        System.out.println(ChatColor.GREEN  + "[TalismanCreator] Enabled with success!");
        this.saveDefaultConfig();
        instance = this;
        this.saveConfig();
        mConfigInstance = new MessagesConfig();
        messagesConfig = mConfigInstance.getMessages();
        talismansManager = new TalismansManager();
    }

    @Override
    public void onDisable() {
        System.out.println(ChatColor.RED  + "[TalismanCreator] Disabled with success!");
    }
}
