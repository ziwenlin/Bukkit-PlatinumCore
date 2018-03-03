package eu.platinumcore.plugin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class Commands implements CommandExecutor {

	private Plugin plugin = PlatinumCoreGuns.getPlugin(PlatinumCoreGuns.class);
	private Metadata md = new Metadata();

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
		Runnables.taskA(() -> commands());
		return valid;
	}

	private void commands() {
		switch (getCommand()) {
		case "platinumcore":
			commandPlatinumcore();
		case "guns":
			commandGuns();
		default:
			break;
		}

	}

	private void commandPlatinumcore() {
		if (getSender() instanceof Player) {
			player = (Player) getSender();
			if (getArgs().length > 0) {
				switch (getArgs()[0]) {
				case "reload":
					Bukkit.broadcastMessage("Reloading PlatinumCore configs!");
					valid = true;
					break;
				default:
					valid = false;
					break;
				}
			}
		}
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
