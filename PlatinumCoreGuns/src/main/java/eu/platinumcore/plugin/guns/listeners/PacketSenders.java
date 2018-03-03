package eu.platinumcore.plugin.guns.listeners;

import java.lang.reflect.InvocationTargetException;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;

public class PacketSenders {

	private ProtocolManager protocolManager;

	public PacketSenders(ProtocolManager protocolManager) {
		setProtocolManager(protocolManager);
	}

	public void sendZoom(Player player) {
		PacketContainer zoom = getProtocolManager().createPacket(PacketType.Play.Server.ENTITY_EFFECT, true);
		zoom.getEffectTypes().write(0, PotionEffectType.SLOW);
		zoom.getBytes().write(0, (byte) 2).write(1, (byte) 5);
	}

	public void sendExplosion(Location location, Player player) {

		PacketContainer explosion = getProtocolManager().createPacket(PacketType.Play.Server.EXPLOSION, true);
		explosion.getDoubles().write(0, location.getX()).write(1, location.getY()).write(2, location.getZ());
		explosion.getFloat().write(0, 3f);

		try {
			getProtocolManager().sendServerPacket(player, explosion);
		} catch (InvocationTargetException e) {
			throw new RuntimeException("Cannot send packet " + explosion, e);
		}
	}

	public void sendBullet(Location location, Player player) {

		PacketContainer bullet = getProtocolManager().createPacket(PacketType.Play.Server.SPAWN_ENTITY, true);

		bullet.getDoubles().write(0, location.getX()).write(1, location.getY()).write(2, location.getZ());
		// bullet.getShorts().write(0, (short) 10);

		try {
			getProtocolManager().sendServerPacket(player, bullet);
		} catch (InvocationTargetException e) {
			throw new RuntimeException("Cannot send packet " + bullet, e);
		}
	}

	public ProtocolManager getProtocolManager() {
		return protocolManager;
	}

	public void setProtocolManager(ProtocolManager protocolManager) {
		this.protocolManager = protocolManager;
	}

}
