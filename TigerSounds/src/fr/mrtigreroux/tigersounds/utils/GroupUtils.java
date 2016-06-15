package fr.mrtigreroux.tigersounds.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import fr.mrtigreroux.tigersounds.data.Message;
import fr.mrtigreroux.tigersounds.managers.FilesManager;
import fr.mrtigreroux.tigersounds.objects.CustomItem;

/**
 * @author MrTigreroux
 */

public class GroupUtils {
	
	public static String getConfigPath(int groupNumber) {
		return "Groups.Group"+groupNumber;
	}
	
	public static String getCustomName(int groupNumber) {
		String path = getConfigPath(groupNumber)+".Name";
		return ChatColor.translateAlternateColorCodes(ConfigUtils.getColorCharacter(), FilesManager.getGroups.contains(path) ? FilesManager.getGroups.getString(path) : getDefaultName(groupNumber));
	}
	
	public static String getDefaultName(int groupNumber) {
		return Message.DEFAULT_GROUP_NAME.get().replaceAll("_Number_", ""+groupNumber);
	}
	
	public static String getDescription(int groupNumber) {
		String path = getConfigPath(groupNumber)+".Description";
		return FilesManager.getGroups.contains(path) ? FilesManager.getGroups.getString(path) : Message.NONE_DESCRIPTION.get();
	}
	
	public static List<String> getSoundsList(int groupNumber) {
		String path = getConfigPath(groupNumber)+".Sounds";
		return FilesManager.getGroups.contains(path) && FilesManager.getGroups.getStringList(path) != null ? FilesManager.getGroups.getStringList(path) : new ArrayList<String>();
	}
	
	public static void setSoundsList(int groupNumber, List<String> soundsList) {
		FilesManager.getGroups.set(getConfigPath(groupNumber)+".Sounds", soundsList);
		FilesManager.saveGroups();
	}
	
	public static int getTotalSounds(int groupNumber) {
		return getSoundsList(groupNumber).size();
	}
	
	public static int getTotalGroups() {
		int totalGroups = 0;
		for(int groupNumber = 1; groupNumber <= Integer.MAX_VALUE; groupNumber++) {
			if(!FilesManager.getGroups.contains(getConfigPath(groupNumber))) break;
			else totalGroups++;
		}
		return totalGroups;
	}
	
	public static ItemStack getItem(int groupNumber) {
		String sounds = null;
		List<String> soundsList = GroupUtils.getSoundsList(groupNumber);
		if(soundsList.size() < 1) sounds = Message.NONE_SOUND.get();
		else if(ConfigUtils.isEnabled(FilesManager.getConfig, "Config.ShowSoundsList")) {
			String defaultSeparation = Message.SEPARATION.get();
			int maxLength = 35;
			for(String sound : soundsList) {
				String soundName = SoundUtils.getCustomName(sound);
				String separation;
				if(sounds != null && sounds.length() >= maxLength) {
					separation = defaultSeparation+ConfigUtils.getLineBreakSymbol();
					maxLength += 45;
				} else separation = defaultSeparation;
				if(sounds == null) sounds = soundName;
				else sounds += separation+soundName;
			}
		} else sounds = "Sounds hidden";
		return new CustomItem().type(getMaterial(groupNumber)).damage(getDamage(groupNumber)).skullOwner(getSkullOwner(groupNumber)).hideFlags(true)
				.name(getCustomName(groupNumber))
				.lore(ChatColor.translateAlternateColorCodes(ConfigUtils.getColorCharacter(), Message.GROUP_DETAILS.get().replaceAll("_Description_", getDescription(groupNumber)).replaceAll("_Number_", ""+groupNumber).replaceAll("_Sounds_", sounds)).split(ConfigUtils.getLineBreakSymbol())).create();
	}
	
	public static String getSkullOwner(int groupNumber) {
		String path = getConfigPath(groupNumber)+".Icon";
		if(ConfigUtils.getMaterial(FilesManager.getGroups, path) != null) return null;
		String configSkull = ConfigUtils.getSkull(FilesManager.getGroups, path);
		return configSkull != null ? configSkull : ConfigUtils.getDefaultSkull("Group");
	}
	
	public static Material getMaterial(int groupNumber) {
		String path = getConfigPath(groupNumber)+".Icon";
		Material configMaterial = ConfigUtils.getMaterial(FilesManager.getGroups, path);
		Material defaultMaterial = ConfigUtils.getDefaultMaterial("Group");
		return configMaterial != null ? configMaterial : defaultMaterial != null ? defaultMaterial : Material.BARRIER;
	}

	public static short getDamage(int groupNumber) {
		String path = getConfigPath(groupNumber)+".Icon";
		return ConfigUtils.getMaterial(FilesManager.getGroups, path) == null && getMaterial(groupNumber) == ConfigUtils.getDefaultMaterial("Group") ? ConfigUtils.getDefaultDamage("Group") : ConfigUtils.getDamage(FilesManager.getGroups, path);
	}
	
}
