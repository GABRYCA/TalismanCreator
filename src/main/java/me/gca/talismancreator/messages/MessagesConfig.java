package me.gca.talismancreator.messages;

import me.gca.talismancreator.TalismanCreator;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class MessagesConfig {

    private FileConfiguration messages;
    private HashMap<String, String> defMessages = new HashMap<>() {{
        put("Messages.Missing_Permission", "Sorry but you don't have the permission to use that!");
        put("Messages.Command_Not_Found", "Sorry, command not found!");
        put("Messages.Talisman_Not_Found", "Sorry, Talisman not found!");
        put("Messages.Talisman_Remove_Success", "Talisman removed with success!");
        }};

    public MessagesConfig(){

        // Filepath
        File file = new File(TalismanCreator.getInstance().getDataFolder() + "/Messages.yml");

        if (messages == null){
            try {
                // Create and load.
                file.createNewFile();
                messages = YamlConfiguration.loadConfiguration(file);
                // Here code to write all new strings.
                for (String i : defMessages.keySet()){
                    messages.set(i, defMessages.get(i));
                }
                // Save.
                messages.save(file);
            } catch (IOException e){
                e.printStackTrace();
                return;
            }

        } else {
            // Load config.
            messages = YamlConfiguration.loadConfiguration(file);
            // Check if all strings are already there and if not update it, if updated then save.
            for (String i : defMessages.keySet()){
                if (messages.get(i) == null){
                    messages.set(i, defMessages.get(i));
                }
            }
            try {
                messages.save(file);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
        // Load again the final version.
        messages = YamlConfiguration.loadConfiguration(file);
    }

    public FileConfiguration getMessages(){
        return messages;
    }
}
