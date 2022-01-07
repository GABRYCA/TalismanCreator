package me.gca.talismancreator.commands;

import com.cryptomorin.xseries.XMaterial;
import me.gca.talismancreator.TalismanCreator;
import me.gca.talismancreator.managers.Talisman;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TalismanCommands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String pluginPrefix = TalismanCreator.getPluginPrefix();
        Configuration messages = TalismanCreator.getMessagesConfig();
        if (!sender.hasPermission("talisman.command")){
            sender.sendMessage(TalismanCreator.colorFormat(pluginPrefix + " &c" + messages.getString("Messages.Missing_Permission") + " [talisman.command]"));
            return false;
        }
        if (args.length == 0){
            commandList(sender);
            return true;
        }
        switch (args[0]) {
            case "gui" -> {
                // Must be player to use this command.
                if (!(sender instanceof Player)) {
                    sender.sendMessage(TalismanCreator.colorFormat(pluginPrefix + " &c" + messages.getString("Messages.Player_Only")));
                    return true;
                }
                //TODO
                // Main GUI and admin stuff.
            }
            case "add" -> {
                if (args.length <= 3){
                    sender.sendMessage(TalismanCreator.colorFormat(pluginPrefix + " &eMissing parameters:"));
                    if (args.length == 1){
                        sender.sendMessage(TalismanCreator.colorFormat("&f - &c<Material> <Effect> <Title>"));
                    } else  if (args.length == 2){
                        sender.sendMessage(TalismanCreator.colorFormat("&f - &c<Effect> <Title>"));
                    } else {
                        sender.sendMessage(TalismanCreator.colorFormat("&f - &c<Title>"));
                    }
                    return true;
                }
                Optional<XMaterial> xMaterial = XMaterial.matchXMaterial(args[1]);
                if (xMaterial.isEmpty()){
                    sender.sendMessage(TalismanCreator.colorFormat(pluginPrefix + " &c" + messages.getString("Messages.Talisman_Invalid_Material") + " [" + args[1] + "]"));
                    return true;
                }
                PotionEffectType potionEffectType = PotionEffectType.getByName(args[2]);
                if (potionEffectType == null){
                    sender.sendMessage(TalismanCreator.colorFormat(pluginPrefix + " &c" + messages.getString("Messages.Talisman_Invalid_Effect") + " [" + args[2] + "]"));
                    return true;
                }
                StringBuilder titleBuilder = new StringBuilder();
                for (int i = 3; i < args.length; i++){
                    if (i != 3){
                        titleBuilder.append(" ");
                    }
                    titleBuilder.append(args[i]);
                }
                String title = titleBuilder.toString();
                if (TalismanCreator.getTalismansManager().getTalisman(title) != null){
                    sender.sendMessage(TalismanCreator.colorFormat(pluginPrefix + " &c" + messages.getString("Messages.Talisman_Already_Existing")));
                    return true;
                }
                List<PotionEffect> potionEffects = new ArrayList<>();
                potionEffects.add(new PotionEffect(potionEffectType, Integer.MAX_VALUE, 1));
                TalismanCreator.getTalismansManager().addTalisman(new Talisman(xMaterial.get(), title, potionEffects));
                sender.sendMessage(TalismanCreator.colorFormat(pluginPrefix + " &6" + messages.getString("Messages.Talisman_Add_Success")));
                return true;
            }
            case "remove" -> {
                if (args.length < 2){
                    sender.sendMessage(TalismanCreator.colorFormat(pluginPrefix + " &eMissing parameters:"));
                    sender.sendMessage(TalismanCreator.colorFormat("&f - &c <Title>"));
                    return true;
                }
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 1; i < args.length; i++){
                    if (i != 1){
                        stringBuilder.append(" ");
                    }
                    stringBuilder.append(args[i]);
                }
                String title = stringBuilder.toString();
                Talisman talisman = TalismanCreator.getTalismansManager().getTalisman(title);
                if (talisman == null){
                    sender.sendMessage(TalismanCreator.colorFormat(pluginPrefix + " &c" + messages.getString("Messages.Talisman_Not_Found") + " [" + title + "]"));
                    return true;
                }
                TalismanCreator.getTalismansManager().removeTalisman(talisman);
                sender.sendMessage(TalismanCreator.colorFormat(pluginPrefix + " &6" + messages.getString("Messages.Talisman_Remove_Success")));
                return true;
            }
            case "edit" -> {
                if (args.length < 2){
                    sender.sendMessage(TalismanCreator.colorFormat(pluginPrefix + " &eMissing parameters: "));
                    sender.sendMessage(TalismanCreator.colorFormat("&f - &6<Title>"));
                    return true;
                }
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 2; i < args.length; i++){
                    if (i != 2){
                        stringBuilder.append(" ");
                    }
                    stringBuilder.append(args[i]);
                }
                String title = stringBuilder.toString();
                Talisman talisman = TalismanCreator.getTalismansManager().getTalisman(title);
                if (talisman == null){
                    sender.sendMessage(TalismanCreator.colorFormat(pluginPrefix + " &c" + messages.getString("Messages.Talisman_Not_Found")));
                    return true;
                }
                //TODO
                // Open Talisman manager GUI.
                return true;
            }
            case "give" -> {
                if (args.length <= 2){
                    sender.sendMessage(TalismanCreator.colorFormat(pluginPrefix + " &eMissing parameters: "));
                    if (args.length == 1){
                        sender.sendMessage(TalismanCreator.colorFormat( "&f - &c<Player> <Title>"));
                    } else {
                        sender.sendMessage(TalismanCreator.colorFormat( "&f - &c<Title>"));
                    }
                    return true;
                }
                Player pReceiver = Bukkit.getPlayer(args[1]);
                if (pReceiver == null){
                    sender.sendMessage(TalismanCreator.colorFormat(pluginPrefix + " &c" + messages.getString("Messages.Player_Not_Found")));
                    return true;
                }
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 2; i < args.length; i++){
                    if (i != 2){
                        stringBuilder.append(" ");
                    }
                    stringBuilder.append(args[i]);
                }
                String title = stringBuilder.toString();
                Talisman talisman = TalismanCreator.getTalismansManager().getTalisman(title);
                if (talisman == null){
                    sender.sendMessage(TalismanCreator.colorFormat(pluginPrefix + " &c" + messages.getString("Messages.Talisman_Not_Found")));
                    return true;
                }
                ItemStack talismanItem = talisman.getItemStack();
                talismanItem.setAmount(1);
                pReceiver.getInventory().addItem(talismanItem);
                TalismanCreator.getTalismansManager().applyTalismansToPlayer(pReceiver);
                sender.sendMessage(TalismanCreator.colorFormat(pluginPrefix + " &6" + messages.getString("Messages.Talisman_Give_Success")));
                return true;
            }
            case "list" -> {
                List<Talisman> talismans = TalismanCreator.getTalismansManager().getTalismans();
                if (talismans.isEmpty()){
                    sender.sendMessage(TalismanCreator.colorFormat(pluginPrefix + " &6" + messages.getString("Messages.No_Talismans")));
                    return true;
                }
                sender.sendMessage(TalismanCreator.colorFormat( "&6Talismans:"));
                for (Talisman talisman : talismans){
                    sender.sendMessage(TalismanCreator.colorFormat("&f - &6" + talisman.getTitle()));
                }
                return true;
            }
            default -> {
                sender.sendMessage(TalismanCreator.colorFormat(pluginPrefix + " &c" + messages.getString("Messages.Command_Not_Found")));
                commandList(sender);
                return true;
            }
        }
        return false;
    }

    public void commandList(CommandSender sender){
        sender.sendMessage(TalismanCreator.colorFormat("&6" + TalismanCreator.getPluginPrefix() + ":"));
        sender.sendMessage(TalismanCreator.colorFormat("&f - &6/talisman gui"));
        sender.sendMessage(TalismanCreator.colorFormat("&f - &6/talisman add <Material> <Effect> <Title>"));
        sender.sendMessage(TalismanCreator.colorFormat("&f - &6/talisman remove <Title>"));
        sender.sendMessage(TalismanCreator.colorFormat("&f - &6/talisman edit <Title>"));
        sender.sendMessage(TalismanCreator.colorFormat("&f - &6/talisman give <Player> <Title>"));
        sender.sendMessage(TalismanCreator.colorFormat("&f - &6/talisman list"));
    }
}
