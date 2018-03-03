package eu.platinumcore.plugin.guns.commands;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import eu.platinumcore.plugin.guns.PlatinumCoreGuns;

public class Commands implements TabExecutor {

	private Plugin plugin = PlatinumCoreGuns.getPlugin(PlatinumCoreGuns.class);
	private CommandSender sender;
	private Command cmd;
	private String label;
	private String[] args;
	private String command;

	private Player player;
	private boolean valid = false;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		setSender(sender);
		setCmd(cmd);
		setLabel(label);
		setArgs(args);
		setCommand(cmd.getName());
		commandGuns();
		return valid;
	}

	@Override
	public List<String> onTabComplete(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		// TODO Auto-generated method stub
		return null;
	}

	private void commandGuns() {
		if (sender instanceof Player) {
			player = (Player) sender;
			if (args.length > 0) {
				if (plugin.getServer().getPlayer(args[0]) != null) {
					player = plugin.getServer().getPlayer(args[0]);
					player.sendMessage("Respawning " + player.getDisplayName() + " over 3 seconds if dead!");
				} else {
					player.sendMessage("Can't respawn" + args[0] + "!");
				}
			} else {
				player.sendMessage("Respawning " + player.getDisplayName() + " over 3 seconds if dead!");
			}
			valid = true;
		}
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public CommandSender getSender() {
		return sender;
	}

	public void setSender(CommandSender sender) {
		this.sender = sender;
	}

	public Command getCmd() {
		return cmd;
	}

	public void setCmd(Command cmd) {
		this.cmd = cmd;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String[] getArgs() {
		return args;
	}

	public void setArgs(String[] args) {
		this.args = args;
	}

}
