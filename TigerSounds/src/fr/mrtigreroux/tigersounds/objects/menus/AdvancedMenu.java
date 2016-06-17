package fr.mrtigreroux.tigersounds.objects.menus;

import java.util.List;

import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.mrtigreroux.tigersounds.data.MenuItem;
import fr.mrtigreroux.tigersounds.data.Message;
import fr.mrtigreroux.tigersounds.managers.FilesManager;
import fr.mrtigreroux.tigersounds.objects.User;
import fr.mrtigreroux.tigersounds.utils.ConfigUtils;
import fr.mrtigreroux.tigersounds.utils.GroupUtils;
import fr.mrtigreroux.tigersounds.utils.MessageUtils;
import fr.mrtigreroux.tigersounds.utils.SoundUtils;

/**
 * @author MrTigreroux
 */

public class AdvancedMenu extends Menu {

	public AdvancedMenu(User u) {
		super(54, u);
	}
	
	public void open(int groupNumber, String configName, boolean sound) {
		Inventory inv = getInventory(Message.ADVANCED_TITLE.get().replaceAll("_Sound_", configName.replaceAll("-", " ")), true);
		inv.setItem(0, GroupUtils.getItem(groupNumber));
		inv.setItem(4, SoundUtils.getItem(configName));
		if(GroupUtils.getTotalSounds(groupNumber) != 1) {
			inv.setItem(2, MenuItem.SOUND_SWITCH_PREVIOUS.get());
			inv.setItem(6, MenuItem.SOUND_SWITCH_NEXT.get());
		}
		
		for(MenuItem menuItem : MenuItem.values()) if(menuItem.getPosition() != 0) {
			ItemStack item;
			double value;
			switch(menuItem) {
				case CHANGE_PITCH: value = SoundUtils.getPitch(configName); item = menuItem.name(Message.CHANGE_PITCH.get().replaceAll("_Pitch_", MessageUtils.cleanDouble(value))).get(); item.setAmount((int) value); break;
				case CHANGE_VOLUME: value = SoundUtils.getVolume(configName); item = menuItem.name(Message.CHANGE_VOLUME.get().replaceAll("_Volume_", MessageUtils.cleanDouble(value))).get(); item.setAmount((int) value); break;
				case CHANGE_DISTANCE: value = SoundUtils.getDistance(configName); item = menuItem.name(Message.CHANGE_DISTANCE.get().replaceAll("_Distance_", MessageUtils.cleanDouble(value))).get(); item.setAmount((int) value); break;
				default: item = menuItem.get(); break;
			}
			inv.setItem(menuItem.getPosition(), item);
		}
		
		p.openInventory(inv);
		if(sound) p.playSound(p.getLocation(), ConfigUtils.getSoundMenu(), 1, 1);
	}

	@Override
	public void onClick(ItemStack item, int slot, ClickType click) {
		//Player p = u.getPlayer();
		int groupNumber = u.getOpenedGroup();
		String configName = u.getOpenedSound();
		List<String> soundsList = GroupUtils.getSoundsList(groupNumber);
		int soundNumber = soundsList.indexOf(configName);
		if(slot == 0) u.openSoundsMenu(1, groupNumber);
		else if(slot == 4) soundClick(groupNumber, configName, click);
		else if(slot == 2 || slot == 6) {
			soundNumber += slot == 2  ? -1 : 1;
			int totalSounds = soundsList.size();
			if(soundNumber > totalSounds) soundNumber = 1;
			if(soundNumber < 1) soundNumber = totalSounds;
			u.openAdvancedMenu(groupNumber, soundsList.get(soundNumber), true);
		}
		else if(slot >= 18 && slot <= size-9) {
			String path = SoundUtils.getConfigPath(configName);
			if(slot == MenuItem.CHANGE_PITCH.getPosition()) FilesManager.getSounds.set(path+".Pitch", MessageUtils.roundTo6Places(SoundUtils.getPitch(configName)+(click.toString().contains("RIGHT") ? -0.1d : 0.1d)));
			else if(slot == MenuItem.CHANGE_VOLUME.getPosition()) FilesManager.getSounds.set(path+".Volume", SoundUtils.getVolume(configName)+(click.toString().contains("RIGHT") ? -1 : 1));
			else if(slot == MenuItem.CHANGE_DISTANCE.getPosition()) FilesManager.getSounds.set(path+".Distance", SoundUtils.getDistance(configName)+(click.toString().contains("RIGHT") ? -10 : 10));
			else if(slot == MenuItem.GLOW.getPosition()) FilesManager.getSounds.set(path+".Glow", click.toString().contains("LEFT") ? true : false);
			else if(slot == MenuItem.REMOVE_SOUND.getPosition()) {
				String clickString = click.toString();
				if(clickString.contains("LEFT") && !soundsList.contains(configName)) {
					MessageUtils.sendErrorMessage(p, Message.SOUND_NOT_IN_GROUP.get().replaceAll("_Sound_", configName).replaceAll("_Group_", GroupUtils.getCustomName(groupNumber)));
					return;
				}
				u.openConfirmationMenu(groupNumber, configName, clickString.contains("LEFT") ? "FROM_GROUP" : "OF_SOUND");
				/*
				soundsList.remove(configName);
				GroupUtils.setSoundsList(groupNumber, soundsList);
				if(clickString.contains("RIGHT")) {
					FilesManager.getSounds.set(SoundUtils.getConfigPath(configName), null);
					FilesManager.saveSounds();
					u.openSoundsMenu(1, groupNumber);
				}*/
				return;
			}
			else {
				if(slot == MenuItem.CHANGE_NAME.getPosition()) u.editSetting("Name");
				if(slot == MenuItem.CHANGE_DESCRIPTION.getPosition()) u.editSetting("Description");
				if(slot == MenuItem.CHANGE_VANILLANAME.getPosition()) u.editSetting("Vanilla");
				if(slot == MenuItem.CHANGE_BUKKITNAME.getPosition()) u.editSetting("Bukkit");
				return;
			}
			FilesManager.saveSounds();
			u.openAdvancedMenu(groupNumber, configName, false);
			u.playSound(configName);
		}
	}
	
}
