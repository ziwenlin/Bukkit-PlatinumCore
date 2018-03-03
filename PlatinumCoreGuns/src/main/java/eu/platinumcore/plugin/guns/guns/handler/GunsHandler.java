package eu.platinumcore.plugin.guns.guns.handler;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import eu.platinumcore.plugin.guns.guns.Gun;

public class GunsHandler {

	public static void fireGun(Gun gun, Entity entity) {
		long time = System.currentTimeMillis();
		if (gun.getLastTimeUsed() >= time) {
			return;
		}
		if (MagazinesHandler.hasGunAmmo(gun)) {
			ShootHandler.soundFireGun(gun, entity);
			ShootHandler.shootBullet(gun, entity); // Fire gun
			MagazinesHandler.decreaseGunAmmo(gun, entity);// Removes 1 bullet from the magazine
			gun.setLastTimeUsed(time + 50 * gun.getCooldown()); // Cooldown
			if (entity instanceof Player) {
				((Player) entity).setCooldown(gun.getItem().getType(), gun.getCooldown());
			}
		} else {
			ShootHandler.soundNoAmmo(gun, entity);
		}

	}

	public static void zoomGun(Gun gun, Entity entity) {
		if (entity instanceof Player) {
			Player player = (Player) entity;
			if (player.hasPotionEffect(PotionEffectType.SLOW)) {
				player.setWalkSpeed(0.2F);
				player.removePotionEffect(PotionEffectType.SLOW);
			} else {
				player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 1000000, 5, true, false), true);
				player.setWalkSpeed(1.0F);
			}
		}
	}

	public static void reloadGun(Gun gun, Entity entity) {
		if (entity instanceof Player) {
			Player player = (Player) entity;
			MagazinesHandler.reload(gun, player);
			player.setCooldown(gun.getItem().getType(), gun.getMagazine().getReloadTime());
			gun.updateLastTimeUsed(gun.getMagazine().getReloadTime() * 50);
		}
	}

	public static void customizeGun(Gun gun, Entity entity) {

	}

	public static void swapGun(Gun gun, Entity entity) {
		gun.updateLastTimeUsed(gun.getMagazine().getReloadTime() * 50);
		if (entity instanceof Player) {
			((Player) entity).setCooldown(gun.getItem().getType(), gun.getMagazine().getReloadTime());
		}
	}
}
