package fr.mrtigreroux.tigersounds.objects.menus;

import org.bukkit.Sound;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.mrtigreroux.tigersounds.data.MenuItem;
import fr.mrtigreroux.tigersounds.data.Message;
import fr.mrtigreroux.tigersounds.objects.User;
import fr.mrtigreroux.tigersounds.utils.GroupUtils;

/**
 * @author MrTigreroux
 */

public class GroupsMenu extends Menu {

	public GroupsMenu(User u) {
		super(54, u);
	}
	
	public void open(int page) {
		Inventory inv = getInventory(Message.GROUPS_TITLE.get(), true);
		
		inv.setItem(4, MenuItem.GROUPS_ICON.get());
		int firstGroup = 1;
		if(page >= 2) {
			inv.setItem(size-7, MenuItem.PAGE_SWITCH_PREVIOUS.get());
			firstGroup = ((page-1)*27)+1;
		}
		int totalGroups = GroupUtils.getTotalGroups();
		for(int groupNumber = firstGroup; groupNumber <= firstGroup+26; groupNumber++) {
			if(groupNumber > totalGroups) break;
			inv.setItem(groupNumber-firstGroup+18, GroupUtils.getItem(groupNumber));
		}
		
		if(firstGroup+26 < totalGroups) inv.setItem(size-3, MenuItem.PAGE_SWITCH_NEXT.get());
		p.openInventory(inv);
		p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 1, 1);
	}

	@Override
	public void onClick(ItemStack item, int slot, ClickType click) {
		if(slot >= 18 && slot <= size-9) u.openSoundsMenu(1, slot-18+((u.getOpenedPage()-1)*27)+1);
	}
	
}
