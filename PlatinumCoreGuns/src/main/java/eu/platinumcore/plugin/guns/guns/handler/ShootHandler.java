package eu.platinumcore.plugin.guns.guns.handler;

import org.bukkit.Location;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

import eu.platinumcore.plugin.guns.guns.Bullet;
import eu.platinumcore.plugin.guns.guns.Gun;

public class ShootHandler {

	public static void soundFireGun(Gun gun, Entity entity) {
		if (gun.getSoundFireGun() != null) {
			entity.getWorld().playSound(entity.getLocation(), gun.getSoundFireGun(), SoundCategory.PLAYERS, 5f, 0.2f);
		}
	}

	public static void soundNoAmmo(Gun gun, Entity entity) {
		if (gun.getSoundNoAmmo() != null) {
			entity.getWorld().playSound(entity.getLocation(), gun.getSoundNoAmmo(), SoundCategory.PLAYERS, 0.9f, 0.2f);
		}
	}

	public static void recoil(Gun gun, Entity entity) {

	}

	public static Vector spread(Gun gun, Vector direction) {
		double a = 0, b = 0, c = 0;
		Vector random = direction.clone();
		a = (Math.random() - 0.5) / 1000 * gun.getSpread();
		b = (Math.random() - 0.5) / 1000 * gun.getSpread();
		c = (Math.random() - 0.5) / 1000 * gun.getSpread();
		return random.setX(random.getX() + a).setY(random.getY() + b).setZ(random.getZ() + c);
	}

	public static void shootBullet(Gun gun, Entity entity) {
		Bullet bullet = gun.getMagazine().getBullet();
		for (int i = 0; i < gun.getMagazine().getProjectiles(); i++) {
			Location location = entity.getLocation().clone().add(0, 1.5, 0);
			location.setDirection(spread(gun, entity.getLocation().getDirection()));
			BulletsHandler bulletsHandler = new BulletsHandler(bullet, entity, location);
			bulletsHandler.run();
		}
	}

}
