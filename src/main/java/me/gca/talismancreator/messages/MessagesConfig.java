package me.gca.talismancreator.messages;

import me.gca.talismancreator.TalismanCreator;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class MessagesConfig {

    private FileConfiguration messages;

    public MessagesConfig(){

        // Filepath
        File file = new File(TalismanCreator.getInstance().getDataFolder() + "/Messages.yml");

        if (messages == null){
            try {
                // Create and load.
                file.createNewFile();
                messages = YamlConfiguration.loadConfiguration(file);
                // Here code to write all new strings.
                //TODO

                // Save.
                messages.save(file);
            } catch (IOException ex){}

        } else {
            // Load config.
            messages = YamlConfiguration.loadConfiguration(file);
            // Check if all strings are already there and if not update it, if updated then save.
            //TODO

        }
        // Load it again the final version.
        messages = YamlConfiguration.loadConfiguration(file);
    }

}
