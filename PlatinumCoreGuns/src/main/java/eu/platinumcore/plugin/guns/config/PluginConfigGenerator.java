package eu.platinumcore.plugin.guns.config;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import eu.platinumcore.plugin.guns.PlatinumCoreGuns;
import eu.platinumcore.plugin.guns.extra.Keys;

public class PluginConfigGenerator {

	private final static Plugin plugin = PlatinumCoreGuns.getPlugin();

	public void loadFile(String folder, String name, File file, FileConfiguration fileConfig) {
		PluginConfig.getFolderFiles().get(folder).put(name, fileConfig);
		PluginConfig.getFolderFileNames().get(folder).add(name);
	}

	public File generateFile(String folderName, String fileName) {
		File folder = new File(plugin.getDataFolder(), folderName);
		File file = new File(folder, fileName + Keys.extensionyml);
		if (!file.exists()) {
			file.mkdirs();
		}
		return file;
	}

	public void generateGun(String fileName) {
		File file = generateFile(Keys.guns, fileName);
		FileConfiguration fileConfig = new YamlConfiguration();
		fileConfig.set("Cooldown", 10);
		fileConfig.set("Accuracy", 50d);
		fileConfig.set("Spread", 10d);
		fileConfig.set("Recoil", 10d);
		fileConfig.set("RecoilType", "Normal");
		fileConfig.set("Zoom", false);
		fileConfig.set("DualWield", false);
		fileConfig.set("Sounds.Fire", Sound.BLOCK_DISPENSER_LAUNCH.toString());
		fileConfig.set("Sounds.NoAmmo", Sound.BLOCK_DISPENSER_FAIL.toString());
		fileConfig.set("Accepted Magazines", new ArrayList<>());
		fileConfig.set("Magazines", 1);
		fileConfig.set("Shots", 1);
		saveFile(fileConfig, file);
		loadFile(Keys.guns, fileName, file, fileConfig);
	}

	public void generateMagazine(String fileName) {
		File file = generateFile(Keys.magazines, fileName);
		FileConfiguration fileConfig = new YamlConfiguration();
		fileConfig.set("Size", 10);
		fileConfig.set("ReloadTime", 30);
		fileConfig.set("Projectiles", 1);
		fileConfig.set("Default Bullet", "");
		;
		saveFile(fileConfig, file);
		loadFile(Keys.magazines, fileName, file, fileConfig);
	}

	public void generateBullet(String fileName) {
		File file = generateFile(Keys.bullets, fileName);
		FileConfiguration fileConfig = new YamlConfiguration();
		fileConfig.set("Size", 10);
		fileConfig.set("ReloadTime", 30);
		fileConfig.set("Projectiles", 1);
		fileConfig.set("Default Bullet", "");
		;
		saveFile(fileConfig, file);
		loadFile(Keys.bullets, fileName, file, fileConfig);
	}

	public void saveFile(FileConfiguration fileConfig, File file) {
		try {
			fileConfig.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
