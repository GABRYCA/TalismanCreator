package me.gca.talismancreator.messages;

import me.gca.talismancreator.TalismanCreator;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class MessagesConfig {

    private FileConfiguration messages;
    private final String locale = TalismanCreator.getInstance().getConfig().getString("Plugin.language");
    private final HashMap<String, String> defMessages = new HashMap<>() {{
        put("Messages.Missing_Permission", "Sorry but you don't have the permission to use that!");
        put("Messages.Command_Not_Found", "Sorry, command not found!");
        put("Messages.Talisman_Not_Found", "Sorry, Talisman not found!");
        put("Messages.Talisman_Add_Success", "Talisman added with success.");
        put("Messages.Talisman_Edit_Success", "Talisman edited with success.");
        put("Messages.Talisman_Invalid_Material", "Material not found!");
        put("Messages.Talisman_Invalid_Effect", "Effect not found!");
        put("Messages.Talisman_Effects_None", "There aren't effects to show.");
        put("Messages.Talisman_Edited_Success", "Talisman edited with success.");
        put("Messages.Talisman_Remove_Success", "Talisman removed with success.");
        put("Messages.Talisman_Effect_Applied_Success", "Talisman effect applied with success.");
        put("Messages.Talisman_Already_Existing", "Already existing Talisman with same title!");
        put("Messages.Talisman_Same_Title", "Talisman with same title as already existing Talisman!");
        put("Messages.Talisman_Give_Success", "Talisman given with success!");
        put("Messages.No_Talismans", "There aren't Talismans available.");
        put("Messages.Player_Only", "You can't run this command, hi console!");
        put("Messages.Missing_Player_Name", "Please specify Player name.");
        put("Messages.Player_Not_Found", "Player not found, it may be offline!");
        put("Messages.GUI_Close_Success", "GUI Closed with success!");
    }};

    public MessagesConfig(){

        // Filepath
        File file = new File(TalismanCreator.getInstance().getDataFolder() + "/messages_" + locale + ".yml");

        // Load config.
        messages = YamlConfiguration.loadConfiguration(file);
        int newMessagesCounter = 0;
        // Check if all strings are already there and if not update it, if updated then save.
        for (String i : defMessages.keySet()){
            if (messages.get(i) == null){
                messages.set(i, defMessages.get(i));
                newMessagesCounter++;
            }
        }
        try {
            messages.save(file);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        if (newMessagesCounter > 0) {
            TalismanCreator.getInstance().getLogger().info(TalismanCreator.colorFormat(newMessagesCounter + " new messages added to the messages_" + locale + ".yml with success!"));
        }
        // Load again the final version.
        messages = YamlConfiguration.loadConfiguration(file);
        TalismanCreator.getInstance().getLogger().info(TalismanCreator.colorFormat("Config File messages.yml loaded with success!"));
    }

    public FileConfiguration getMessages(){
        return messages;
    }
}
