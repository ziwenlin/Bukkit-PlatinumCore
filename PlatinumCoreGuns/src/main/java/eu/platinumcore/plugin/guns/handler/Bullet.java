package eu.platinumcore.plugin.guns.handler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.World;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import eu.platinumcore.plugin.PlatinumCoreGuns;
import eu.platinumcore.plugin.Runnables;
import eu.platinumcore.plugin.extra.Extra;
import eu.platinumcore.plugin.guns.config.Config;

public class Bullet {

	private Plugin plugin = PlatinumCoreGuns.getPlugin(PlatinumCoreGuns.class);
	private Config config = PlatinumCoreGuns.getConfigC();

	private boolean hit = false;
	private boolean error = false;
	private boolean wait = true;
	private boolean running = true;
	private int timer = 0;
	private int traveled = 0;

	private String gun;
	private int speed;
	private int range;
	private double damage;
	private double gravity;
	private double explosionPower;
	private double explosionRange;
	private double particleSpeed;
	private double trailVisibility;
	private Particle particleHit;
	private Particle particleTrail;
	private Sound soundHit;
	private Sound soundTravel;
	private Entity entity;
	private Location location;
	private Vector dir;
	private List<Collection<Entity>> nearby = new ArrayList<Collection<Entity>>();
	private World world;

	public Bullet(Entity shooter, String gunName) {
		setEntity(shooter);
		setGun(gunName);
		setWorld(shooter.getWorld());
		if (getEntity().getType().isAlive()) {
			setLocation(((LivingEntity) entity).getEyeLocation().clone());
		} else {
			setLocation(entity.getLocation().clone());
		}
		readConfig();
	}

	private void readConfig() {
		setSpeed(config.guns.getInt(gun + ".Bullet.Speed"));
		setRange(config.guns.getInt(gun + ".Bullet.Range"));
		setDamage(config.guns.getDouble(gun + ".Bullet.Damage"));
		setGravity(config.guns.getDouble(gun + ".Bullet.Gravity"));
		setTrailVisibility(config.guns.getDouble(gun + ".Bullet.TrailVisibility"));
		setParticleSpeed(config.guns.getDouble(gun + ".Bullet.ParticleSpeed"));
		setExplosionRange(config.guns.getDouble(gun + ".Bullet.ExplosionRange"));
		setExplosionPower(config.guns.getDouble(gun + ".Bullet.ExplosionPower"));
		setParticleTrail(config.stringToParticleGuns(gun + ".Particle.Trail"));
		setParticleHit(config.stringToParticleGuns(gun + ".Particle.Hit"));
		setSoundHit(config.stringToSoundGuns(gun + ".Sound.Hit"));
		setSoundTravel(config.stringToSoundGuns(gun + ".Sound.Travel"));
	}

	public void run() {
		Runnables.taskT(() -> check(), 0, 0);
		Runnables.taskTA(() -> task(), 0, 0);
	}

	private boolean task() {
		if (!wait) {
			for (int i = 0; i < getSpeed() && !hit && !error; timer++, i++) {
				hitBlock();
				hitEntity();
				trail();
				moveBullet();
			}
			wait = true;
		}
		traveled = timer / 2;
		if (traveled >= getRange() || hit || error || !running) {
			running = false;
			hitSound();
			return false;
		}
		return true;
	}

	private boolean check() {
		if (wait) {
			Location l = getLocation().clone();
			Vector d = getDirection().clone();
			for (int i = 0; i < getSpeed(); i++) {
				if (!getWorld().isChunkLoaded(l.getChunk().getX(), l.getChunk().getZ())) {
					getWorld().loadChunk(l.getChunk().getX(), l.getChunk().getZ());
				}
				try {
					nearby.add(getWorld().getNearbyEntities(l, 9, 9, 9));
				} catch (Exception e) {
					e.printStackTrace();
				}
				l.add(d.setY(d.getY() - (getGravity() * 5 / 2000 / getSpeed())));
			}
			wait = false;
		}
		if (!running) {
			explosion();
			return false;
		}
		return true;
	}

	private void damageEntity(Entity entityHit, Entity attacker, double damage) {
		Vector move = entityHit.getVelocity();
		((Damageable) entityHit).damage(damage, attacker);
		((LivingEntity) entityHit).setNoDamageTicks(0);
		entityHit.setVelocity(move);
	}

