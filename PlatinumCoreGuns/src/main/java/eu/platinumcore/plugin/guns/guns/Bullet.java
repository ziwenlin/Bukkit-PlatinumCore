package eu.platinumcore.plugin.guns.guns;

import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;

import eu.platinumcore.plugin.guns.PlatinumCoreGuns;
import eu.platinumcore.plugin.guns.config.PluginConfig;
import eu.platinumcore.plugin.guns.extra.Extra;
import eu.platinumcore.plugin.guns.extra.Keys;

public class Bullet {

	private FileConfiguration file;
	private String name;

	private int speed;
	private int range;
	private double damage;
	private double gravity;

	private Sound soundHit;
	private Sound soundTrail;

	private Particle particleTrail;
	private Particle particleHit;
	private double trailVisibility;

	private double explosionPower;
	private double explosionRange;

	public Bullet() {
		damage = 0;
		speed = 0;
		range = 0;
		gravity = 0;
	}

	public void reload(String name) {
		setName(name);
		if (name != null && PluginConfig.getFolderFileNames().get(Keys.bullets).contains(name)) {
			setFile(PluginConfig.getFolderFiles().get(Keys.bullets).get(getName()));
			speed = file.getInt(Keys.speed, 5);
			range = file.getInt(Keys.range, 1000);
			damage = file.getDouble(Keys.damage, 0);
			gravity = file.getDouble(Keys.gravity, 1);
			explosionPower = file.getDouble(Keys.explosionspower, 0);
			explosionRange = file.getDouble(Keys.explosionsrange, 0);
			setParticleTrail(Extra.stringToParticle(file.getString(Keys.particlestrail, null)));
			setParticleHit(Extra.stringToParticle(file.getString(Keys.particleshit, null)));
			trailVisibility = file.getDouble(Keys.particlestrailvisibility, 50);
			soundHit = Extra.stringToSound(file.getString(Keys.soundshit, null));
			soundTrail = Extra.stringToSound(file.getString(Keys.soundstrail, null));

			if (damage == 0) {
				file.set(Keys.speed, getSpeed());
				file.set(Keys.range, getRange());
				file.set(Keys.damage, damage);
				file.set(Keys.gravity, gravity);
				file.set(Keys.explosionspower, explosionPower);
				file.set(Keys.explosionsrange, explosionRange);
				file.set(Keys.particleshit, null);
				if (particleHit != null)
					file.set(Keys.particleshit, particleHit.toString());
				file.set(Keys.particlestrail, null);
				if (particleTrail != null)
					file.set(Keys.particlestrail, particleTrail.toString());
				file.set(Keys.particlestrailvisibility, null);
				file.set(Keys.particlestrailvisibility, trailVisibility);
				file.set(Keys.soundshit, null);
				if (soundHit != null)
					file.set(Keys.soundshit, soundHit.toString());
				file.set(Keys.soundstrail, null);
				if (soundTrail != null)
					file.set(Keys.soundstrail, soundTrail.toString());
			}
		} else {
			PlatinumCoreGuns.getPlugin().getLogger().warning("Default bullet " + name + " cannot be found!");
			damage = 0;
			speed = 0;
			range = 0;
			gravity = 0;
		}

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public double getTrailVisibility() {
		return trailVisibility;
	}

	public void setTrailVisibility(double trailVisibility) {
		this.trailVisibility = trailVisibility;
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

	public FileConfiguration getFile() {
		return file;
	}

	public void setFile(FileConfiguration file) {
		this.file = file;
	}

	public Sound getSoundTrail() {
		return soundTrail;
	}

	public void setSoundTrail(Sound soundTrail) {
		this.soundTrail = soundTrail;
	}

	public Sound getSoundHit() {
		return soundHit;
	}

	public void setSoundHit(Sound soundHit) {
		this.soundHit = soundHit;
	}

	public Particle getParticleTrail() {
		return particleTrail;
	}

	public void setParticleTrail(Particle particleTrail) {
		this.particleTrail = particleTrail;
	}

	public Particle getParticleHit() {
		return particleHit;
	}

	public void setParticleHit(Particle particleHit) {
		this.particleHit = particleHit;
	}

}
