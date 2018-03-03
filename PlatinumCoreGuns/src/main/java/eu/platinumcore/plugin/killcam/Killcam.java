package eu.platinumcore.plugin.killcam;

import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import eu.platinumcore.plugin.Runnables;

public class Killcam {

	GameMode mode;
	
	public Killcam(Player player, Entity killer) {
		if (killer.isValid()) {	
			mode = player.getGameMode();
			player.setGameMode(GameMode.SPECTATOR);
			player.setSpectatorTarget(killer);
			Runnables.taskL(() -> respawn(player), 40);
		}
	}

	public Killcam(Player player) {
		mode = player.getGameMode();
		player.setGameMode(GameMode.SPECTATOR);
		Runnables.taskL(() -> respawn(player), 40);
	}

	private void respawn(Player player) {
		player.setGameMode(mode);
		player.setLastDamageCause(new EntityDamageEvent(player, DamageCause.CUSTOM, null, null));
	}

}
