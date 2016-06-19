package fr.mrtigreroux.tigersounds.managers;

import java.io.File;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import fr.mrtigreroux.tigersounds.TigerSounds;

/**
 * @author MrTigreroux
 */

public class FilesManager {

	private static TigerSounds main = TigerSounds.getInstance();
	private static String folder = "plugins/TigerSounds";
	public static File config = new File(folder, "config.yml");
	public static FileConfiguration getConfig = YamlConfiguration.loadConfiguration(config);
	public static File messages = new File(folder, "messages.yml");
	public static FileConfiguration getMessages = YamlConfiguration.loadConfiguration(messages);
	public static File groups = new File(folder, "groups.yml");
	public static FileConfiguration getGroups = YamlConfiguration.loadConfiguration(groups);
	public static File sounds = new File(folder, "sounds.yml");
	public static FileConfiguration getSounds = YamlConfiguration.loadConfiguration(sounds);
	
	
	public static void checkFiles() {
		if(!config.exists()) resetConfig(getConfig, config);
		if(!messages.exists()) resetConfig(getMessages, messages);
		if(!groups.exists()) resetConfig(getGroups, groups);
		if(!sounds.exists()) resetConfig(getSounds, sounds);
	}
	
	public static void resetConfig(FileConfiguration fileConfig, File file) {
		main.saveResource(file.getName(), false);
		try {
			fileConfig.load(file);
		} catch (Exception error) {}
		Bukkit.getLogger().log(Level.WARNING, "---------------------------------------------------------------");
		Bukkit.getLogger().log(Level.WARNING, "TigerSounds > Le fichier "+file.getName()+" a ete reinitialise.");
		Bukkit.getLogger().log(Level.WARNING, "---------------------------------------------------------------");
	}

	public static void loadConfig(FileConfiguration fileConfig, File file) {
		try {
			fileConfig.load(file);
		} catch (Exception FileNotFound) {
			if(!file.exists()) resetConfig(fileConfig, file);
			return;
		}
	}
	
	public static void saveConfig(FileConfiguration fileConfig, File file) {
		try {
			fileConfig.save(file);
		} catch (Exception FileNotFound) {
			if(!file.exists()) resetConfig(fileConfig, file);
			return;
		}
	}

	public static void saveConfig() {
		saveConfig(getConfig, config);
	}
	
	public static void saveMessages() {
		saveConfig(getMessages, messages);
	}
	
	public static void saveGroups() {
		saveConfig(getGroups, groups);
	}

	public static void saveSounds() {
		saveConfig(getSounds, sounds);
	}
	
}
