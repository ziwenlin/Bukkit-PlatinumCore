package eu.platinumcore.plugin.guns.config;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import eu.platinumcore.plugin.guns.PlatinumCoreGuns;
import eu.platinumcore.plugin.guns.extra.Keys;

public class PluginConfig {

	private final static Plugin plugin = PlatinumCoreGuns.getPlugin();

	private static File pluginFolder;
	private static FileConfiguration config;

	private static Map<String, Map<String, FileConfiguration>> folderFiles = new HashMap<String, Map<String, FileConfiguration>>();
	private static Map<String, List<String>> folderFileNames = new HashMap<String, List<String>>();
	private static Map<String, File> folders = new HashMap<String, File>();
	private static List<String> folderNames = new ArrayList<String>();

	public PluginConfig() {
		getFolderNames().add(Keys.guns);
		getFolderNames().add(Keys.magazines);
		getFolderNames().add(Keys.bullets);

		setPluginFolder(plugin.getDataFolder());

		setConfig(plugin.getConfig());

		loadFolders();
	}

	private void loadFolders() {
		for (String folderName : getFolderNames()) {
			File folder = new File(getPluginFolder(), folderName);
			folder.mkdirs();
			getFolders().put(folderName, folder);
			getFolderFiles().put(folderName, new HashMap<String, FileConfiguration>());
			getFolderFileNames().put(folderName, new ArrayList<String>());
			loadFiles(folderName, Arrays.asList(folder.listFiles(fileNameFilter(Keys.extensionyml))));
		}
	}

	private void loadFiles(String folderName, List<File> fileList) {
		for (File file : fileList) {
			String fileName = file.getName().replaceAll(Keys.extensionyml, "");
			getFolderFileNames().get(folderName).add(fileName);
			getFolderFiles().get(folderName).put(fileName, new YamlConfiguration());
			if (loadFileConfiguration(folderName, fileName, file)) {
				plugin.getLogger().info("Loaded " + folderName + ": " + fileName + "!");
			} else {
				plugin.getLogger().info("Error " + folderName + ": " + fileName + "!");
			}
		}
	}

	private boolean loadFileConfiguration(String folderName, String fileName, File file) {
		try {
			getFolderFiles().get(folderName).get(fileName).load(file);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private FilenameFilter fileNameFilter(String extension) {
		FilenameFilter filter = new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				String lowercaseName = name.toLowerCase();
				if (lowercaseName.endsWith(extension)) {
					return true;
				} else {
					return false;
				}
			}
		};
		return filter;
	}

	public static FileConfiguration getConfig() {
		return config;
	}

	public static void setConfig(FileConfiguration config) {
		PluginConfig.config = config;
	}

	public static File getPluginFolder() {
		return pluginFolder;
	}

	public static void setPluginFolder(File pluginFolder) {
		PluginConfig.pluginFolder = pluginFolder;
	}

	public static Map<String, Map<String, FileConfiguration>> getFolderFiles() {
		return folderFiles;
	}

	public static void setFolderFiles(Map<String, Map<String, FileConfiguration>> folderFiles) {
		PluginConfig.folderFiles = folderFiles;
	}

	public static Map<String, List<String>> getFolderFileNames() {
		return folderFileNames;
	}

	public void setFolderFileNames(Map<String, List<String>> folderFileNames) {
		PluginConfig.folderFileNames = folderFileNames;
	}

	public static List<String> getFolderNames() {
		return folderNames;
	}

	public static void setFolderNames(List<String> folderNames) {
		PluginConfig.folderNames = folderNames;
	}

	public static Map<String, File> getFolders() {
		return folders;
	}

	public static void setFolders(Map<String, File> folders) {
		PluginConfig.folders = folders;
	}

}
