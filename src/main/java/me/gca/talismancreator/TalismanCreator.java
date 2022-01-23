package me.gca.talismancreator;

import me.gca.talismancreator.commands.TalismanCommands;
import me.gca.talismancreator.events.EventsListeners;
import me.gca.talismancreator.events.GUIListener;
import me.gca.talismancreator.managers.TalismansManager;
import me.gca.talismancreator.managers.heads.HeadAPI;
import me.gca.talismancreator.messages.MessagesConfig;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public final class TalismanCreator extends JavaPlugin {

    public static TalismanCreator instance;
    private static FileConfiguration messagesConfig;
    private static TalismansManager talismansManager;
    private static String pluginPrefix;
    public static final Pattern UUID_PATTERN = Pattern.compile("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-4[0-9a-fA-F]{3}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}");

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

    public static boolean validateUniqueId(String uuid) {
        return UUID_PATTERN.matcher(uuid).matches();
    }

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        instance = this;
        this.saveConfig();
        messagesConfig = new MessagesConfig().getMessages();
        talismansManager = new TalismansManager();
        pluginPrefix = getConfig().getString("Plugin.PluginPrefix");
        Bukkit.getPluginManager().registerEvents(new EventsListeners(), this);
        Bukkit.getPluginManager().registerEvents(new GUIListener(), this);
        getCommand("talisman").setExecutor(new TalismanCommands());
        // BStats
        if (getConfig().getBoolean("Plugin.metrics-bstats")) {
            Metrics metrics = new Metrics(this, 13932);
            getLogger().info(ChatColor.GOLD + "Metrics enabled with success!");
        }
        HeadAPI.getDatabase().updateAsync(heads -> getLogger().info("Fetched " + HeadAPI.getHeads().size() + " heads!"));
        HeadAPI.getDatabase().setRefresh(3600*20);
        getLogger().info(ChatColor.GREEN + "Enabled with success!");
    }

    @Override
    public void onDisable() {
        System.out.println(ChatColor.RED  + "[TalismanCreator] Disabled with success!");
    }
}
