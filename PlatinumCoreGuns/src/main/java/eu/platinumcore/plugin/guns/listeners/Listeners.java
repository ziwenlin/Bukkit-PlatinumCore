package eu.platinumcore.plugin.guns.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.inventory.ItemStack;

import eu.platinumcore.plugin.guns.PlatinumCoreGuns;
import eu.platinumcore.plugin.guns.commands.Runnables;
import eu.platinumcore.plugin.guns.extra.Killcam;
import eu.platinumcore.plugin.guns.extra.NoHunger;
import eu.platinumcore.plugin.guns.guns.Guns;

public class Listeners implements Listener {

	private static Guns guns = PlatinumCoreGuns.getGuns();

	@EventHandler
	public void changeItem(PlayerItemHeldEvent event) {
		Player player = event.getPlayer();
		ItemStack item = player.getInventory().getItem(event.getNewSlot());
		if (guns.isGunValid(item)) {
			guns.swapGun(event.getPlayer(), item);
		}
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		switch (event.getAction()) {
		case LEFT_CLICK_AIR:
		case LEFT_CLICK_BLOCK:
			Player player = event.getPlayer();
			ItemStack item = player.getInventory().getItemInMainHand();
			if (guns.isGunValid(item)) {
				guns.zoomGun(player, item);
				event.setCancelled(true);
			}
		default:
			break;
		}
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		new Killcam(event.getEntity());
	}

	@EventHandler
	public void pluginEnable(PluginEnableEvent event) {
		Runnables.taskT(() -> NoHunger.noHunger(), 400, 400);
	}
}
