package eu.platinumcore.plugin.guns.extra;

import org.bukkit.GameMode;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import eu.platinumcore.plugin.guns.PlatinumCoreGuns;
import eu.platinumcore.plugin.guns.commands.Runnables;

public class Killcam {

	final static FileConfiguration config = PlatinumCoreGuns.getPlugin().getConfig();
	final static boolean enabled = config.getBoolean("Killcam.Enable", true);
	final static int killcamDelay = config.getInt("Killcam.Delay", 20);
	final static int respawnDelay = config.getInt("Killcam.Respawn Delay", 60);

	public Killcam(Player player) {
		player.setHealth(20D);
		GameMode mode = player.getGameMode();
		player.setGameMode(GameMode.SPECTATOR);
		if (enabled && player.getKiller() != null) {
			Runnables.taskL(() -> {
				player.sendMessage("You are spectating " + player.getKiller().getName());
				player.setSpectatorTarget(player.getKiller());
			}, killcamDelay);
			Runnables.taskL(() -> {
				player.setGameMode(mode);
				player.teleport(Extra.getWorldSpawn(player.getWorld()));
			}, respawnDelay + killcamDelay);
		} else {
			Runnables.taskL(() -> {
				player.setGameMode(mode);
				player.teleport(Extra.getWorldSpawn(player.getWorld()));
			}, respawnDelay);
		}
	}

}
