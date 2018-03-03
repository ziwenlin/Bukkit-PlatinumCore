package eu.platinumcore.plugin.guns.commands;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import eu.platinumcore.plugin.guns.PlatinumCoreGuns;

public class PlatinumCoreCommands implements TabExecutor {

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		return null;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length > 0) {
			switch (args[0]) {
			case "reload":
				PlatinumCoreGuns.getPlugin().getLogger().info("Reloading PlatinumCoreGun configs");
				break;
			default:
				break;
			}
			return true;
		}
		return false;
	}

}
