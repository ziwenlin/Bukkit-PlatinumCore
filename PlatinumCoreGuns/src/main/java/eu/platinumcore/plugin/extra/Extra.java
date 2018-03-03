package eu.platinumcore.plugin.extra;

import java.util.Collection;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;

import eu.platinumcore.plugin.Runnables;

public class Extra {

	public static boolean noHunger() {
		Collection<? extends Player> list = Bukkit.getOnlinePlayers();
		for (Player player : list) {
			player.setSaturation(80F);
		}
		return true;
	}

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

	public static void onDeath(PlayerDeathEvent event) {
		Player player = event.getEntity();
		player.setHealth(20D);
		GameMode mode = player.getGameMode();
		player.setGameMode(GameMode.SPECTATOR);
		if (player.getKiller() instanceof Player) {
			Runnables.taskL(() -> {
				player.sendMessage("You are spectating " + player.getKiller().getName());
				player.setSpectatorTarget(player.getKiller());
			}, 20);
		}
		Runnables.taskL(() -> {
			player.setGameMode(mode);
			player.teleport(Extra.getWorldSpawn(player.getWorld()));
		}, 60);
	}

	public static double getRandomChance() {
		double a = Math.random() * 100;
		return a;
	}
	
}
