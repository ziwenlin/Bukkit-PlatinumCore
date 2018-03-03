package eu.platinumcore.plugin.guns.guns;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import eu.platinumcore.plugin.guns.config.PluginConfig;
import eu.platinumcore.plugin.guns.extra.Items;
import eu.platinumcore.plugin.guns.extra.Keys;

public class Magazine {

	private FileConfiguration file;
	private ItemStack item;
	private String itemName;

	private Bullet bullet;
	private String defaultBullet;

	private int size;
	private int amount;
	private int projectiles;
	private int reloadTime;
	private boolean reusable;

	public Magazine() {
		setBullet(new Bullet());
	}

	public void reload(ItemStack item) {
		setItem(item);
		getItem().setAmount(1);
		setItemName(Items.getItemName(item));
		if (getItemName() != null && PluginConfig.getFolderFileNames().get(Keys.magazines).contains(getItemName())) {
			setFile(PluginConfig.getFolderFiles().get(Keys.magazines).get(getItemName()));
			setSize(file.getInt(Keys.size, 10));
			setReloadTime(file.getInt(Keys.reloadtime, 35));
			setProjectiles(file.getInt(Keys.projectiles, 1));
			setReusable(file.getBoolean(Keys.reusable, false));
			setDefaultBullet(file.getString(Keys.defaultbullet, null));

			if (defaultBullet == null) {
			file.set(Keys.size, size);
			file.set(Keys.reusable, reusable);
			file.set(Keys.reloadtime, reloadTime);
			file.set(Keys.projectiles, projectiles);
			file.set(Keys.defaultbullet, defaultBullet);
			}
		}

	}

	public FileConfiguration getFile() {
		return file;
	}

	public void setFile(FileConfiguration file) {
		this.file = file;
	}

	public ItemStack getItem() {
		return item;
	}

	public void setItem(ItemStack magazine) {
		this.item = magazine;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public boolean isReusable() {
		return reusable;
	}

	public void setReusable(boolean reusable) {
		this.reusable = reusable;
	}

	public int getReloadTime() {
		return reloadTime;
	}

	public void setReloadTime(int reloadTime) {
		this.reloadTime = reloadTime;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String name) {
		this.itemName = name;
	}

	public Bullet getBullet() {
		return bullet;
	}

	public void setBullet(Bullet bullet) {
		this.bullet = bullet;
	}

	public int getProjectiles() {
		return projectiles;
	}

	public void setProjectiles(int projectiles) {
		this.projectiles = projectiles;
	}

	public String getDefaultBullet() {
		return defaultBullet;
	}

	public void setDefaultBullet(String defaultBullet) {
		this.defaultBullet = defaultBullet;
	}

}
