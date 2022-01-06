package me.gca.talismancreator.commands;

import me.gca.talismancreator.TalismanCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class TalismanCommands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0){
            commandList(sender);
        }
        return true;
    }

    public void commandList(CommandSender sender){
        sender.sendMessage(TalismanCreator.colorFormat("&6TalismanCommands:"));
        sender.sendMessage(TalismanCreator.colorFormat("&f - &6 /talisman gui"));
        sender.sendMessage(TalismanCreator.colorFormat("&f - &6 /talisman add <Material> <Effect> <Title>"));
        sender.sendMessage(TalismanCreator.colorFormat("&f - &6 /talisman remove <Title>"));
    }
}
