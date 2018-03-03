package eu.platinumcore.plugin;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;

import eu.platinumcore.plugin.extra.Extra;
import eu.platinumcore.plugin.guns.handler.Guns;
import eu.platinumcore.plugin.killcam.Killcam;

public class Listeners implements Listener {

	private Plugin plugin = PlatinumCoreGuns.getPlugin(PlatinumCoreGuns.class);
	private Metadata md = new Metadata();
	private Player p;

	@EventHandler
	public void changeItem(PlayerItemHeldEvent event) {
		Guns gun = new Guns(event);
		if (gun.isValid()) {
			gun.setCooldown(10);
		}
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		Extra.onDeath(event);
	}
	
	@EventHandler
	public void onDamage(EntityDamageEvent event) {
		if (event.getEntity() instanceof Player) {
			p = (Player) event.getEntity();
			if (p.getHealth() - event.getDamage() <= 0D) {
			}
		}
		if (event.isCancelled()) {
			new Killcam(p);
		}
	}

	@EventHandler
	public void sneaking(PlayerToggleSneakEvent event) {
		p = event.getPlayer();
		md.setMD(p, "Sneaking", !p.isSneaking());
	}

	@EventHandler
	public void droppingItem(PlayerDropItemEvent event) {
		p = event.getPlayer();
		if (!(p.getOpenInventory() instanceof PlayerInventory)) {
			if (md.compare(p, "isInventoryOpen", false)) {
				Guns gun = new Guns(event);
				if (gun.isValid()) {
					gun.getMagazine().reload(event);
				}
			}
		}
	}

	@EventHandler
	public void inventoryOpen(InventoryOpenEvent event) {
		p = (Player) event.getPlayer();
		md.setMD(p, "isInventoryOpen", true);
	}

	@EventHandler
	public void inventoryClose(InventoryCloseEvent event) {
		p = (Player) event.getPlayer();
		md.setMD(p, "isInventoryOpen", false);
	}

	@EventHandler
	public void playerClick(PlayerInteractEvent event) {
		Guns gun = new Guns(event);
		if (md.compare(event.getPlayer(), "isInventoryOpen", false) && gun.isValid()) {
			Runnables.task(() -> gun.start());
		}
	}

	@EventHandler
	public void playerJoin(PlayerJoinEvent event) {
		p = event.getPlayer();
		md.setMD(p, "isInventoryOpen", false);
		md.setMD(p, "Sneaking", false);
	}

	@EventHandler
	public void playerLeave(PlayerQuitEvent event) {
		p = event.getPlayer();
		md.removeMD(p, "isInventoryOpen");
		md.removeMD(p, "Sneaking");
	}

	@EventHandler
	public void pluginEnable(PluginEnableEvent event) {
		Runnables.taskT(() -> Extra.noHunger(), 400, 400);
	}
}
