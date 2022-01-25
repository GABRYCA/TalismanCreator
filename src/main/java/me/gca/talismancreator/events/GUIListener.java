package me.gca.talismancreator.events;

import com.cryptomorin.xseries.SkullUtils;
import com.cryptomorin.xseries.XMaterial;
import me.gca.talismancreator.TalismanCreator;
import me.gca.talismancreator.gui.*;
import me.gca.talismancreator.managers.Talisman;
import org.bukkit.Bukkit;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class GUIListener implements Listener {

    private static GUIListener instance;
    private static List<Player> playersUsingGUI = new ArrayList<>();
    private static List<Player> playersUsingChat = new ArrayList<>();
    private static HashMap<Player, Talisman> talismanEditing = new HashMap<>();
    private static final Configuration messages = TalismanCreator.getMessagesConfig();
    private static final String pluginPrefix = TalismanCreator.getPluginPrefix();
    private int id;
    private String mode;
    private int beingEditedLore;

    public GUIListener(){}

    public static GUIListener getInstance(){
        if (instance == null){
            instance = new GUIListener();
        }
        return instance;
    }

    public void addTalismanEditing(Player p, Talisman talisman){
        if (!talismanEditing.containsKey(p) && talismanEditing.get(p) == null) {
            talismanEditing.put(p, talisman);
        }
    }

    public void removeTalismanEditing(Player p){
        talismanEditing.remove(p);
    }

    public void addToActivePlayersGUI(Player p){
        if (!playersUsingGUI.contains(p)){
            playersUsingGUI.add(p);
        }
    }

    public void removeFromActivePlayersGUI(Player p){
        playersUsingGUI.remove(p);
    }

    public void addPlayerActiveChat(Player p){
        if (!playersUsingChat.contains(p)){
            playersUsingChat.add(p);
        }
    }

    public void removePlayerActiveChat(Player p){
        playersUsingChat.remove(p);
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e){

        if (!playersUsingChat.contains(e.getPlayer())){
            return;
        }

        Player p = e.getPlayer();
        Bukkit.getScheduler().cancelTask(id);
        String message = e.getMessage();
        if (message.equalsIgnoreCase("Close")){
            removePlayerActiveChat(p);
            removeTalismanEditing(p);
            p.sendMessage(TalismanCreator.colorFormat(pluginPrefix + " &6" + messages.getString("Messages.Cancel_Chat_Success")));
            e.setCancelled(true);
            return;
        }
        if (mode.equalsIgnoreCase("edit")){
            Talisman oldTalisman = talismanEditing.get(p);
            Talisman newTalisman = oldTalisman;
            List<String> lore = oldTalisman.getLore();
            lore.set(beingEditedLore, TalismanCreator.colorFormat(message));
            newTalisman.setLore(lore);
            TalismanCreator.getTalismansManager().editTalisman(oldTalisman, newTalisman);
        } else if (mode.equalsIgnoreCase("add")) {
            Talisman oldTalisman = talismanEditing.get(p);
            Talisman newTalisman = oldTalisman;
            List<String> lore = oldTalisman.getLore();
            lore.add(TalismanCreator.colorFormat(message));
            newTalisman.setLore(lore);
            TalismanCreator.getTalismansManager().editTalisman(oldTalisman, newTalisman);
        }
        mode = null;
        removePlayerActiveChat(p);
        removeTalismanEditing(p);
        e.setCancelled(true);
        p.sendMessage(TalismanCreator.colorFormat(pluginPrefix + " &6" + messages.getString("Messages.Talisman_Edit_Success")));
    }

    @EventHandler
    public void onInventoryCloseEvent(InventoryCloseEvent e){
        if (e.getPlayer() instanceof Player p){
            if (playersUsingGUI.contains(p)){
                removeFromActivePlayersGUI(p);
                removeTalismanEditing(p);
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){

        if (e.getWhoClicked() instanceof Player){
            Player p = (Player) e.getWhoClicked();
            // Check if contains player clicking inventory.
            if (!playersUsingGUI.contains(p)){
                return;
            }
            ItemStack button = e.getCurrentItem();
            // Check if button is valid.
            if (button == null || !button.hasItemMeta() || !button.getItemMeta().hasDisplayName()){
                e.setCancelled(true);
                return;
            }
            String title = e.getView().getTitle().substring(2);
            String buttonTitle = button.getItemMeta().getDisplayName().substring(2);
            if (buttonTitle.equalsIgnoreCase("Close")){
                p.closeInventory();
                p.sendMessage(TalismanCreator.colorFormat(pluginPrefix + " &6" + messages.getString("Messages.GUI_Close_Success")));
                e.setCancelled(true);
                return;
            }

            // Switch between GUI titles.
            switch (title){

                case "Talisman Edit" -> {

                    if (talismanEditing.get(p) == null){
                        p.closeInventory();
                        e.setCancelled(true);
                        return;
                    }

                    switch (buttonTitle){
                        case "Manage Item" -> {
                            Talisman talisman = talismanEditing.get(p);
                            new TalismanManageItem(p, talisman);
                            addTalismanEditing(p, talisman);
                        }
                        case "Manage Effects" -> {
                            Talisman talisman = talismanEditing.get(p);
                            new TalismanManageEffects(p, talisman);
                            addTalismanEditing(p, talisman);
                        }
                        case "Manage Lore" -> {
                            Talisman talisman = talismanEditing.get(p);
                            new TalismanManageLore(p, talisman);
                            addTalismanEditing(p, talisman);
                            e.setCancelled(true);
                        }
                        default -> {
                            e.setCancelled(true);
                        }
                    }
                    return;
                }

                case "Talisman Manage Item" -> {

                    if (talismanEditing.get(p) == null){
                        p.closeInventory();
                        p.sendMessage(TalismanCreator.colorFormat(pluginPrefix + " &cCached Talisman not found!"));
                        return;
                    }

                    switch (buttonTitle){
                        case "Choose from Items" ->{
                            Talisman talisman = talismanEditing.get(p);
                            new TalismanItemsGUI(p, talisman, 0);
                            addTalismanEditing(p, talisman);
                        }
                        case "Choose from Heads" -> {
                            Talisman talisman = talismanEditing.get(p);
                            new TalismanHeadsGUI(p, talisman, 0);
                            addTalismanEditing(p, talisman);
                        }
                        default -> {
                            e.setCancelled(true);
                        }
                    }
                }

                case "Talisman Items" -> {
                    if (talismanEditing.get(p) == null){
                        p.closeInventory();
                        e.setCancelled(true);
                        return;
                    }

                    String[] parts = buttonTitle.split(" ");
                    if (parts.length == 1){
                        Talisman oldTalisman = talismanEditing.get(p);
                        ItemMeta meta = oldTalisman.getItemStack().getItemMeta();
                        ItemStack itemClicked = e.getCurrentItem();
                        itemClicked.setItemMeta(meta);
                        Talisman newTalisman = oldTalisman;
                        newTalisman.setxMaterial(XMaterial.matchXMaterial(itemClicked));
                        newTalisman.setSkull(false);
                        TalismanCreator.getTalismansManager().editTalisman(oldTalisman, newTalisman);
                        p.closeInventory();
                        p.sendMessage(TalismanCreator.colorFormat(pluginPrefix + " &6" + messages.getString("Messages.Talisman_Edit_Success")));
                    } else if (parts.length == 2){
                        Talisman talisman = talismanEditing.get(p);
                        if (parts[0].equalsIgnoreCase("Previous-Page")){
                            new TalismanItemsGUI(p, talismanEditing.get(p), Integer.parseInt(parts[1]));
                            addTalismanEditing(p, talisman);
                        } else if (parts[0].equalsIgnoreCase("Next-Page")){
                            new TalismanItemsGUI(p, talismanEditing.get(p), Integer.parseInt(parts[1]));
                            addTalismanEditing(p, talisman);
                        }
                    }
                    e.setCancelled(true);
                }

                case "Talisman Heads" -> {
                    if (talismanEditing.get(p) == null){
                        p.closeInventory();
                        e.setCancelled(true);
                        return;
                    }

                    String[] parts = buttonTitle.split(" ");
                    if (parts.length == 1){
                        Talisman oldTalisman = talismanEditing.get(p);
                        TalismanCreator.getTalismansManager().editTalisman(oldTalisman, new Talisman(oldTalisman.getTitle(), buttonTitle, oldTalisman.getLore(), oldTalisman.getEffects()));
                        p.closeInventory();
                        p.sendMessage(TalismanCreator.colorFormat(pluginPrefix + " &6" + messages.getString("Messages.Talisman_Edit_Success")));
                    } else if (parts.length == 2){
                        Talisman talisman = talismanEditing.get(p);
                        if (parts[0].equalsIgnoreCase("Previous-Page")){
                            new TalismanHeadsGUI(p, talismanEditing.get(p), Integer.parseInt(parts[1]));
                            addTalismanEditing(p, talisman);
                        } else if (parts[0].equalsIgnoreCase("Next-Page")){
                            new TalismanHeadsGUI(p, talismanEditing.get(p), Integer.parseInt(parts[1]));
                            addTalismanEditing(p, talisman);
                        }
                    }
                    e.setCancelled(true);
                }

                case "Talisman Manage Effects" -> {

                    if (talismanEditing.get(p) == null){
                        p.closeInventory();
                        p.sendMessage(TalismanCreator.colorFormat(pluginPrefix + " &cCached Talisman not found!"));
                        return;
                    }

                    switch (buttonTitle){
                        case "Remove effects" ->{
                            Talisman talisman = talismanEditing.get(p);
                            new TalismanRemoveEffectsGUI(p, talisman);
                            addTalismanEditing(p, talisman);                        }
                        case "Add effect" -> {
                            Talisman talisman = talismanEditing.get(p);
                            new TalismanEffectsGUI(p, talisman, 0);
                            addTalismanEditing(p, talisman);
                        }
                        default -> {
                            e.setCancelled(true);
                        }
                    }

                }

                case "Talisman Effects" -> {
                    if (talismanEditing.get(p) == null){
                        p.closeInventory();
                        p.sendMessage(TalismanCreator.colorFormat(pluginPrefix + " &cCached Talisman not found!"));
                        return;
                    }

                    String[] parts = buttonTitle.split(" ");
                    if (parts.length == 1){
                        Talisman oldTalisman = talismanEditing.get(p);
                        List<PotionEffect> potionEffects = oldTalisman.getEffects();
                        PotionEffectType potionEffectType = PotionEffectType.getByName(buttonTitle);
                        if (potionEffectType == null){
                            e.setCancelled(true);
                            p.closeInventory();
                            p.sendMessage(TalismanCreator.colorFormat(pluginPrefix + " &cSomething went wrong, not valid effect!"));
                            return;
                        }
                        potionEffects.add(new PotionEffect(PotionEffectType.getByName(buttonTitle), Integer.MAX_VALUE, 1));
                        Talisman newTalisman = oldTalisman;
                        newTalisman.setEffects(potionEffects);
                        TalismanCreator.getTalismansManager().editTalisman(oldTalisman, newTalisman);
                        p.closeInventory();
                        p.sendMessage(TalismanCreator.colorFormat(pluginPrefix + " &6" + messages.getString("Messages.Talisman_Edit_Success")));
                    } else if (parts.length == 2){
                        Talisman talisman = talismanEditing.get(p);
                        if (parts[0].equalsIgnoreCase("Previous-Page")){
                            new TalismanEffectsGUI(p, talisman, Integer.parseInt(parts[1]));
                            addTalismanEditing(p, talisman);
                        } else if (parts[0].equalsIgnoreCase("Next-Page")){
                            new TalismanEffectsGUI(p, talisman, Integer.parseInt(parts[1]));
                            addTalismanEditing(p, talisman);
                        }
                    }
                    e.setCancelled(true);
                }

                case "Talisman Remove Effect" -> {
                    if (talismanEditing.get(p) == null){
                        p.closeInventory();
                        e.setCancelled(true);
                        return;
                    }

                    Talisman oldTalisman = talismanEditing.get(p);
                    List<PotionEffect> potionEffects = oldTalisman.getEffects();
                    PotionEffectType potionEffectType = PotionEffectType.getByName(buttonTitle);
                    if (potionEffectType == null){
                        e.setCancelled(true);
                        p.closeInventory();
                        p.sendMessage(TalismanCreator.colorFormat(pluginPrefix + " &cSomething went wrong, not valid effect!"));
                        return;
                    }
                    potionEffects.remove(new PotionEffect(PotionEffectType.getByName(buttonTitle), Integer.MAX_VALUE, 1));
                    Talisman newTalisman = oldTalisman;
                    newTalisman.setEffects(potionEffects);
                    TalismanCreator.getTalismansManager().editTalisman(oldTalisman, newTalisman);
                    p.closeInventory();
                    p.sendMessage(TalismanCreator.colorFormat(pluginPrefix + " &6" + messages.getString("Messages.Talisman_Edit_Success")));
                    e.setCancelled(true);
                }

                case "Talisman Manage Lores" -> {

                    if (talismanEditing.get(p) == null){
                        p.closeInventory();
                        e.setCancelled(true);
                        return;
                    }

                    if (buttonTitle.equals("Legend:")){
                        e.setCancelled(true);
                        return;
                    } else if (buttonTitle.equals("Add Line")){
                        p.sendMessage(TalismanCreator.colorFormat(pluginPrefix + " &6Write &cClose &6to cancel,"));
                        p.sendMessage(TalismanCreator.colorFormat(pluginPrefix + " &6Otherwise write your text and press enter."));
                        p.sendMessage(TalismanCreator.colorFormat(pluginPrefix + " &6This event will close in 30 seconds!"));
                        addPlayerActiveChat(p);
                        mode = "add";
                        id = Bukkit.getScheduler().scheduleSyncDelayedTask(TalismanCreator.getInstance(), () -> {
                            if (playersUsingChat.contains(p)){
                                return;
                            }
                            removePlayerActiveChat(p);
                            removeTalismanEditing(p);
                            p.sendMessage(TalismanCreator.colorFormat(pluginPrefix + " &6" + messages.getString("Messages.Out_Of_Time")));
                            mode = null;
                        }, 20L * 30);
                        Talisman talisman = talismanEditing.get(p);
                        p.closeInventory();
                        addTalismanEditing(p, talisman);
                        return;
                    }
                    if (e.getClick().isRightClick()) {
                        Talisman oldTalisman = talismanEditing.get(p);
                        Talisman newTalisman = oldTalisman;
                        List<String> lores = oldTalisman.getLore();
                        lores.remove(buttonTitle);
                        newTalisman.setLore(lores);
                        TalismanCreator.getTalismansManager().editTalisman(oldTalisman, newTalisman);
                        p.closeInventory();
                        p.sendMessage(TalismanCreator.colorFormat(pluginPrefix + " &6" + messages.getString("Messages.Talisman_Edit_Success")));
                    } else if (e.getClick().isLeftClick()){
                        p.sendMessage(TalismanCreator.colorFormat(pluginPrefix + " &6Write &cClose &6to cancel,"));
                        p.sendMessage(TalismanCreator.colorFormat(pluginPrefix + " &6Otherwise write your text and press enter."));
                        p.sendMessage(TalismanCreator.colorFormat(pluginPrefix + " &6This event will close in 30 seconds!"));
                        beingEditedLore = e.getSlot();
                        mode = "edit";
                        addPlayerActiveChat(p);
                        id = Bukkit.getScheduler().scheduleSyncDelayedTask(TalismanCreator.getInstance(), () -> {
                            if (playersUsingChat.contains(p)){
                                return;
                            }
                            removePlayerActiveChat(p);
                            removeTalismanEditing(p);
                            p.sendMessage(TalismanCreator.colorFormat(pluginPrefix + " &6" + messages.getString("Messages.Out_Of_Time")));
                            mode = null;
                        }, 20L * 30);
                        Talisman talisman = talismanEditing.get(p);
                        p.closeInventory();
                        addTalismanEditing(p, talisman);
                    }
                    e.setCancelled(true);
                }

                case "Talismans GUI" -> {

                    String[] parts = buttonTitle.split(" ");
                    if (parts.length == 2){
                        if (parts[0].equalsIgnoreCase("Previous-Page")){
                            new TalismanTalismansGUI(p, Integer.parseInt(parts[1]));
                            return;
                        } else if (parts[0].equalsIgnoreCase("Next-Page")){
                            new TalismanTalismansGUI(p, Integer.parseInt(parts[1]));
                            return;
                        }
                    }
                    Talisman talisman = TalismanCreator.getTalismansManager().getTalisman(buttonTitle);
                    if (talisman == null){
                        p.sendMessage(TalismanCreator.colorFormat(pluginPrefix + " &cNot found error."));
                        return;
                    }
                    new TalismanEditGUI(p, talisman);
                    addTalismanEditing(p, talisman);
                }

                default -> {
                    p.sendMessage(TalismanCreator.colorFormat(pluginPrefix + " &cSomething went wrong, this GUI title hasn't been found!"));
                    e.setCancelled(true);
                    return;
                }
            }
        }
    }
}
