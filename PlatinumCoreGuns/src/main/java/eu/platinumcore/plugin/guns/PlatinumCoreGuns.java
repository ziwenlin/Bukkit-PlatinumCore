package eu.platinumcore.plugin.guns;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import eu.platinumcore.plugin.guns.commands.PlatinumCoreCommands;
import eu.platinumcore.plugin.guns.config.PluginConfig;
import eu.platinumcore.plugin.guns.guns.Guns;
import eu.platinumcore.plugin.guns.listeners.Listeners;
import eu.platinumcore.plugin.guns.listeners.PacketListeners;

public class PlatinumCoreGuns extends JavaPlugin {

	private static Plugin plugin;
	private static PluginConfig pluginConfig; // Never initialize classes
	private static PacketListeners packetListeners;

	private static Guns guns;

	@Override
	public void onEnable() {
		setPlugin(getPlugin(PlatinumCoreGuns.class));

		setPluginConfig(new PluginConfig());

		setGuns(new Guns());

		setPacketListeners(new PacketListeners());

		getServer().getPluginManager().registerEvents(new Listeners(), this);

		getCommand("platinumcore").setExecutor(new PlatinumCoreCommands());
		getCommand("platinumcore").setTabCompleter(new PlatinumCoreCommands());
		super.onEnable();
	}
	
	public static PacketListeners getPacketListeners() {
		return packetListeners;
	}

	public void setPacketListeners(PacketListeners packetListeners) {
		PlatinumCoreGuns.packetListeners = packetListeners;
	}

	public static Guns getGuns() {
		return guns;
	}

	public static void setGuns(Guns guns) {
		PlatinumCoreGuns.guns = guns;
	}

	public static Plugin getPlugin() {
		return plugin;
	}

	public static void setPlugin(Plugin plugin) {
		PlatinumCoreGuns.plugin = plugin;
	}

	public static PluginConfig getPluginConfig() {
		return pluginConfig;
	}

	public static void setPluginConfig(PluginConfig pluginConfig) {
		PlatinumCoreGuns.pluginConfig = pluginConfig;
	}

}
