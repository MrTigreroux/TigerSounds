package fr.mrtigreroux.tigersounds.managers;

import java.util.Arrays;

import fr.mrtigreroux.tigersounds.TigerSounds;
import fr.mrtigreroux.tigersounds.commands.SoundsCommand;
import fr.mrtigreroux.tigersounds.commands.TigerSoundsCommand;

/**
 * @author MrTigreroux
 */

public class CommandsManager {
	
	public static TigerSounds main = TigerSounds.getInstance();
	
	public static void registerCommands() {
		main.getCommand("sounds").setExecutor(new SoundsCommand());
		main.getCommand("sounds").setAliases(Arrays.asList("sound"));
		main.getCommand("tigersounds").setExecutor(new TigerSoundsCommand());
	}
	
}
