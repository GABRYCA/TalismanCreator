package me.gca.talismancreator;

import me.gca.talismancreator.commands.TalismanCommands;
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
    private static FileConfiguration messagesConfig;
    private static TalismansManager talismansManager;
    private static String pluginPrefix;

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

    public static FileConfiguration getMessagesConfig() {
        return messagesConfig;
    }

    public static String getPluginPrefix() {
        return pluginPrefix;
    }

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new EventsListeners(), this);
        getCommand("talisman").setExecutor(new TalismanCommands());
        getLogger().info(ChatColor.GREEN + "Enabled with success!");
        this.saveDefaultConfig();
        instance = this;
        this.saveConfig();
        messagesConfig = new MessagesConfig().getMessages();
        talismansManager = new TalismansManager();
        pluginPrefix = getConfig().getString("Plugin.PluginPrefix");
    }

    @Override
    public void onDisable() {
        System.out.println(ChatColor.RED  + "[TalismanCreator] Disabled with success!");
    }
}
