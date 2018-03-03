package eu.platinumcore.plugin;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Items {
	 
	public static List<String> getItemLore(ItemStack item) {
		if (item.hasItemMeta() && item.getItemMeta().hasLore()) {
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
