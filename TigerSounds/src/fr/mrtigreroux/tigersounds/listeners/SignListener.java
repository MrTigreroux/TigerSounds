package fr.mrtigreroux.tigersounds.listeners;

import java.util.UUID;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import fr.mrtigreroux.tigersounds.data.UserData;
import fr.mrtigreroux.tigersounds.managers.FilesManager;
import fr.mrtigreroux.tigersounds.objects.User;
import fr.mrtigreroux.tigersounds.utils.SoundUtils;

/**
 * @author MrTigreroux
 */

public class SignListener implements Listener {

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public void onSignChange(SignChangeEvent e) {
		UUID uuid = e.getPlayer().getUniqueId();
		if(!UserData.SettingModified.containsKey(uuid)) return;
		
		String text = "";
		for(String line : e.getLines()) if(line != null && !line.equals("")) text = text.equals("") ? line : text+" "+line;
		
		User u = new User(e.getPlayer());
		String configName = u.getOpenedSound();
		if(!text.equals("")) {
			FilesManager.getSounds.set(SoundUtils.getConfigPath(configName)+"."+UserData.SettingModified.get(uuid), text);
			FilesManager.saveSounds();
		}
		UserData.SettingModified.remove(uuid);
		u.updateSignBlock(e.getBlock());
		u.openAdvancedMenu(u.getOpenedGroup(), configName, true);
		e.setCancelled(true);
	}
	
}
