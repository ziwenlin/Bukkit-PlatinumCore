package eu.platinumcore.plugin.guns.extra;

import org.bukkit.entity.Entity;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

import eu.platinumcore.plugin.guns.PlatinumCoreGuns;

public class Metadata {

	private static Plugin plugin = PlatinumCoreGuns.getPlugin();

	public void set(Entity entity, String key, Object value) {
		entity.setMetadata(key, new FixedMetadataValue(plugin, value));
	}

	public Object get(Entity entity, String key) {
		if (has(entity, key)) {
			for (MetadataValue mv : entity.getMetadata(key)) {
				if (mv.getOwningPlugin().equals(plugin)) {
					Object value = mv.value();
					return value;
				}
			}
		}
		return null;
	}

	public void remove(Entity entity, String key) {
		entity.removeMetadata(key, plugin);
	}

	public boolean has(Entity entity, String key) {
		return entity.hasMetadata(key);
	}

	public boolean compare(Entity entity, String key, Object value) {
		return (get(entity, key) == value);
	}
}
