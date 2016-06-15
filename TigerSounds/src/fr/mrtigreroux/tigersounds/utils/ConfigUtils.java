package fr.mrtigreroux.tigersounds.utils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import fr.mrtigreroux.tigersounds.managers.FilesManager;

/**
 * @author MrTigreroux
 */

public class ConfigUtils {
	
	private final static Set<String> ActivationWords = new HashSet<String>(Arrays.asList("true", "t" ,"on", "o", "enabled", "yes", "y", "activated", "act", "a"));
	
	public static boolean isEnabled(ConfigurationSection config, String path) {
		return config.get(path) != null && ActivationWords.contains(config.getString(path));
	}
	
	public static char getColorCharacter() {
		return FilesManager.getConfig.contains("Config.ColorCharacter") && FilesManager.getConfig.getString("Config.ColorCharacter") != null && FilesManager.getConfig.getString("Config.ColorCharacter").length() >= 1 ? FilesManager.getConfig.getString("Config.ColorCharacter").charAt(0) : '&';
	}
	
	public static String getLineBreakSymbol() {
		return FilesManager.getConfig.get("Config.LineBreakSymbol") != null ? FilesManager.getConfig.getString("Config.LineBreakSymbol") : "//";
	}
	
	public static Material getMaterial(ConfigurationSection config, String path) {
		String icon = config.getString(path);
		return icon != null && icon.startsWith("Material-") ? Material.matchMaterial(icon.split("-")[1].toUpperCase().replaceAll(":"+getDamage(config, path), "")) : null;
	}
	
	public static Material getDefaultMaterial(String type) {
		return getMaterial(FilesManager.getConfig, "Config.Default"+type+"Icon");
	}
	
	public static short getDamage(ConfigurationSection config, String path) {
		try {
			String icon = config.getString(path);
			return icon != null && icon.startsWith("Material-") && icon.contains(":") ? Short.parseShort(icon.split(":")[1]) : 0;
		} catch(Exception NoDamage) {
			return 0;
		}
	}

	public static short getDefaultDamage(String type) {
		return getDamage(FilesManager.getConfig, "Config.Default"+type+"Icon");
	}
	
	public static String getSkull(ConfigurationSection config, String path) {
		String icon = config.getString(path);
		return icon != null && icon.startsWith("Skull-") ? icon.split("-")[1] : null;
	}

	public static String getDefaultSkull(String type) {
		return getSkull(FilesManager.getConfig, "Config.Default"+type+"Icon");
	}
	
}
