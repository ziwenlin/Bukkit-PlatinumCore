package eu.platinumcore.plugin;

import org.bukkit.entity.Entity;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class Metadata {

	private static Plugin plugin = PlatinumCoreGuns.getPlugin(PlatinumCoreGuns.class);

	public void setMD(Entity entity, String key, Object value) {
		entity.setMetadata(key, new FixedMetadataValue(plugin, value));
	}

	public Object getMD(Entity entity, String key) {
		if (hasMD(entity, key)) {
			for (MetadataValue mv : entity.getMetadata(key)) {
				if (mv.getOwningPlugin().equals(plugin)) {
					Object value = mv.value();
					return value;
				}
			}
		}
		return null;
	}

	public void removeMD(Entity entity, String key) {
		entity.removeMetadata(key, plugin);
	}

	public boolean hasMD(Entity entity, String key) {
		return entity.hasMetadata(key);
	}

	public boolean compare(Entity entity, String key, Object value) {
		return (getMD(entity, key) == value);
	}
}
