package eu.platinumcore.plugin.guns.extra;

import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class NoHunger {

	public static boolean noHunger() {
		Collection<? extends Player> list = Bukkit.getOnlinePlayers();
		for (Player player : list) {
			player.setSaturation(80F);
		}
		return true;
	}

}
