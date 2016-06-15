package fr.mrtigreroux.tigersounds.data;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Material;

import fr.mrtigreroux.tigersounds.objects.menus.Menu;

/**
 * @author MrTigreroux
 */

public class UserData {

	public static HashMap<UUID, Menu>    MenuOpened = new HashMap<UUID, Menu>();
	public static HashMap<UUID, Integer> MenuGroup = new HashMap<UUID, Integer>();
	public static HashMap<UUID, Integer> MenuPage = new HashMap<UUID, Integer>();
	public static HashMap<UUID, String>  MenuSound = new HashMap<UUID, String>();
	public static HashMap<UUID, String>  MenuAction = new HashMap<UUID, String>();

	public static HashMap<UUID, String>   SettingModified = new HashMap<UUID, String>();
	public static HashMap<UUID, Material> SignMaterial = new HashMap<UUID, Material>();
	public static HashMap<UUID, Byte>     SignData = new HashMap<UUID, Byte>();
	
}
