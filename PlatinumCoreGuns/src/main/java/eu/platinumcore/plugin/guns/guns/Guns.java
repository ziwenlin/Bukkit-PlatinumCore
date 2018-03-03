package eu.platinumcore.plugin.guns.guns;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

import eu.platinumcore.plugin.guns.config.PluginConfig;
import eu.platinumcore.plugin.guns.extra.Items;
import eu.platinumcore.plugin.guns.extra.Keys;
import eu.platinumcore.plugin.guns.guns.handler.GunsHandler;
import eu.platinumcore.plugin.guns.guns.handler.UUIDHandler;

public class Guns {

	private static List<String> gunList = new ArrayList<String>(); // All gun names from the config
	private static Map<String, Gun> guns = new HashMap<String, Gun>(); // Saved in file before disable
	private static Map<ItemStack, Gun> fast = new HashMap<ItemStack, Gun>(); // Mostly used for easy check and get

	public Guns() {
		setGunList(PluginConfig.getFolderFileNames().get(Keys.guns)); // Load everything
	}

	public void registerNewGun(ItemStack item) {
		String UUID = addUUID(item);
		Gun gun = new Gun(item);
		getGuns().put(UUID, gun);
		fast.put(item, gun);
	}

	public void registerUUIDGun(ItemStack item) {
		String UUID = getGunUUID(item);
		Gun gun = new Gun(item);
		getGuns().put(UUID, gun);
		fast.put(item, gun);
	}

	public void registerItemGun(ItemStack item) {
		String UUID = getGunUUID(item);
		Gun gun = getGuns().get(UUID);
		fast.put(item, gun);
	}

	public String addUUID(ItemStack item) {
		List<String> lore = Items.getItemLore(item);
		String UUID = UUIDHandler.generateUUID().toString();
		lore.add(0, UUID);
		Items.setItemLore(item, lore);
		return UUID;
	}

	public void registerGun(ItemStack item) {
		if (hasGunUUID(item)) {
			if (isGunUUIDRegistered(item)) {
				registerItemGun(item);
			} else {
				registerUUIDGun(item);
			}
		} else {
			registerNewGun(item);
		}
	}

	public void fireGun(Entity entity, ItemStack item) {
		if (!isGunRegistered(item)) {
			reloadGun(entity, item);
		}
		GunsHandler.fireGun(fast.get(item), entity);

	}

	public void reloadGun(Entity entity, ItemStack item) {
		if (!isGunRegistered(item)) {
			registerGun(item);
		}
		GunsHandler.reloadGun(fast.get(item), entity);
	}

	public void zoomGun(Entity entity, ItemStack item) {
		if (isGunRegistered(item)) {
			GunsHandler.zoomGun(fast.get(item), entity);
		}
	}

	public void swapGun(Entity entity, ItemStack item) {
		if (isGunRegistered(item)) {
			GunsHandler.swapGun(fast.get(item), entity);
		}
	}

	public void customizeGun(Entity entity, ItemStack item) {
		if (isGunRegistered(item)) {
			GunsHandler.customizeGun(fast.get(item), entity);
		}
	}

	public boolean hasGunUUID(ItemStack item) {
		return getGunUUID(item) != null;
	}

	public boolean isGunUUIDRegistered(ItemStack item) {
		return getGuns().containsKey(getGunUUID(item));
	}

	public boolean isGunRegistered(ItemStack item) {
		return fast.containsKey(item);
	}

	public boolean isGunValid(ItemStack item) {
		return getGunList().contains(Items.getItemName(item));
	}

	public String getGunUUID(ItemStack item) {
		String UUID = null;
		for (String line : Items.getItemLore(item))
			if (line.matches("[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}")) {
				UUID = line;
			}
		return UUID;
	}

	public void saveToFile() {

	}

	public static Map<String, Gun> getGuns() {
		return guns;
	}

	public static void setGuns(Map<String, Gun> guns) {
		Guns.guns = guns;
	}

	public static List<String> getGunList() {
		return gunList;
	}

	public static void setGunList(List<String> gunList) {
		Guns.gunList = gunList;
	}

}
