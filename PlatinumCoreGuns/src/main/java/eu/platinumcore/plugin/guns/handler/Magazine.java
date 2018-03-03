package eu.platinumcore.plugin.guns.handler;

import java.util.List;

import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

import eu.platinumcore.plugin.Items;
import eu.platinumcore.plugin.PlatinumCoreGuns;
import eu.platinumcore.plugin.guns.config.Config;

public class Magazine {

	static Config config = PlatinumCoreGuns.getConfigC();

	private String itemName;
	private ItemStack item;
	private Guns gun;

	public Magazine(Guns gun) {
		this.gun = gun;
		item = gun.getItem();
		itemName = gun.getItemName();
	}

	public void reload(PlayerDropItemEvent event) {
		event.setCancelled(true);
		String revAmmo = config.guns.getString(itemName + ".Gun.Ammo");
		ItemStack[] invP = event.getPlayer().getInventory().getContents();
		ItemStack ammo = getMagazine(invP, revAmmo);
		if (ammo != null) {
			int magazine = config.magazine.getInt(revAmmo);
			ammo.setAmount(ammo.getAmount() - 1);
			setAmmo(magazine);
			gun.setCooldown(34);
		}

	}

	public int getAmmoInMag(ItemStack ammo) {
		if (ammo.getItemMeta().hasLore()) {
			List<String> loreAmmo = ammo.getItemMeta().getLore();
			for (String l : loreAmmo) {
				if (l.contains("Ammo: ")) {
					return Integer.parseInt(l.replace("Ammo: ", ""));
				}
			}
		}
		return config.magazine.getInt(ammo.getItemMeta().getDisplayName());
	}

	public ItemStack getMagazine(ItemStack[] inventory, String revAmmo) {
		for (ItemStack item : inventory) {
			if (item != null) {
				String str = Items.getItemName(item);
				if (str != null) {
					if (revAmmo.equals(str)) {				
						return item;
					}
				}
			}
		}
		gun.getPlayer().sendMessage("No ammo"); // Configuable later
		return null;
	}

	public void removeAmmo(int amount) {
		List<String> lore = Items.getItemLore(item);
		for (String str : lore) {
			if (str.contains("Ammo: ")) {
				int ammo = Integer.parseInt(str.replace("Ammo: ", ""));
				int index = lore.indexOf(str);
				lore.set(index, ("Ammo: " + (ammo - amount)));
				Items.setItemLore(item, lore);
				return;
			}
		}
	}

	public void setAmmo(int amount) {
		List<String> lore = Items.getItemLore(item);
		for (String str : lore) {
			if (str.contains("Ammo: ")) {
				int index = lore.indexOf(str);
				lore.set(index, ("Ammo: " + amount));
			}
		}
		if (lore.isEmpty()) {
			lore.add("Ammo: " + amount);
		}
		Items.setItemLore(item, lore);
	}

	public int getAmmo() {
		List<String> lore = Items.getItemLore(item);
		for (String str : lore) {
			if (str.contains("Ammo: ")) {
				return Integer.parseInt(str.replace("Ammo: ", ""));
			}
		}
		return 0;
	}

	public boolean hasAmmo() { // Checks ammo in gun
		return (getAmmo() > 0);
	}

}
