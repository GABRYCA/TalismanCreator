package me.gca.talismancreator.events;

import me.gca.talismancreator.TalismanCreator;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class EventsListeners implements Listener {

    @EventHandler
    public void onBlockPickup(EntityPickupItemEvent e){
        // Run only if is Player, for now I don't want other entities to be able to use Talismans.
        if (e.getEntity() instanceof Player){
            TalismanCreator.getTalismansManager().applyTalismanItem((Player) e.getEntity(), e.getItem().getItemStack());
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e){
        // If KeepInventory is active, reload Talismans.
        if (e.getKeepInventory()){
            TalismanCreator.getTalismansManager().applyTalismansToPlayer(e.getEntity());
        }
    }

    @EventHandler
    public void onDropEvent(PlayerDropItemEvent e){
        if (TalismanCreator.getTalismansManager().isTalisman(e.getItemDrop().getItemStack())){
            TalismanCreator.getTalismansManager().applyTalismansToPlayer(e.getPlayer());
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        TalismanCreator.getTalismansManager().applyTalismansToPlayer(e.getPlayer());
    }
}

