package eu.platinumcore.plugin.guns.guns.handler;

import java.util.Arrays;
import java.util.List;

import org.bukkit.GameMode;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import eu.platinumcore.plugin.guns.PlatinumCoreGuns;
import eu.platinumcore.plugin.guns.extra.Items;
import eu.platinumcore.plugin.guns.guns.Gun;
import eu.platinumcore.plugin.guns.guns.Magazine;

public class MagazinesHandler {

	private final static FileConfiguration config = PlatinumCoreGuns.getPlugin().getConfig();

	public static void reload(Gun gun, Player player) {
		ItemStack magazine = getPlayerInventoryMagazine(player, gun);
		if (magazine != null) {
			reuseMagazine(gun, player);
			gun.getMagazine().reload(magazine.clone());
			if (player.getGameMode() != GameMode.CREATIVE) {
				Items.increaseItemAmount(magazine, -1);
			}
			int amount = getGunMagazineAmmo(gun);
			gun.getMagazine().setAmount(amount);
			String bullet = getGunMagazineBullets(gun);
			gun.getMagazine().getBullet().reload(bullet);
		}

	}

	public static void reuseMagazine(Gun gun, Player player) {
		if (gun.getMagazine().isReusable() && player.getGameMode() != GameMode.CREATIVE) {
			setGunMagazineBullets(gun, gun.getMagazine().getBullet().getName());
			setGunMagazineAmmo(gun, gun.getMagazine().getAmount());
			setPlayerInventoryMagazine(gun, player);
		}
	}

	public static void setPlayerInventoryMagazine(Gun gun, Player player) {
		List<ItemStack> inventory = Arrays.asList(player.getInventory().getStorageContents());
		for (int i = 35; i >= 0; i--) {
			ItemStack slot = inventory.get(i);
			if (slot == null) {
				player.getInventory().setItem(i, gun.getMagazine().getItem());
				return;
			}
			if (gun.getMagazine().getItem().isSimilar(slot)) {
				Items.increaseItemAmount(player.getInventory().getItem(i), 1);
				return;
			}
		}
	}

	public static ItemStack getPlayerInventoryMagazine(Player player, Gun gun) {
		List<String> compatibleMagazine = gun.getCompatibleMagazine();
		ItemStack[] inventory = player.getInventory().getContents();
		for (ItemStack item : inventory) {
			String itemName = Items.getItemName(item);
			if (itemName != null && compatibleMagazine.contains(itemName) && getMagazineAmmo(item) != 0) {
				return item;
			}
		}
		player.sendMessage(config.getString("Guns.No Ammo Message", "No ammo/magazines!")); // Configuable in config
		return null;
	}

	public static void decreaseGunAmmo(Gun gun, Entity player) {
		if (player instanceof Player && ((HumanEntity) player).getGameMode() != GameMode.CREATIVE) {
			Magazine mag = gun.getMagazine();
			mag.setAmount(mag.getAmount() - 1);
		}
	}

	public static boolean hasGunAmmo(Gun gun) {
		return (gun.getMagazine().getAmount() > 0);
	}

	public static String getGunMagazineBullets(Gun gun) {
		ItemStack item = gun.getMagazine().getItem();
		List<String> lore = Items.getItemLore(item);
		for (String str : lore) {
			if (str.contains("Bullets: ")) {
				return str.replace("Bullets: ", "");
			}
		}
		return gun.getMagazine().getDefaultBullet();
	}

	public static void setGunMagazineBullets(Gun gun, String bulletName) {
		Magazine magazine = gun.getMagazine();
		ItemStack item = magazine.getItem();
		List<String> lore = Items.getItemLore(item);
		for (String str : lore) {
			if (str.contains("Bullets: ")) {
				lore.remove(lore.indexOf(str));
				break;
			}
		}
		lore.add("Bullets: " + bulletName);
		Items.setItemLore(item, lore);
	}

	public static int getGunMagazineAmmo(Gun gun) {
		Magazine mag = gun.getMagazine();
		ItemStack item = mag.getItem();
		List<String> lore = Items.getItemLore(item);
		for (String str : lore) {
			if (str.contains("Ammo: ")) {
				return Integer.parseInt(str.replace("Ammo: ", ""));
			}
		}
		return mag.getSize();
	}

	public static void setGunMagazineAmmo(Gun gun, int amount) {
		Magazine magazine = gun.getMagazine();
		ItemStack item = magazine.getItem();
		List<String> lore = Items.getItemLore(item);
		for (String str : lore) {
			if (str.contains("Ammo: ")) {
				lore.remove(lore.indexOf(str));
				break;
			}
		}
		lore.add("Ammo: " + amount);
		Items.setItemLore(item, lore);
	}

	// public static void setGunMagazineLore(Gun gun, int amount, String bulletName)
	// {
	// Magazine magazine = gun.getMagazine();
	// ItemStack item = magazine.getItem();
	// List<String> lore = Items.getItemLore(item);
	// for (String str : lore) {
	// if (str.contains("Bullets: ")) {
	// lore.set(lore.indexOf(str), ("Bullets: " + bulletName));
	// }
	// if (str.contains("Ammo: ")) {
	// lore.set(lore.indexOf(str), ("Ammo: " + amount));
	// }
	// }
	// if (lore.isEmpty()) {
	// lore.add("Bullets: " + magazine.getBullet().getName());
	// lore.add("Ammo: " + bulletName);
	// }
	// Items.setItemLore(item, lore);
	// }

	public static String getMagazineBullets(ItemStack item) {
		List<String> lore = Items.getItemLore(item);
		for (String str : lore) {
			if (str.contains("Bullets: ")) {
				return str.replace("Bullets: ", "");
			}
		}
		return null;
	}

	public static void setMagazineBullets(ItemStack item, String bulletName) {
		List<String> lore = Items.getItemLore(item);
		for (String str : lore) {
			if (str.contains("Bullets: ")) {
				lore.remove(lore.indexOf(str));
				break;
			}
		}
		lore.add("Bullets: " + bulletName);
		Items.setItemLore(item, lore);
	}

	public static int getMagazineAmmo(ItemStack item) {
		List<String> lore = Items.getItemLore(item);
		for (String str : lore) {
			if (str.contains("Ammo: ")) {
				return Integer.parseInt(str.replace("Ammo: ", ""));
			}
		}
		return -1;
	}

	public static void setMagazineAmmo(ItemStack item, int amount) {
		List<String> lore = Items.getItemLore(item);
		for (String str : lore) {
			if (str.contains("Ammo: ")) {
				lore.remove(lore.indexOf(str));
				break;
			}
		}
		lore.add("Ammo: " + amount);
		Items.setItemLore(item, lore);
	}

}
