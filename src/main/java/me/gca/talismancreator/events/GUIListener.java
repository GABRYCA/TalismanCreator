package me.gca.talismancreator.events;

import me.gca.talismancreator.TalismanCreator;
import me.gca.talismancreator.gui.TalismanItemsGUI;
import me.gca.talismancreator.gui.TalismanManageItem;
import me.gca.talismancreator.managers.Talisman;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GUIListener implements Listener {

    private static GUIListener instance;
    private static List<Player> playersUsingGUI = new ArrayList<>();
    private static HashMap<Player, Talisman> talismanEditing = new HashMap<>();
    private static final Configuration messages = TalismanCreator.getMessagesConfig();
    private static final String pluginPrefix = TalismanCreator.getPluginPrefix();

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
                            new TalismanManageItem(p, talismanEditing.get(p));
                            addTalismanEditing(p, talisman);
                        }
                        case "Manage Lore" -> {
                            // TODO Open Lore Manage GUI.
                        }
                        case "Manage effects" -> {
                            // TODO Open Effects Manage GUI.
                        }
                        default -> {
                            e.setCancelled(true);
                        }
                    }
                    return;
                }

                case "Talisman Manage Item" -> {

                    if (talismanEditing.get(p) == null){
                        p.sendMessage("Null talisman");
                        p.closeInventory();
                        e.setCancelled(true);
                        return;
                    }

                    switch (buttonTitle){
                        case "Choose from Items" ->{
                            Talisman talisman = talismanEditing.get(p);
                            new TalismanItemsGUI(p, talismanEditing.get(p), 0);
                            addTalismanEditing(p, talisman);
                        }
                        case "Choose from Heads" -> {
                            // TODO Open GUI showing some random Heads with pages.
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
                        Talisman newTalisman = new Talisman(itemClicked, oldTalisman.getEffects());
                        TalismanCreator.getTalismansManager().editTalisman(oldTalisman, newTalisman);
                        p.closeInventory();
                        p.sendMessage(TalismanCreator.colorFormat(pluginPrefix + " &6" + messages.getString("Messages.Talisman_Add_Success")));
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

                default -> {
                    p.sendMessage(TalismanCreator.colorFormat(pluginPrefix + " &cSomething went wrong, this GUI title hasn't been found!"));
                    e.setCancelled(true);
                    return;
                }
            }
        }
    }
}
