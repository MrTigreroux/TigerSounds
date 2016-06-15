package fr.mrtigreroux.tigersounds.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import fr.mrtigreroux.tigersounds.data.Message;
import fr.mrtigreroux.tigersounds.objects.User;

/**
 * @author MrTigreroux
 */

public class InventoryListener implements Listener {

	private boolean isSoundMenu(Inventory inv) {
		String title = inv.getTitle();
		return title.startsWith(Message.GROUPS_TITLE.get()) || title.startsWith(Message.SOUNDS_TITLE.get().replaceAll("_Group_", "")) ||
				title.startsWith(Message.ADVANCED_TITLE.get().replaceAll("_Sound_", "")) || title.startsWith(Message.CONFIRM_SUPPRESSION_FROM_GROUP_TITLE.get().replaceAll("_Sound_", "")) ||
				title.startsWith(Message.CONFIRM_SUPPRESSION_OF_SOUND_TITLE.get().replaceAll("_Sound_", ""));
	}
	
	@EventHandler(ignoreCancelled = true)
	public void onInventoryOpen(InventoryOpenEvent e) {
		if(!(e.getPlayer() instanceof Player)) return;
		User u = new User((Player) e.getPlayer());
		if(u.hasOpenedMenu()) u.closeMenu(false);
	}
	
	@EventHandler(ignoreCancelled = true)
	public void onInventoryDrag(InventoryDragEvent e) {
		if(!(e.getWhoClicked() instanceof Player)) return;
		User u = new User((Player) e.getWhoClicked());
		Inventory inv = e.getInventory();
		if(inv != null && inv.getType() == InventoryType.CHEST && u.hasOpenedMenu()) {
			if(isSoundMenu(inv) && u.hasOpenedMenu()) e.setCancelled(true);
			else u.closeMenu(false);
		}
	}
	
	@EventHandler(ignoreCancelled = true)
	public void onInventoryClick(InventoryClickEvent e) {
		if(!(e.getWhoClicked() instanceof Player)) return;
		User u = new User((Player) e.getWhoClicked());
		Inventory inv = e.getClickedInventory();
		if(inv != null && inv.getType() == InventoryType.CHEST && u.hasOpenedMenu()) {
			if(isSoundMenu(inv) && u.hasOpenedMenu()) {
				e.setCancelled(true);
				u.getOpenedMenu().click(e.getCurrentItem(), e.getSlot(), e.getClick());
			} else u.closeMenu(false);
		}
	}
	
}
