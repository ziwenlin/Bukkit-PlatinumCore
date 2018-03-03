package eu.platinumcore.plugin.guns.guns;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import eu.platinumcore.plugin.guns.config.PluginConfig;
import eu.platinumcore.plugin.guns.extra.Extra;
import eu.platinumcore.plugin.guns.extra.Items;
import eu.platinumcore.plugin.guns.extra.Keys;

public class Gun {

	private FileConfiguration file;
	private String name;
	private ItemStack item;

	private Magazine magazine;

	private List<String> compatibleMagazine = new ArrayList<String>();

	private long lastTimeUsed = System.currentTimeMillis();

	private boolean zoom = false;
	private boolean dualWield = false;
	private String recoilType = "Normal";
	private double recoil = 0;
	private int magazines = 0;
	private int shots = 0;
	private int cooldown = 0;
	private double accuracy = 0;
	private double spread = 0;

	private Sound soundFireGun = Sound.BLOCK_STONE_BREAK;
	private Sound soundNoAmmo = Sound.BLOCK_NOTE_BELL;

	public Gun(ItemStack item) {
		reload(item);
		setMagazine(new Magazine());
	}

	public void reload(ItemStack item) {
		setItem(item);
		setName(Items.getItemName(item));
		setLastTimeUsed(System.currentTimeMillis());
		if (getName() != null && PluginConfig.getFolderFileNames().get(Keys.guns).contains(getName())) {
			setFile(PluginConfig.getFolderFiles().get(Keys.guns).get(getName()));
			setCooldown(file.getInt(Keys.cooldown, 10));
			setAccuracy(file.getDouble(Keys.accuracy, 50));
			setSpread(file.getDouble(Keys.spread, 50));
			setRecoil(file.getDouble(Keys.recoil, 10));
			setRecoilType(file.getString(Keys.recoiltype, "Normal"));
			setZoom(file.getBoolean(Keys.zoom, false));
			setDualWield(file.getBoolean(Keys.dualwield, false));
			setSoundFireGun(Extra.stringToSound(file.getString(Keys.soundsfire, null)));
			setSoundNoAmmo(Extra.stringToSound(file.getString(Keys.soundsnoammo, null)));
			setCompatibleMagazine(file.getStringList(Keys.acceptedmagazines));
			setMagazines(file.getInt(Keys.magazines, 1));
			setShots(file.getInt(Keys.shots, 1));

			if (shots == 0) {
				file.set(Keys.cooldown, getCooldown());
				file.set(Keys.accuracy, getAccuracy());
				file.set(Keys.spread, getSpread());
				file.set(Keys.recoil, getRecoil());
				file.set(Keys.recoiltype, getRecoilType());
				file.set(Keys.zoom, isZoom());
				file.set(Keys.dualwield, isDualWield());
				file.set(Keys.soundsfire, null);
				if (soundFireGun != null)
					file.set(Keys.soundsfire, getSoundFireGun().toString());
				file.set(Keys.soundsnoammo, null);
				if (soundNoAmmo != null)
					file.set(Keys.soundsnoammo, getSoundNoAmmo().toString());
				file.set(Keys.acceptedmagazines, getCompatibleMagazine());
				file.set(Keys.magazines, getMagazines());
				file.set(Keys.shots, getShots());
			}
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRecoilType() {
		return recoilType;
	}

	public void setRecoilType(String recoilType) {
		this.recoilType = recoilType;
	}

	public int getCooldown() {
		return cooldown;
	}

	public void setCooldown(int cooldown) {
		this.cooldown = cooldown;
	}

	public double getSpread() {
		return spread;
	}

	public void setSpread(double spread) {
		this.spread = spread;
	}

	public Sound getSoundFireGun() {
		return soundFireGun;
	}

	public void setSoundFireGun(Sound soundFireGun) {
		this.soundFireGun = soundFireGun;
	}

	public Sound getSoundNoAmmo() {
		return soundNoAmmo;
	}

	public void setSoundNoAmmo(Sound soundNoAmmo) {
		this.soundNoAmmo = soundNoAmmo;
	}

	public double getRecoil() {
		return recoil;
	}

	public void setRecoil(double recoil) {
		this.recoil = recoil;
	}

	public Magazine getMagazine() {
		return magazine;
	}

	public void setMagazine(Magazine magazine) {
		this.magazine = magazine;
	}

	public long getLastTimeUsed() {
		return lastTimeUsed;
	}

	public void updateLastTimeUsed(int time) {
		setLastTimeUsed(System.currentTimeMillis() + time);
	}

	public void setLastTimeUsed(long lastTimeUsed) {
		this.lastTimeUsed = lastTimeUsed;
	}

	public void setZoom(boolean zoom) {
		this.zoom = zoom;
	}

	public void setDualWield(boolean dualWield) {
		this.dualWield = dualWield;
	}

	public FileConfiguration getFile() {
		return file;
	}

	public void setFile(FileConfiguration file) {
		this.file = file;
	}

	public double getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(double accuracy) {
		this.accuracy = accuracy;
	}

	public List<String> getCompatibleMagazine() {
		return compatibleMagazine;
	}

	public void setCompatibleMagazine(List<String> compatibleMagazine) {
		this.compatibleMagazine = compatibleMagazine;
	}

	public int getMagazines() {
		return magazines;
	}

	public void setMagazines(int magazines) {
		this.magazines = magazines;
	}

	public int getShots() {
		return shots;
	}

	public void setShots(int shots) {
		this.shots = shots;
	}

	public ItemStack getItem() {
		return item;
	}

	public void setItem(ItemStack item) {
		this.item = item;
	}

	public boolean isZoom() {
		return zoom;
	}

	public boolean isDualWield() {
		return dualWield;
	}

}
