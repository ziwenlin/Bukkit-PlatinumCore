package eu.platinumcore.plugin;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

import eu.platinumcore.plugin.guns.config.Config;

public class PlatinumCoreGuns extends JavaPlugin {

	private static Config configC; // Never initialize classes
	private static CommandExecutor CE;
	private static List<String> gunTypes = new ArrayList<String>(); // Only initialize lists

	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(new Listeners(), this);

		setConfigC(new Config());
		getConfigC().load();

		setCE(new Commands());

		getGunTypes().addAll(getConfigC().guns.getKeys(false));

		getCommand("guns").setExecutor(getCE());
		getCommand("platinumcore").setExecutor(getCE());
		
	}

	@Override
	public void onDisable() {
		getGunTypes().clear();
	}

	
	
	
	public static Config getConfigC() {
		return configC;
	}

	public static void setConfigC(Config configC) {
		PlatinumCoreGuns.configC = configC;
	}

	public static List<String> getGunTypes() {
		return gunTypes;
	}

	public static void setGunTypes(List<String> gunTypes) {
		PlatinumCoreGuns.gunTypes = gunTypes;
	}

	public static CommandExecutor getCE() {
		return CE;
	}

	public static void setCE(CommandExecutor cE) {
		CE = cE;
	}

}
