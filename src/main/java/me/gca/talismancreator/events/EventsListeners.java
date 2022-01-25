package me.gca.talismancreator.events;

import com.cryptomorin.xseries.XMaterial;
import me.gca.talismancreator.TalismanCreator;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

public class EventsListeners implements Listener {

    @EventHandler
    public void onBlockPickup(EntityPickupItemEvent e){
        if (!e.isCancelled()) {
            // Run only if is Player, for now I don't want other entities to be able to use Talismans.
            if (e.getEntity() instanceof Player) {
                TalismanCreator.getTalismansManager().applyTalismanItem((Player) e.getEntity(), e.getItem().getItemStack());
            }
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
        if (!e.isCancelled()) {
            if (TalismanCreator.getTalismansManager().isTalisman(e.getItemDrop().getItemStack())) {
                TalismanCreator.getTalismansManager().applyTalismansToPlayer(e.getPlayer());
            }
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        TalismanCreator.getTalismansManager().applyTalismansToPlayer(e.getPlayer());
    }

    @EventHandler
    public void onPlayerConsumeEvent(PlayerItemConsumeEvent e){
        if (!e.isCancelled()) {
            // If user drinks milk, reapply effects
            ItemStack itemStack = e.getItem();
            if (TalismanCreator.getTalismansManager().isTalisman(e.getItem())){
                e.setCancelled(true);
            } else if (itemStack.getType().equals(XMaterial.MILK_BUCKET.parseMaterial())) {
                TalismanCreator.getTalismansManager().applyTalismansToPlayer(e.getPlayer());
            }
        }
    }

    @EventHandler
    public void onPlayerPlaceEvent(BlockPlaceEvent e){
        if (!e.isCancelled()){
            if (TalismanCreator.getTalismansManager().isTalisman(e.getItemInHand())){
                e.setCancelled(true);
            }
        }
    }
}

