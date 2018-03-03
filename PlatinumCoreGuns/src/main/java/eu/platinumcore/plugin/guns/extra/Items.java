package eu.platinumcore.plugin.guns.extra;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Items {

	public static void increaseItemAmount(ItemStack item, int amount) {
		item.setAmount(item.getAmount() + amount);
	}

	public static List<String> getItemLore(ItemStack item) {
		if (item != null && item.hasItemMeta() && item.getItemMeta().hasLore()) {
			return item.getItemMeta().getLore();
		}
		return new ArrayList<String>();
	}

	public static String getItemName(ItemStack item) {
		if (item != null && item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {
			return item.getItemMeta().getDisplayName();
		}
		return null;
	}

	public static void setItemName(ItemStack item, String name) {
		try {
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(name);
			item.setItemMeta(meta);
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

	public static void setItemLore(ItemStack item, List<String> lore) {
		try {
			ItemMeta meta = item.getItemMeta();
			meta.setLore(lore);
			item.setItemMeta(meta);
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}
}
