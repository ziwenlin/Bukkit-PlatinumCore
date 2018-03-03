package eu.platinumcore.plugin.guns.handler;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import eu.platinumcore.plugin.Metadata;
import eu.platinumcore.plugin.PlatinumCoreGuns;
import eu.platinumcore.plugin.guns.config.Config;

public class Shoot {

	private static Plugin plugin = PlatinumCoreGuns.getPlugin(PlatinumCoreGuns.class);
	private static Config config = PlatinumCoreGuns.getConfigC();
	private static Metadata md = new Metadata();
	private long time = System.currentTimeMillis();

	private String itemName;
	private Guns gun;
	private int ammoGun;
	private int recoilType;
	private int cooldown;
	private int bulletsPerShot;
	private double spread;

	public Sound soundFireGun;
	public Sound soundNoAmmo;

	public Event event;
	public Player player;
	public Location ploc;
	public Vector dir;

	public Shoot(PlayerEvent event, Guns gun) {
		this.event = event;
		this.gun = gun;
		itemName = gun.getItemName();
		player = event.getPlayer();
		ploc = player.getLocation();
		dir = ploc.getDirection();
		if (gun.getMagazine().hasAmmo()) {
			shoot();
		} else {
			soundNoAmmo();
		}
	}


	private void soundFireGun() {
		soundFireGun = config.stringToSoundGuns(itemName + ".Sound.Fire");
		if (soundFireGun != null) {
			player.playSound(ploc, soundFireGun, SoundCategory.PLAYERS, 5f, 0.2f);
		}
	}

	private void soundNoAmmo() {
		soundNoAmmo = config.stringToSoundGuns(itemName + ".Sound.OutOfAmmo");
		if (soundNoAmmo != null) {
			player.playSound(ploc, soundNoAmmo, SoundCategory.AMBIENT, 0.9f, 0.2f);
		}
	}

	private Vector spread() {
		double a = 0, b = 0, c = 0;
		Vector random = dir.clone();
		a = (getRandom() - 0.5) / 100 * spread;
		b = (getRandom() - 0.5) / 100 * spread;
		c = (getRandom() - 0.5) / 100 * spread;
		return random.setX(random.getX() + a).setY(random.getY() + b).setZ(random.getZ() + c);
	}

	private double getRandom() {
		double a = Math.random();
		return a;
	}

	private void shoot() {
		spread = config.guns.getDouble(itemName + ".Gun.Spread");
		cooldown = config.guns.getInt(itemName + ".Gun.Cooldown");
		recoilType = config.guns.getInt(itemName + ".Gun.RecoilType");
		bulletsPerShot = config.guns.getInt(itemName + ".Gun.Bullets");
		gun.setCooldown(cooldown);
		gun.getMagazine().removeAmmo(1);
		bullets();
		soundFireGun();
	}

	private void bullets() {
		for (int i = 0; i < bulletsPerShot; i++) {
			Bullet bullet = new Bullet(player, itemName);
			bullet.setDirection(spread().multiply(0.5));
			bullet.run();
		}
	}
	
	
	
	
}
