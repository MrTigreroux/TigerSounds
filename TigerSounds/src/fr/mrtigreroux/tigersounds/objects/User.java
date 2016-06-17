package fr.mrtigreroux.tigersounds.objects;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

import fr.mrtigreroux.tigersounds.data.UserData;
import fr.mrtigreroux.tigersounds.objects.menus.AdvancedMenu;
import fr.mrtigreroux.tigersounds.objects.menus.ConfirmationMenu;
import fr.mrtigreroux.tigersounds.objects.menus.GroupsMenu;
import fr.mrtigreroux.tigersounds.objects.menus.Menu;
import fr.mrtigreroux.tigersounds.objects.menus.SoundsMenu;
import fr.mrtigreroux.tigersounds.utils.ReflectionUtils;
import fr.mrtigreroux.tigersounds.utils.SoundUtils;

/**
 * @author MrTigreroux
 */

public class User {
	
	private Player p;
	private UUID uuid;
	
	public User(Player p) {
		this.p = p;
		this.uuid = p.getUniqueId();
	}
	
	public Player getPlayer() {
		return p;
	}
	
	public void openGroupsMenu(int page) {
		openMenu(new GroupsMenu(this), page, -1, null, false, null);
	}
	
	public void openSoundsMenu(int page, int groupNumber) {
		openMenu(new SoundsMenu(this), page, groupNumber, null, false, null);
	}

	public void openAdvancedMenu(int groupNumber, String configName, boolean sound) {
		openMenu(new AdvancedMenu(this), -1, groupNumber, configName, sound, null);
	}

	public void openConfirmationMenu(int groupNumber, String configName, String action) {
		openMenu(new ConfirmationMenu(this), -1, groupNumber, configName, false, action);
	}
	
	public void openMenu(Menu menu, int page, int groupNumber, String configName, boolean sound, String action) {
		if(menu instanceof GroupsMenu) ((GroupsMenu) menu).open(page);
		else if(menu instanceof SoundsMenu) ((SoundsMenu) menu).open(page, groupNumber);
		else if(menu instanceof AdvancedMenu) ((AdvancedMenu) menu).open(groupNumber, configName, sound);
		else if(menu instanceof ConfirmationMenu) ((ConfirmationMenu) menu).open(groupNumber, configName, action);
		updateMenuData(menu, page, groupNumber, configName, action);
	}
	
	private void updateMenuData(Menu menu, int page, int groupNumber, String configName, String action) {
		if(menu == null) UserData.MenuOpened.remove(uuid); UserData.MenuOpened.put(uuid, menu);
		if(page == -1) UserData.MenuPage.remove(uuid); else UserData.MenuPage.put(uuid, page);
		if(groupNumber == -1) UserData.MenuGroup.remove(uuid); else UserData.MenuGroup.put(uuid, groupNumber);
		if(configName == null) UserData.MenuSound.remove(uuid); else UserData.MenuSound.put(uuid, configName);
		if(action == null) UserData.MenuAction.remove(uuid); else UserData.MenuAction.put(uuid, action);
	}
	
	public boolean hasOpenedMenu() {
		return UserData.MenuOpened.containsKey(uuid);
	}
	
	public Menu getOpenedMenu() {
		return UserData.MenuOpened.containsKey(uuid) ? UserData.MenuOpened.get(uuid) : null;
	}
	
	public int getOpenedPage() {
		return UserData.MenuPage.containsKey(uuid) ? UserData.MenuPage.get(uuid) : 1;
	}
	
	public int getOpenedGroup() {
		return UserData.MenuGroup.containsKey(uuid) ? UserData.MenuGroup.get(uuid) : -1;
	}
	
	public String getOpenedSound() {
		return UserData.MenuSound.containsKey(uuid) ? UserData.MenuSound.get(uuid) : null;
	}
	
	public String getConfirmAction() {
		return UserData.MenuAction.containsKey(uuid) ? UserData.MenuAction.get(uuid) : null;
	}
	
	public void closeMenu(boolean closeInventory) {
		if(closeInventory) p.closeInventory();
		updateMenuData(null, -1, -1, null, null);
	}

	@SuppressWarnings("deprecation")
	public void editSetting(String setting) {
		try {
			Location loc = p.getLocation();
			World world = loc.getWorld();
			Block b = world.getBlockAt(loc.getBlockX(), world.getMaxHeight()-1, loc.getBlockZ());
			UserData.SignMaterial.put(uuid, b.getType());
			UserData.SignData.put(uuid, b.getData());
			
			Block support = b.getRelative(BlockFace.DOWN);
			if(support.getType() == Material.AIR) support.setType(Material.BEDROCK);
			b.setType(Material.SIGN_POST);
			Sign s = (Sign) b.getState();
			if(!(s instanceof Sign)) return;
			s.setLine(0, "§7[§6TigerSounds§7]");
			s.setLine(1, "§e"+p.getName());
			s.setLine(2, "§8change un");
			s.setLine(3, "§8paramètre");
			s.update();
			
			UserData.SettingModified.put(uuid, setting);
			Object tileEntity = ReflectionUtils.getDeclaredField(s,  "sign");
			ReflectionUtils.setDeclaredField(tileEntity, "isEditable", true);
			ReflectionUtils.setDeclaredField(tileEntity, "h", ReflectionUtils.getHandle(p));
			ReflectionUtils.sendPacket(p,  ReflectionUtils.getPacket("PacketPlayOutOpenSignEditor", ReflectionUtils.callDeclaredConstructor(ReflectionUtils.getNMSClass("BlockPosition"), s.getX(), s.getY(), s.getZ())));
		} catch (Exception Error) {}
	}
	
	@SuppressWarnings("deprecation")
	public void updateSignBlock(Block b) {
		b.setType(UserData.SignMaterial.containsKey(uuid) ? UserData.SignMaterial.get(uuid) : Material.AIR);
		Block support = b.getRelative(BlockFace.DOWN);
		if(support.getType() == Material.BEDROCK) support.setType(Material.AIR);
		if(UserData.SignData.containsKey(uuid)) b.setData(UserData.SignData.get(uuid));
		UserData.SignMaterial.remove(uuid);
		UserData.SignData.remove(uuid);
	}
	
	public void playSound(String configName) {
		p.playSound(p.getLocation().add(SoundUtils.getDistance(configName), 0, 0), SoundUtils.getSound(configName), SoundUtils.getVolume(configName), (float) SoundUtils.getPitch(configName));
	}
	
}
