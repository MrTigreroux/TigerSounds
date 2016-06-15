package fr.mrtigreroux.tigersounds.objects.menus;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.mrtigreroux.tigersounds.data.MenuItem;
import fr.mrtigreroux.tigersounds.data.Message;
import fr.mrtigreroux.tigersounds.objects.CustomItem;
import fr.mrtigreroux.tigersounds.objects.User;
import fr.mrtigreroux.tigersounds.utils.SoundUtils;

/**
 * @author MrTigreroux
 */

public abstract class  Menu {

	protected int size;
	protected Player p;
	protected User u;
	
	public Menu(int size, User u) {
		this.size = size;
		this.u = u;
		this.p = u.getPlayer();
	}
	
	public Inventory getInventory(String title, boolean borders) {
		if(title.length() > 32) title = title.substring(0, 29)+"..";
		Inventory inv = Bukkit.createInventory(null, size, title);
		if(borders) {
			ItemStack gui = new CustomItem().type(Material.STAINED_GLASS_PANE).damage((byte) 7).name("").create();
			int size = inv.getSize();
			for(int position = 9; position <= 17; position++) inv.setItem(position, gui);
			for(int position = size-9; position <= size-1; position++) inv.setItem(position, gui);
			inv.setItem(size-5, MenuItem.CLOSE.get());
		}
		return inv;
	}
	
	public void click(ItemStack item, int slot, ClickType click) {
		if(slot == -1 || item == null || item.getType() == Material.AIR || (item.getType() == Material.STAINED_GLASS_PANE && ((slot >= size-9 && slot <= size-1) || (slot >= 9 && slot <= 17)))) return;
		if(slot == size-5 && item.isSimilar(MenuItem.CLOSE.get())) {
			u.closeMenu(true);
			p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 1, 1);
			return;
		}
		
		int page = u.getOpenedPage();
		int newPage = slot == size-7 && item.isSimilar(MenuItem.PAGE_SWITCH_PREVIOUS.get()) ? page-1 : slot == size-3 && item.isSimilar(MenuItem.PAGE_SWITCH_NEXT.get()) ? page+1 : -1;
		if(newPage != -1) {
			u.openMenu(u.getOpenedMenu(), newPage, u.getOpenedGroup(), u.getOpenedSound(), true, u.getConfirmAction());
			p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 1, 1);
			return;
		}
		onClick(item, slot, click);
	}
	
	public abstract void onClick(ItemStack item, int slot, ClickType click);
	
	public void soundClick(int groupNumber, String configName, ClickType click) {
		switch(click) {
			case LEFT: case SHIFT_LEFT: u.playSound(configName); return;
			case MIDDLE: u.openAdvancedMenu(groupNumber, configName, true); return;
			case RIGHT: case SHIFT_RIGHT:
				String vanillaName = SoundUtils.getVanillaName(configName);
				TextComponent playSound = new TextComponent(ChatColor.translateAlternateColorCodes('&', Message.PLAYSOUND.get().replaceAll("_Sound_", SoundUtils.getCustomName(configName))));
				playSound.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/playsound "+vanillaName+" @p \u007E"+SoundUtils.getDistance(configName)+" \u007E \u007E "+SoundUtils.getVolume(configName)+" "+SoundUtils.getPitch(configName)));
				playSound.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(Message.PLAYSOUND_DETAILS.get().replaceAll("_Sound_", vanillaName)).create()));
				p.spigot().sendMessage(playSound);
				u.closeMenu(true);
				return;
			default: return;
		}
	}
	
}
