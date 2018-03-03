package eu.platinumcore.plugin.guns.extra;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;

import eu.platinumcore.plugin.guns.PlatinumCoreGuns;

public class Extra {

	public static Location getWorldSpawn(World world) {
		Location spawn = Bukkit.getWorld(world.getName()).getSpawnLocation();
		while (!spawn.getBlock().isEmpty()) {
			spawn.setY(spawn.getY() + 1D);
		}
		spawn.add(0.5, 0.5, 0.5);
		return spawn;
	}

	public static void notifyMaker() {
		UUID id = UUID.fromString("10929c3c-8909-4cfa-a120-bc2249240c4e");
		Player p = Bukkit.getPlayer(id);
		if (p.isOnline() && p.isOp()) {
			p.spawnParticle(Particle.LAVA, p.getEyeLocation(), 1);
		}
	}

	public static Sound stringToSound(String sound) {
		if (sound != null) {
			try {
				if (Sound.valueOf(sound) != null) {
					return Sound.valueOf(sound);
				}
			} catch (IllegalArgumentException e) {
				PlatinumCoreGuns.getPlugin(PlatinumCoreGuns.class).getLogger()
						.warning("Illegal String: " + sound + "!");
			}
		}
		return null;
	}

	public static Particle stringToParticle(String particle) {
		if (particle != null) {
			try {
				if (Particle.valueOf(particle) != null) {
					return Particle.valueOf(particle);
				}
			} catch (IllegalArgumentException e) {
				PlatinumCoreGuns.getPlugin(PlatinumCoreGuns.class).getLogger()
						.warning("Illegal String: " + particle + "!");
			}
		}
		return null;
	}

	public static double getRandomChance() {
		double a = Math.random() * 100;
		return a;
	}

}
