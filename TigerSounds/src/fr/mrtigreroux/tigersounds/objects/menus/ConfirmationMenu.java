package fr.mrtigreroux.tigersounds.objects.menus;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.mrtigreroux.tigersounds.data.Message;
import fr.mrtigreroux.tigersounds.managers.FilesManager;
import fr.mrtigreroux.tigersounds.objects.CustomItem;
import fr.mrtigreroux.tigersounds.objects.User;
import fr.mrtigreroux.tigersounds.utils.ConfigUtils;
import fr.mrtigreroux.tigersounds.utils.GroupUtils;
import fr.mrtigreroux.tigersounds.utils.MessageUtils;
import fr.mrtigreroux.tigersounds.utils.SoundUtils;

/**
 * @author MrTigreroux
 */

public class ConfirmationMenu extends Menu {

	public ConfirmationMenu(User u) {
		super(27, u);
	}
	
	public void open(int groupNumber, String configName, String action) {
		Inventory inv = getInventory(Message.valueOf("CONFIRM_SUPPRESSION_"+action+"_TITLE").get().replaceAll("_Sound_", configName.replaceAll("-", " ")), false);
		
		ItemStack gui = new CustomItem().type(Material.STAINED_GLASS_PANE).damage((byte) 7).name("").create();
		for(int position : Arrays.asList(1, 2, 3, 5, 6, 7, 10, 12, 14, 16, 19, 20, 21, 23, 24, 25)) inv.setItem(position, gui);
		
		inv.setItem(11, new CustomItem().type(Material.STAINED_CLAY).damage((byte) 5).name(Message.CONFIRM_SUPPRESSION.get()).lore(Message.valueOf("CONFIRM_SUPPRESSION_"+action+"_DETAILS").get().replaceAll("_Sound_", configName).replaceAll("_Group_", GroupUtils.getCustomName(groupNumber)).split(ConfigUtils.getLineBreakSymbol())).create());
		inv.setItem(13, SoundUtils.getItem(configName));
		inv.setItem(15, new CustomItem().type(Material.STAINED_CLAY).damage((byte) 14).name(Message.CANCEL_SUPPRESSION.get()).lore(Message.CANCEL_SUPPRESION_DETAILS.get().split(ConfigUtils.getLineBreakSymbol())).create());
		
		p.openInventory(inv);
		p.playSound(p.getLocation(), ConfigUtils.getSoundMenu(), 1, 1);
	}

	@Override
	public void onClick(ItemStack item, int slot, ClickType click) {
		int groupNumber = u.getOpenedGroup();
		String configName = u.getOpenedSound();
		List<String> soundsList = GroupUtils.getSoundsList(groupNumber);
		String action = u.getConfirmAction();
		switch(slot) {
			case 11:
				if(action.equals("FROM_GROUP") && !soundsList.contains(configName)) {
					MessageUtils.sendErrorMessage(p, Message.SOUND_NOT_IN_GROUP.get().replaceAll("_Sound_", configName).replaceAll("_Group_", GroupUtils.getCustomName(groupNumber)).replaceAll("_Criterion_", ""));
					return;
				}
				soundsList.remove(configName);
				GroupUtils.setSoundsList(groupNumber, soundsList);
				if(action.equals("OF_SOUND")) {
					FilesManager.getSounds.set(SoundUtils.getConfigPath(configName), null);
					FilesManager.saveSounds();
				}
				u.openSoundsMenu(1, groupNumber);
				break;
			case 13: soundClick(groupNumber, configName, click); return;
			case 15: u.openAdvancedMenu(groupNumber, configName, true); return;
			default: return;
		}
	}
	
}
