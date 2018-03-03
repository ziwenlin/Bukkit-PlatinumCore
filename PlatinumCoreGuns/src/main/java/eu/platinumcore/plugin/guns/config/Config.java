package eu.platinumcore.plugin.guns.config;

import java.io.File;
import java.io.IOException;

import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import eu.platinumcore.plugin.PlatinumCoreGuns;

public class Config {
	
	private static Plugin plugin = PlatinumCoreGuns.getPlugin(PlatinumCoreGuns.class);

	public File configF, gunsF, magazineF;
	public FileConfiguration config, guns, magazine;
	
	
	public void load() {
		loadFileConfig();
		loadFileGuns();
		loadFileMagazine();
		magazine = new YamlConfiguration();
		config = new YamlConfiguration();
		guns = new YamlConfiguration();
		try {
			magazine.load(magazineF);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
		try {
			config.load(configF);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
		try {
			guns.load(gunsF);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}

	public void loadFolderGuns() {
		
	}
	
	public void loadFileConfig() {
		try {
			configF = new File(plugin.getDataFolder(), "config.yml");
			if (!configF.exists()) {
				plugin.getLogger().info("Config.yml not found, creating!");
				configF.getParentFile().mkdirs();
				plugin.saveResource("config.yml", false);
			} else {
				plugin.getLogger().info("Config.yml found, loading!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void loadFileGuns() {
		try {
			gunsF = new File(plugin.getDataFolder(), "guns.yml");
			if (!gunsF.exists()) {
				plugin.getLogger().info("Guns.yml not found, creating!");
				gunsF.getParentFile().mkdirs();
				plugin.saveResource("guns.yml", false);
			} else {
				plugin.getLogger().info("Guns.yml found, loading!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void loadFileMagazine() {
		try {
			magazineF = new File(plugin.getDataFolder(), "magazine.yml");
			if (!magazineF.exists()) {
				plugin.getLogger().info("Magazine.yml not found, creating!");
				magazineF.getParentFile().mkdirs();
				plugin.saveResource("magazine.yml", false);
			} else {
				plugin.getLogger().info("Magazine.yml found, loading!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Sound stringToSoundGuns(String path) {
		String part = guns.getString(path);
		if (part != null) {
			try {
				if (Sound.valueOf(part) != null) {
					return Sound.valueOf(part);
				}
			} catch (IllegalArgumentException e) {
				plugin.getLogger().warning("Illegal String: " + path + part + "!");
			}
		}
		return null;
	}

	public Particle stringToParticleGuns(String path) {
		String part = guns.getString(path);
		if (part != null) {
			try {
				if (Particle.valueOf(part) != null) {
					return Particle.valueOf(part);
				}
			} catch (IllegalArgumentException e) {
				plugin.getLogger().warning("Illegal String: " + path + part + "!");
			}
		}
		return null;
	}

}
