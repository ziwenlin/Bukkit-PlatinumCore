package eu.platinumcore.plugin.guns.guns.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UUIDHandler {

	private static List<UUID> listUUID = new ArrayList<UUID>();

	public static UUID generateUUID() {
		UUID random = UUID.randomUUID();
		if (!getListUUID().contains(random)) {
			getListUUID().add(random);
			return random;
		}
		return generateUUID();
	}

	public boolean addUUID(UUID uuid) {
		if (!getListUUID().contains(uuid)) {
			getListUUID().add(uuid);
			return true;
		}
		return false;
	}

	public UUID getUUIDfromString(String uuid) {
		if (uuid.indexOf('-') == 9) {
			UUID id = UUID.fromString(uuid);
			addUUID(id);
			return id;
		}
		return null;
	}

	public static List<UUID> getListUUID() {
		return listUUID;
	}

	public void setListUUID(List<UUID> listUUID) {
		UUIDHandler.listUUID = listUUID;
	}

}
