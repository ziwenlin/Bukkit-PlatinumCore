package eu.platinumcore.plugin.guns.guns.handler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import eu.platinumcore.plugin.guns.PlatinumCoreGuns;
import eu.platinumcore.plugin.guns.commands.Runnables;
import eu.platinumcore.plugin.guns.extra.Extra;
import eu.platinumcore.plugin.guns.guns.Bullet;

public class BulletsHandler {

	private static final Plugin plugin = PlatinumCoreGuns.getPlugin();

	private double precision = plugin.getConfig().getInt("Guns.Precision", 1);

	private List<Entity> nearby = new ArrayList<Entity>();

	private Bullet bullet;
	private Entity entity;
	private Location location;

	private boolean wait = true, running = true, hit = false, error = false;
	private int traveled = 0, timer = 0;

	public BulletsHandler(Bullet bullet, Entity entity, Location location) {
		this.bullet = bullet;
		this.entity = entity;
		this.location = location;
		location.setDirection(location.getDirection().multiply(1d / precision));
	}

	public void run() {
		Runnables.taskT(() -> check(), 0, 0);
		Runnables.taskTA(() -> task(), 0, 0);
	}

	private boolean task() {
		if (!wait) {
			for (int i = 0; i < (bullet.getSpeed() * precision) && !hit && !error; timer++, i++) {
				hitBlock();
				hitEntity();
				trail();
				moveBullet();
			}
			wait = true;
		}
		traveled = (int) (timer / precision);
		if (traveled >= bullet.getRange() || hit || error || !running) {
			running = false;
			return false;
		}
		return true;
	}

	private boolean check() {
		if (wait) {
			nearby.clear();
			Location l = location.clone();
			Vector d = location.getDirection().clone();
			for (int i = 0; i < (bullet.getSpeed() * precision); i++) {
				if (!l.getWorld().isChunkLoaded(l.getChunk().getX(), l.getChunk().getZ())) {
					l.getWorld().loadChunk(l.getChunk().getX(), l.getChunk().getZ());
				}
				l.add(d.setY(d.getY() - (bullet.getGravity() * 0.005d / precision / bullet.getSpeed())));
			}
			try {
				double a = 9 * bullet.getSpeed();
				nearby.addAll(l.getWorld().getNearbyEntities(l, a, a, a));
			} catch (Exception e) {
				e.printStackTrace();
			}
			wait = false;
		}
		if (!running) {
			explosion();
			hitSound();
			return false;
		}
		return true;
	}

	public void damageEntity(Entity entityHit, Entity attacker, double damage) {
		Vector move = entityHit.getVelocity();
		((Damageable) entityHit).damage(damage, attacker);
		((LivingEntity) entityHit).setNoDamageTicks(0);
		entityHit.setVelocity(move);
	}

	private void explosion() {
		if ((bullet.getExplosionRange() > 0 || bullet.getExplosionPower() > 0) && bullet.getDamage() != 0) {
			double range = bullet.getDamage() / bullet.getExplosionRange() + bullet.getExplosionRange();
			double a = range * 0.25;
			double b = range * 1.5;
			int c = (int) Math.pow((range / 10), 2) * 10;
			if (bullet.getParticleHit() != null) {
				location.getWorld().spawnParticle(bullet.getParticleHit(), location, c, a, a, a, 1);
			}
			Collection<Entity> near = location.getWorld().getNearbyEntities(location, b, b, b);
			for (Entity ent : near) {
				Location pos = ent.getLocation();
				double distance = pos.distance(location);
				if (distance < (range) & ent.getType().isAlive()) {
					double percentage = 1 - (distance / (range));
					double processDamage = bullet.getDamage() * percentage * percentage;
					double dmg = processDamage;
					Runnables.task(() -> damageEntity(ent, entity, dmg));
				}
			}
		}
	}

	private void hitBlock() {
		try {
			hit = (location.getBlock().getType().isSolid());
		} catch (Exception e) {
			error = true;
		}
	}

	private void hitEntity() {
		for (Entity ent : nearby) {
			if (ent.getType().isAlive() && !ent.isDead() && ent != entity) {
				Location pos = ent.getLocation();
				double ex = pos.getX();
				double ey = pos.getY();
				double ez = pos.getZ();
				double px = location.getX();
				double py = location.getY();
				double pz = location.getZ();
				double ewitdh = 0.8 * ent.getWidth();
				double eheigth = 1.2 * ent.getHeight();
				if (between(px, ex - ewitdh, ex + ewitdh) && between(pz, ez - ewitdh, ez + ewitdh)
						&& between(py, ey, ey + eheigth)) {
					double dmg = bullet.getDamage();
					Runnables.task(() -> damageEntity(ent, entity, dmg));
					hit = true;
					break;
				}
			}
		}
	}

	private void hitSound() {
		if (bullet.getSoundHit() != null) {
			location.getWorld().playSound(location, bullet.getSoundHit(), SoundCategory.PLAYERS,
					(10f * (float) bullet.getDamage() / 20f), 0.4F);
		}
	}

	private void trail() {
		if (Extra.getRandomChance() < bullet.getTrailVisibility()) {
			if (bullet.getParticleTrail() != null) {
				location.getWorld().spawnParticle(bullet.getParticleTrail(), location, 1, 0, 0, 0, 0);
			}
			if (bullet.getSoundTrail() != null) {
				location.getWorld().playSound(location, bullet.getSoundTrail(), SoundCategory.WEATHER, 0.3f, 2f);
			}
		}
	}

	private boolean between(double a, double b, double c) {
		if (a >= b && a <= c) {
			return true;
		} else {
			return false;
		}
	}

	private void moveBullet() {
		Vector direction = location.getDirection();
		direction.setY(
				direction.getY() - (bullet.getGravity() * 0.005d * ((double) timer / precision / bullet.getSpeed())));
		location.add(direction);
	}
}