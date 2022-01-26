package me.gca.talismancreator.events;

import org.bukkit.event.Listener;

public class EventsListeners implements Listener {

    /*@EventHandler
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

    @EventHandler
    public void onInventoryClickItem(InventoryClickEvent e) {
        if (!GUIListener.playersUsingGUI.isEmpty() || GUIListener.playersUsingGUI.contains(e.getView().getPlayer())){
            return;
        }
        if (e.getCursor() == null || e.getClickedInventory() == null){
            return;
        }
        Player p = (Player) e.getView().getPlayer();
        if (e.getClickedInventory().getType() == InventoryType.PLAYER){
            if (e.getCurrentItem() != null){
                if (!TalismanCreator.getTalismansManager().isTalisman(e.getCurrentItem())){
                    return;
                }
            }
            if (e.getView().getCursor() != null){
                if (!TalismanCreator.getTalismansManager().isTalisman(e.getView().getCursor())){
                    return;
                }
            }
            Bukkit.getScheduler().runTaskLater(TalismanCreator.getInstance(), () -> {
                p.updateInventory();
                TalismanCreator.getTalismansManager().applyTalismansToPlayer(p);
            }, 1L);
        } else if (e.isShiftClick()){
            Bukkit.getScheduler().runTaskLater(TalismanCreator.getInstance(), () -> {
                p.updateInventory();
                TalismanCreator.getTalismansManager().applyTalismansToPlayer(p);
            }, 1L);
            return;
        }
        /*if (e.getClickedInventory().getType() != InventoryType.PLAYER) {
            TalismanCreator.getTalismansManager().applyTalismansToPlayer((Player) e.getView().getPlayer());
            return;
        }
        if (TalismanCreator.getTalismansManager().isTalisman(e.getCursor().clone())){
            TalismanCreator.getTalismansManager().applyTalismanItem((Player) e.getView().getPlayer(), e.getCursor().clone());
        }*/
    //}
}

