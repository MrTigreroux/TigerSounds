package fr.mrtigreroux.tigersounds.managers;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import fr.mrtigreroux.tigersounds.TigerSounds;
import fr.mrtigreroux.tigersounds.listeners.InventoryListener;
import fr.mrtigreroux.tigersounds.listeners.SignListener;

/**
 * @author MrTigreroux
 */

public class ListenersManager {

	public static TigerSounds main = TigerSounds.getInstance();
	
	public static void registerListeners() {
		PluginManager pm = Bukkit.getServer().getPluginManager();
		pm.registerEvents(new InventoryListener(), main);
		pm.registerEvents(new SignListener(), main);
	}
	
}