	private void explosion() {
		if ((getExplosionRange() > 0 || getExplosionPower() > 0) && getDamage() != 0) {
			double range = getDamage() / getExplosionRange() + getExplosionRange();
			double a = range * 0.25;
			double b = range * 1.5;
			int c = (int) Math.pow((range / 10), 2) * 10;
			if (particleHit != null) {
				getWorld().spawnParticle(getParticleHit(),
						getLocation().clone().subtract(getDirection()).subtract(getDirection()), c, a, a, a, 1);
			}
			Collection<Entity> near = getWorld().getNearbyEntities(getLocation(), b, b, b);
			for (Entity ent : near) {
				Location pos = ent.getLocation();
				double distance = pos.distance(getLocation());
				if (distance < (range) & ent.getType().isAlive()) {
					double percentage = 1 - (distance / (range));
					double processDamage = getDamage() * percentage * percentage;
					double dmg = processDamage;
					Runnables.task(() -> damageEntity(ent, entity, dmg));
				}
			}
		}
	}

	private void hitBlock() {
		try {
			hit = (getLocation().getBlock().getType().isSolid());
		} catch (Exception e) {
			error = true;
		}
	}

	void test(Runnable test) {
		test.run();
	}

	private void hitEntity() {
		Collection<Entity> near = new ArrayList<Entity>();
		if (nearby.size() > 0) {
			try {
				if (nearby.get(0).size() > 0) {
					near = nearby.get(0);
				}
				nearby.remove(0);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		for (Entity ent : near) {
			if (ent.getType().isAlive() && !ent.isDead() && ent != getEntity()) {
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
					double dmg = getDamage();
					Runnables.task(() -> damageEntity(ent, entity, dmg));
					hit = true;
					break;
				}
			}
		}
	}

	private void hitSound() {
		if (getSoundHit() != null) {
			getWorld().playSound(getLocation(), getSoundHit(), SoundCategory.PLAYERS, (10f * (float) getDamage() / 20f),
					0.4F);
		}
	}

	private void trail() {
		if (Extra.getRandomChance() < getTrailVisibility()) {
			double a = getParticleSpeed() / 1000;
			if (getParticleTrail() != null) {
				getWorld().spawnParticle(getParticleTrail(), getLocation(), 1, a, a, a, a);
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
		getLocation().add(getDirection().setY(getDirection().getY() - (getGravity() * 5 / 2000 / getSpeed())));
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Vector getDirection() {
		return dir;
	}

	public void setDirection(Vector dir) {
		this.dir = dir;
	}

	public String getGun() {
		return gun;
	}

	public void setGun(String gun) {
		this.gun = gun;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
	}

	public double getDamage() {
		return damage;
	}

	public void setDamage(double damage) {
		this.damage = damage;
	}

	public double getGravity() {
		return gravity;
	}

	public void setGravity(double gravity) {
		this.gravity = gravity;
	}

	public double getExplosionPower() {
		return explosionPower;
	}

	public void setExplosionPower(double explosionPower) {
		this.explosionPower = explosionPower;
	}

	public double getExplosionRange() {
		return explosionRange;
	}

	public void setExplosionRange(double explosionRange) {
		this.explosionRange = explosionRange;
	}

	public double getParticleSpeed() {
		return particleSpeed;
	}

	public void setParticleSpeed(double particleSpeed) {
		this.particleSpeed = particleSpeed;
	}

	public double getTrailVisibility() {
		return trailVisibility;
	}

	public void setTrailVisibility(double trailVisibility) {
		this.trailVisibility = trailVisibility;
	}

	public Particle getParticleTrail() {
		return particleTrail;
	}

	public void setParticleTrail(Particle particleTrail) {
		this.particleTrail = particleTrail;
	}

	public Sound getSoundHit() {
		return soundHit;
	}

	public void setSoundHit(Sound soundHit) {
		this.soundHit = soundHit;
	}

	public Sound getSoundTravel() {
		return soundTravel;
	}

	public void setSoundTravel(Sound soundTravel) {
		this.soundTravel = soundTravel;
	}

	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}

	public Particle getParticleHit() {
		return particleHit;
	}

	public void setParticleHit(Particle particleHit) {
		this.particleHit = particleHit;
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}

}