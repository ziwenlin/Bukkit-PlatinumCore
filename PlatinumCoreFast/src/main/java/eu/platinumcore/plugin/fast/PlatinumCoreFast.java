package eu.platinumcore.plugin.fast;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import eu.platinumcore.plugin.fast.event.LootChestSpawnEvent;

public class PlatinumCoreFast extends JavaPlugin implements Listener{

	Plugin plugin = this;
	
	@Override
	public void onEnable() {
		// TODO Auto-generated method stub
		super.onEnable();
		getServer().getPluginManager().registerEvents(this, plugin);
		
	}
	
	@EventHandler
	public void onChestSpawn(LootChestSpawnEvent event) {
		
	}
	
}
