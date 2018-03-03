package eu.platinumcore.plugin.guns.handler;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import eu.platinumcore.plugin.Items;
import eu.platinumcore.plugin.Metadata;
import eu.platinumcore.plugin.PlatinumCoreGuns;
import eu.platinumcore.plugin.Runnables;
import eu.platinumcore.plugin.guns.config.Config;

public class Guns {

	private static Plugin plugin = PlatinumCoreGuns.getPlugin(PlatinumCoreGuns.class);
	private static Config config = PlatinumCoreGuns.getConfigC();
	private static Metadata md = new Metadata();
	private static Runnables r = new Runnables();
	private long time = System.currentTimeMillis();

	private Magazine mag;
	private String itemName;
	private ItemStack item;
	private Event event;
	private Player player;

	public Guns(Event event) {
		setEvent(event);
		setPlayer(((PlayerEvent) event).getPlayer());
		setItemEvent(getEvent());
		setItemName(Items.getItemName(item));
		setMagazine(new Magazine(this));
	}
	
	private void setItemEvent(Event event) {
		switch (event.getClass().getSimpleName()) {
		case "PlayerInteractEvent":
			setItem(getPlayer().getInventory().getItemInMainHand());
			return;
		case "PlayerItemHeldEvent":
			setItem(getPlayer().getInventory().getItem(((PlayerItemHeldEvent) event).getNewSlot()));
			return;
		case "PlayerDropItemEvent":
			setItem(((PlayerDropItemEvent) event).getItemDrop().getItemStack());
			return;
		default:
			setItem(new ItemStack(Material.AIR));
			return;
		}
	}
	
	public void start() {
		if (isRightClick()) {
			if (isReady()) {
				new Shoot((PlayerEvent) event, this);
			}
		} else if (isLeftClick()) {
			Runnables.task(() -> zoom());
		}
	}

	public void zoom() {
		if (getPlayer().hasPotionEffect(PotionEffectType.SLOW)) {
			getPlayer().setWalkSpeed(0.2F);
			getPlayer().removePotionEffect(PotionEffectType.SLOW);
		} else {
			getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 10000, 5, true, false), true);
			getPlayer().setWalkSpeed(1.0F);
		}
	}

	public boolean isReady() {
		String key = itemName + " Cooldown";
		if (md.hasMD(getPlayer(), key)) {
			long t = time;
			try { // Sometimes the player has a corrupted key
				t = (long) md.getMD(getPlayer(), key);
			} catch (Exception e) { // Key is being replaced
				md.setMD(getPlayer(), key, time + 100);
			}
			return (t < time);
		}
		return true; // If the player never used this gun
	}

	public void setCooldown(int cooldown) {
		String key = itemName + " Cooldown";
		long gunCooldown = time + 50 * cooldown; // Setting cooldown
		md.setMD(getPlayer(), key, gunCooldown); // Store the cooldown in the player
		getPlayer().setCooldown(item.getType(), cooldown); // Show the cooldown for the player
	}

	public boolean isValid() {
		if (itemName != null) {
			for (String str : PlatinumCoreGuns.getGunTypes()) {
				if (itemName.contains(str)) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean isRightClick() {
		Action act = ((PlayerInteractEvent) getEvent()).getAction();
		return (act == Action.RIGHT_CLICK_AIR || act == Action.RIGHT_CLICK_BLOCK);
	}

	public boolean isLeftClick() {
		Action act = ((PlayerInteractEvent) getEvent()).getAction();
		return (act == Action.LEFT_CLICK_AIR || act == Action.LEFT_CLICK_BLOCK);
	}

	private double getRandomNumber() {
		double random = Math.random() * 100;
		return random;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public void setItem(ItemStack item) {
		this.item = item;
	}

	public ItemStack getItem() {
		return item;
	}

	public String getItemName() {
		return itemName;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Magazine getMagazine() {
		return mag;
	}

	public void setMagazine(Magazine mag) {
		this.mag = mag;
	}

}
