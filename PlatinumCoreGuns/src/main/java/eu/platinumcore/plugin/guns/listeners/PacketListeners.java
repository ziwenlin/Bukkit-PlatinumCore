package eu.platinumcore.plugin.guns.listeners;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers.PlayerDigType;

import eu.platinumcore.plugin.guns.PlatinumCoreGuns;
import eu.platinumcore.plugin.guns.guns.Guns;

public class PacketListeners {

	private Plugin plugin = PlatinumCoreGuns.getPlugin(PlatinumCoreGuns.class);
	private Guns handler = PlatinumCoreGuns.getGuns();
	private ProtocolManager protocolManager;
	private PacketSenders packetSenders;

	public PacketListeners() {
		setProtocolManager(ProtocolLibrary.getProtocolManager());
		setPacketSenders(new PacketSenders(getProtocolManager()));

		getProtocolManager().addPacketListener(new PacketAdapter(plugin, PacketType.Play.Client.BLOCK_PLACE) {
			@Override
			public void onPacketReceiving(PacketEvent event) {
				Player player = event.getPlayer();
				ItemStack item = player.getInventory().getItemInMainHand();
				if (handler.isGunValid(item)) {
					handler.fireGun(player, item);
					event.setCancelled(true);
				}
			}
		});

		getProtocolManager().addPacketListener(new PacketAdapter(plugin, PacketType.Play.Client.BLOCK_DIG) {
			@Override
			public void onPacketReceiving(PacketEvent event) {
				PlayerDigType action = event.getPacket().getPlayerDigTypes().read(0);
				if (action == PlayerDigType.DROP_ITEM) {
					Player player = event.getPlayer();
					ItemStack item = player.getInventory().getItemInMainHand();
					if (handler.isGunValid(item)) {
						handler.reloadGun(player, item);
						event.setCancelled(true);
					}
				}
			}
		});
	}

	public ProtocolManager getProtocolManager() {
		return protocolManager;
	}

	public void setProtocolManager(ProtocolManager protocolManager) {
		this.protocolManager = protocolManager;
	}

	public PacketSenders getPacketSenders() {
		return packetSenders;
	}

	public void setPacketSenders(PacketSenders packetSenders) {
		this.packetSenders = packetSenders;
	}
}
