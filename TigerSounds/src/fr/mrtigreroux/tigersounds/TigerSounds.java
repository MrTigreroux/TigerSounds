package fr.mrtigreroux.tigersounds;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import fr.mrtigreroux.tigersounds.managers.CommandsManager;
import fr.mrtigreroux.tigersounds.managers.ListenersManager;
import fr.mrtigreroux.tigersounds.managers.FilesManager;
import fr.mrtigreroux.tigersounds.objects.User;

/**
 * @author MrTigreroux
 * Finished on: 15-06-2016
 */

public class TigerSounds extends JavaPlugin {

	public static TigerSounds instance;
	
	@Override
	public void onEnable() {
		instance = this;
		FilesManager.checkFiles();
		CommandsManager.registerCommands();
		ListenersManager.registerListeners();
	}
	
	@Override
	public void onDisable() {
		for(Player p : Bukkit.getOnlinePlayers()) {
			User u = new User(p);
			if(u.hasOpenedMenu()) u.closeMenu(true);
		}
	}
	
	public static TigerSounds getInstance() {
		return instance;
	}
	
}
