package eu.platinumcore.plugin;

import java.util.concurrent.Callable;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Runnables {

	private static Plugin plugin = PlatinumCoreGuns.getPlugin(PlatinumCoreGuns.class);

	public static void taskA(Runnable task) {
		new BukkitRunnable() {
			@Override
			public void run() {
				task.run();
			}
		}.runTaskAsynchronously(plugin);
	}

	public static void taskLA(Runnable task, long delay) {
		new BukkitRunnable() {
			@Override
			public void run() {
				task.run();
			}
		}.runTaskLaterAsynchronously(plugin, delay);
	}

	public static void taskTA(Callable<Boolean> task, long delay, long interval) {
		new BukkitRunnable() {
			@Override
			public void run() {
				try {
					if (!(boolean) task.call()) {
						cancel();
					}
				} catch (IllegalStateException e) {
					Bukkit.broadcastMessage(task.toString() + " caused an exception!");
					e.printStackTrace();
					cancel();
				} catch (Exception e) {
					Bukkit.broadcastMessage(task.toString() + " caused an exception!");
					e.printStackTrace();
					cancel();
				}
			}
		}.runTaskTimerAsynchronously(plugin, delay, interval);
	}

	public static void task(Runnable task) {
		new BukkitRunnable() {
			@Override
			public void run() {
				task.run();
			}
		}.runTask(plugin);
	}

	public static void taskL(Runnable task, long delay) {
		new BukkitRunnable() {
			@Override
			public void run() {
				task.run();
			}
		}.runTaskLater(plugin, delay);
	}

	public static void taskT(Callable<Boolean> task, long delay, long interval) {
		new BukkitRunnable() {
			@Override
			public void run() {
				try {
					if (!task.call()) {
						cancel();
					}
				} catch (IllegalStateException e) {
					Bukkit.broadcastMessage(task.toString() + " caused an exception!");
					e.printStackTrace();
					cancel();
				} catch (Exception e) {
					Bukkit.broadcastMessage(task.toString() + " caused an exception!");
					e.printStackTrace();
					cancel();
				}
			}
		}.runTaskTimer(plugin, delay, interval);
	}
}
