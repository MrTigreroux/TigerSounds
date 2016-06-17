package fr.mrtigreroux.tigersounds.objects;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import fr.mrtigreroux.tigersounds.data.MonsterId;
import fr.mrtigreroux.tigersounds.utils.ReflectionUtils;

/**
 * @author MrTigreroux
 */

public class CustomItem {

	private Material material = null;
	private Integer amount = Integer.valueOf(1);
	private Short damage = Short.valueOf((short) 0);
	private String displayName = null;
	private List<String> lore = null;
	private Map<Enchantment, Integer> enchantments = null;
	private String skullOwner = null;
	private boolean hideFlags = false;
	private boolean glow = false;
	
	public CustomItem() {}

	public Material getMaterial() {
		return skullOwner == null ? material : Material.SKULL_ITEM;
	}

	public Integer getAmount() {
		return amount;
	}

	public Short getDamage() {
		return skullOwner != null ? 3 : damage;
	}

	public String getDisplayName() {
		return displayName;
	}

	public List<String> getLore() {
		return lore;
	}

	public Map<Enchantment, Integer> getEnchantments() {
		return enchantments;
	}

	public CustomItem type(Material material) {
		this.material = material;
		return this;
	}

	public CustomItem amount(Integer amount) {
		this.amount = amount;
		return this;
	}

	public CustomItem damage(Byte damage) {
		this.damage = (short) damage;
		return this;
	}

	public CustomItem damage(Short damage) {
		this.damage = damage;
		return this;
	}
	
	public CustomItem name(String displayName) {
		this.displayName = displayName;
		return this;
	}

	public CustomItem lore(String... strings) {
		if(strings != null) this.lore = Arrays.asList(strings);
		return this;
	}

	public CustomItem lore(List<String> lore) {
		this.lore = lore;
		return this;
	}
	
	public CustomItem skullOwner(String name) {
		skullOwner = name;
		return this;
	}

	public CustomItem enchant(Enchantment enchantment, Integer level) {
		if(enchantments == null) enchantments = new HashMap<Enchantment, Integer>();
		enchantments.put(enchantment, level);
		return this;
	}

	public CustomItem enchants(Map<Enchantment, Integer> enchantments) {
		this.enchantments = enchantments;
		return this;
	}

	public CustomItem hideFlags(boolean hideFlags) {
		this.hideFlags = hideFlags;
		return this;
	}
	
	public CustomItem glow(boolean glow) {
		this.glow = glow;
		return this;
	}

	public ItemStack create() {
		ItemStack item = new ItemStack(getMaterial(), getAmount().intValue(), getDamage());
		if(getMaterial() == Material.MONSTER_EGG && !ReflectionUtils.ver().startsWith("v1_8")) {
			try {
				Object nmsStack = ReflectionUtils.getCBClass("inventory.CraftItemStack").getMethod("asNMSCopy", ItemStack.class).invoke(null, item);
				Object tag = (Boolean)nmsStack.getClass().getMethod("hasTag").invoke(nmsStack) ? nmsStack.getClass().getMethod("getTag").invoke(nmsStack) : ReflectionUtils.getNMSClass("NBTTagCompound").newInstance();
				Object nested = ReflectionUtils.getNMSClass("NBTTagCompound").newInstance();
				nested.getClass().getMethod("setString", String.class, String.class).invoke(nested, "id", MonsterId.getMonster(getDamage()));
				tag.getClass().getMethod("set", String.class, ReflectionUtils.getNMSClass("NBTBase")).invoke(tag, "EntityTag", nested);
				nmsStack.getClass().getMethod("setTag", ReflectionUtils.getNMSClass("NBTTagCompound")).invoke(nmsStack, tag);
				item = (ItemStack) ReflectionUtils.getCBClass("inventory.CraftItemStack").getMethod("asBukkitCopy", ReflectionUtils.getNMSClass("ItemStack")).invoke(null, nmsStack);
			} catch (Exception Error) {}
		}
		if(skullOwner != null) {
			SkullMeta skullM = (SkullMeta) item.getItemMeta();
			skullM.setOwner(skullOwner);
			item.setItemMeta(skullM);
		}
		if(displayName != null || lore != null || hideFlags) {
			ItemMeta itemM = item.getItemMeta();
			if(hideFlags) itemM.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_POTION_EFFECTS, ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_PLACED_ON);
			if(glow) {
				if(enchantments == null || enchantments.size() == 0) enchant(Enchantment.WATER_WORKER, 1);
				if(!itemM.getItemFlags().contains(ItemFlag.HIDE_ENCHANTS)) itemM.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			}
			if(displayName != null) itemM.setDisplayName(displayName);
			if(lore != null) itemM.setLore(lore);
			item.setItemMeta(itemM);
		}
		if(enchantments != null) {
			for(Enchantment enchant : enchantments.keySet()) {
				int level = enchantments.get(enchant).intValue();
				item.addUnsafeEnchantment(enchant, level);
			}
		}
		return item;
	}
	
}
