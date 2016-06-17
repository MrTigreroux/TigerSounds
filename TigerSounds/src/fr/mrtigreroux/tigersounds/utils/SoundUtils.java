package fr.mrtigreroux.tigersounds.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import fr.mrtigreroux.tigersounds.data.Message;
import fr.mrtigreroux.tigersounds.managers.FilesManager;
import fr.mrtigreroux.tigersounds.objects.CustomItem;

/**
 * @author MrTigreroux
 */

public class SoundUtils {
	public static String getConfigName(Sound sound) {
		return sound.toString().substring(0, 1)+sound.toString().substring(1).replaceAll("_", "-").toLowerCase();
	}

	public static String getConfigPath(String configName) {
		return "Sounds."+configName;
	}
	
	public static Sound getSound(String configName) {
		Sound bukkitSound = ConfigUtils.getSoundMenu();
		String configSound = FilesManager.getSounds.getString(getConfigPath(configName)+".Bukkit") != null ? FilesManager.getSounds.getString(getConfigPath(configName)+".Bukkit").toUpperCase() : "";
		String bukkitName = configName.toUpperCase().replaceAll("-", "_");
		if(ReflectionUtils.ver().startsWith("v1_9")) {
			configSound = configSound.replaceAll("IDLE", "AMBIENT");
			bukkitName = bukkitName.replaceAll("IDLE", "AMBIENT");
		}
		for(String sound : Arrays.asList(configSound, bukkitName, "ENTITY_"+bukkitName, "BLOCK_"+bukkitName, "ITEM_"+bukkitName, "MUSIC_"+bukkitName)) {
			try {
				bukkitSound = Sound.valueOf(sound); break;
			} catch (Exception InvalidSound) {}
		}
		return bukkitSound;
	}

	public static String getCustomName(String configName) {
		String path = getConfigPath(configName)+".Name";
		return ChatColor.translateAlternateColorCodes(ConfigUtils.getColorCharacter(), FilesManager.getSounds.contains(path) ? ChatColor.RESET+FilesManager.getSounds.getString(path) : getDefaultName(configName));
	}

	public static String getVanillaName(String configName) {
		String path = getConfigPath(configName)+".Vanilla";
		return FilesManager.getSounds.contains(path) ? FilesManager.getSounds.getString(path) : isMobSound(configName) ? "mob."+getSound(configName).toString().toLowerCase().replaceAll("_", ".") : getSound(configName).toString().toLowerCase().replaceAll("_", ".");
	}
	
	public static String getDefaultName(String configName) {
		return Message.DEFAULT_SOUND_NAME.get().replaceAll("_Name_", configName.replaceAll("-", " "));
	}
	
	public static String getBukkitName(String configName) {
		Sound sound = getSound(configName);
		return sound != ConfigUtils.getSoundMenu() ? sound.toString() : Message.NONE_BUKKITNAME.get();
	}
	
	public static String getDescription(String configName) {
		String path = getConfigPath(configName)+".Description";
		return FilesManager.getSounds.contains(path) ? FilesManager.getSounds.getString(path) : Message.NONE_DESCRIPTION.get();
	}
	
	public static String getWord(Sound sound, int part) {
		return sound.toString().split("_")[part];
	}
	
	public static boolean isMobSound(String configName) {
		try {
			return EntityType.valueOf(getWord(getSound(configName), 0)) != null;
		} catch (Exception NotMob) {
			return false;
		}
	}
	
	public static boolean isGlowing(String configName) {
		String path = getConfigPath(configName)+".Glow";
		return FilesManager.getSounds.contains(path) && FilesManager.getSounds.get(path) != null ? ConfigUtils.isEnabled(FilesManager.getSounds, path) : false;
	}
	
	public static ItemStack getItem(String configName) {
		return new CustomItem().type(getMaterial(configName)).damage(getDamage(configName)).skullOwner(getSkullOwner(configName))
				.glow(isGlowing(configName)).hideFlags(true)
				.name(SoundUtils.getCustomName(configName))
				.lore(ChatColor.translateAlternateColorCodes(ConfigUtils.getColorCharacter(), Message.SOUND_DETAILS.get().replaceAll("_Description_", getDescription(configName)).replaceAll("_Bukkit_", getBukkitName(configName)).replaceAll("_Vanilla_", getVanillaName(configName))).replaceAll("_Config_", configName).split(ConfigUtils.getLineBreakSymbol())).create();
	}
	
	public static String getSkullOwner(String configName) {
		String path = getConfigPath(configName)+".Icon";
		if(ConfigUtils.getMaterial(FilesManager.getSounds, path) != null) return null;
		String configSkull = ConfigUtils.getSkull(FilesManager.getSounds, path);
		String firstWord = getSound(configName) != null && isMobSound(configName) ? getWord(getSound(configName), 0) : null;
		return configSkull != null ? configSkull : firstWord != null ? "MHF_"+firstWord.substring(0, 1)+firstWord.substring(1).toLowerCase() : ConfigUtils.getDefaultSkull("Sound");
	}
	
	public static Material getMaterial(String configName) {
		String path = getConfigPath(configName)+".Icon";
		Material configMaterial = ConfigUtils.getMaterial(FilesManager.getSounds, path);
		Material defaultMaterial = ConfigUtils.getDefaultMaterial("Sound");
		return configMaterial != null ? configMaterial : defaultMaterial != null ? defaultMaterial : Material.BARRIER;
	}
	
	public static short getDamage(String configName) {
		String path = getConfigPath(configName)+".Icon";
		return ConfigUtils.getMaterial(FilesManager.getSounds, path) == null && getMaterial(configName) == ConfigUtils.getDefaultMaterial("Sound") ? ConfigUtils.getDefaultDamage("Sound") : ConfigUtils.getDamage(FilesManager.getSounds, path);
	}
	
	public static double getPitch(String configName) {
		String path = getConfigPath(configName)+".Pitch";
		return FilesManager.getSounds.contains(path) && FilesManager.getSounds.get(path) != null ? MessageUtils.roundTo6Places(FilesManager.getSounds.getDouble(path)) : 1f;
	}

	public static int getVolume(String configName) {
		String path = getConfigPath(configName)+".Volume";
		return FilesManager.getSounds.contains(path) && FilesManager.getSounds.get(path) instanceof Integer ? FilesManager.getSounds.getInt(path) : 1;
	}

	public static int getDistance(String configName) {
		String path = getConfigPath(configName)+".Distance";
		return FilesManager.getSounds.contains(path) && FilesManager.getSounds.get(path) instanceof Integer ? FilesManager.getSounds.getInt(path) : 0;
	}
	
	public static List<String> getAllSounds() {
		List<String> sounds = new ArrayList<String>();
		for(Sound sound : Sound.values()) sounds.add(getConfigName(sound));
		for(String sound : FilesManager.getSounds.getConfigurationSection("Sounds").getKeys(false)) if(!sounds.contains(sound)) sounds.add(sound);
		return sounds;
	}
	
}
