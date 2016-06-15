package fr.mrtigreroux.tigersounds.data;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import fr.mrtigreroux.tigersounds.objects.CustomItem;
import fr.mrtigreroux.tigersounds.utils.ConfigUtils;

/**
 * @author MrTigreroux
 */

public enum MenuItem {

	GROUPS_ICON(0, Material.CHEST, Message.GROUPS_ICONNAME.get()),
	CLOSE(0, Material.BARRIER, Message.CLOSE.get()),
	PAGE_SWITCH_PREVIOUS(0, Material.FEATHER, Message.PAGE_SWITCH_PREVIOUS.get()),
	PAGE_SWITCH_NEXT(0, Material.FEATHER, Message.PAGE_SWITCH_NEXT.get()),
	GROUP_SWITCH_PREVIOUS(0, Material.FEATHER, Message.GROUP_SWITCH_PREVIOUS.get()),
	GROUP_SWITCH_NEXT(0, Material.FEATHER, Message.GROUP_SWITCH_NEXT.get()),
	SOUND_SWITCH_PREVIOUS(0, Material.FEATHER, Message.SOUND_SWITCH_PREVIOUS.get()),
	SOUND_SWITCH_NEXT(0, Material.FEATHER, Message.SOUND_SWITCH_NEXT.get()),
	
	CHANGE_PITCH(20, Material.STAINED_CLAY, (byte) 5, Message.CHANGE_PITCH.get(), Message.CHANGE_PITCH_DETAILS.get()),
	CHANGE_VOLUME(22, Material.STAINED_CLAY, (byte) 3, Message.CHANGE_VOLUME.get(), Message.CHANGE_VOLUME_DETAILS.get()),
	CHANGE_DISTANCE(24, Material.STAINED_CLAY, (byte) 11, Message.CHANGE_DISTANCE.get(), Message.CHANGE_DISTANCE_DETAILS.get()),
	GLOW(36, Material.NETHER_STAR, Message.GLOW.get(), Message.GLOW_DETAILS.get()),
	CHANGE_NAME(38, Material.SIGN, Message.CHANGE_NAME.get(), Message.CHANGE_NAME_DETAILS.get()),
	CHANGE_DESCRIPTION(39, Material.PAPER, Message.CHANGE_DESCRIPTION.get(), Message.CHANGE_DESCRIPTION_DETAILS.get()),
	CHANGE_VANILLANAME(41, Material.GRASS, Message.CHANGE_VANILLANAME.get(), Message.CHANGE_VANILLANAME_DETAILS.get()),
	CHANGE_BUKKITNAME(42, Material.LAVA_BUCKET, Message.CHANGE_BUKKITNAME.get(), Message.CHANGE_BUKKITNAME_DETAILS.get()),
	REMOVE_SOUND(44, Material.FLINT_AND_STEEL, Message.REMOVE_SOUND.get(), Message.REMOVE_SOUND_DETAILS.get());
	
	private int position;
	private Material material;
	private Byte durability = 0;
	private String name;
	private String details = null;
	
	MenuItem(int position, Material material, String name) {
		this.position = position;
		this.material = material;
		this.name = name;
	}
	
	MenuItem(int position, Material material, String name, String details) {
		this(position, material, name);
		this.details = details;
	}

	MenuItem(int position, Material material, Byte durability, String name, String details) {
		this(position, material, name, details);
		this.durability = durability;
	}
	
	public ItemStack get() {
		return new CustomItem().type(material).damage(durability).name(name).lore(details != null ? details.split(ConfigUtils.getLineBreakSymbol()) : null).create();
	}
	
	public MenuItem name(String name) {
		this.name = name;
		return this;
	}
	
	public MenuItem details(String details) {
		this.details = details;
		return this;
	}
	
	public int getPosition() {
		return position;
	}
	
}
