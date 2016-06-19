package fr.mrtigreroux.tigersounds.objects.menus;

import java.util.List;

import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.mrtigreroux.tigersounds.data.MenuItem;
import fr.mrtigreroux.tigersounds.data.Message;
import fr.mrtigreroux.tigersounds.objects.User;
import fr.mrtigreroux.tigersounds.utils.ConfigUtils;
import fr.mrtigreroux.tigersounds.utils.GroupUtils;
import fr.mrtigreroux.tigersounds.utils.SoundUtils;

/**
 * @author MrTigreroux
 */

public class SoundsMenu extends Menu {

	public SoundsMenu(User u) {
		super(54, u);
	}
	
	public void open(int page, int groupNumber) {
		Inventory inv = getInventory(Message.SOUNDS_TITLE.get().replaceAll("_Group_", GroupUtils.getCustomName(groupNumber)), true);
		
		inv.setItem(0, MenuItem.GROUPS_ICON.get());
		inv.setItem(4, GroupUtils.getItem(groupNumber));
		if(GroupUtils.getTotalGroups() != 1) {
			inv.setItem(2, MenuItem.GROUP_SWITCH_PREVIOUS.get());
			inv.setItem(6, MenuItem.GROUP_SWITCH_NEXT.get());
		}
		int firstSound = 1;
		if(page >= 2) {
			inv.setItem(size-7, MenuItem.PAGE_SWITCH_PREVIOUS.get());
			firstSound = ((page-1)*27)+1;
		}
		List<String> soundsList = GroupUtils.getSoundsList(groupNumber);
		int totalSounds = soundsList.size();
		boolean modified = false;
		for(int soundNumber = firstSound; soundNumber <= firstSound+27; soundNumber++) {
			if(soundNumber > totalSounds) break;
			String configName = soundsList.get(soundNumber-1);
			inv.setItem(soundNumber-firstSound+18, SoundUtils.getItem(configName));
		}
		if(modified) GroupUtils.setSoundsList(groupNumber, soundsList);
		if(firstSound+27 < totalSounds) inv.setItem(size-3, MenuItem.PAGE_SWITCH_NEXT.get());
		p.openInventory(inv);
		p.playSound(p.getLocation(), ConfigUtils.getMenuSound(), 1, 1);
	}

	@Override
	public void onClick(ItemStack item, int slot, ClickType click) {
		int groupNumber = u.getOpenedGroup();
		if(slot == 0) u.openGroupsMenu(1);
		else if(slot == 2 || slot == 6) {
			groupNumber += slot == 2  ? -1 : 1;
			int totalGroups = GroupUtils.getTotalGroups();
			if(groupNumber > totalGroups) groupNumber = 1;
			if(groupNumber < 1) groupNumber = totalGroups;
			u.openSoundsMenu(1, groupNumber);
		} else if(slot >= 18 && slot <= size-9) soundClick(groupNumber, GroupUtils.getSoundsList(groupNumber).get(slot-18+((u.getOpenedPage()-1)*27)), click);
	}
	
}
