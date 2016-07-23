package de.unitygaming.bukkit.vsign.packets.v1_7;

import org.bukkit.entity.Player;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;

import de.unitygaming.bukkit.vsign.Version;
import de.unitygaming.bukkit.vsign.VirtualSign;
import de.unitygaming.bukkit.vsign.invoker.Invoker;

public class ProtocolLibvSign extends de.unitygaming.bukkit.vsign.packets.v1_6.ProtocolLibvSign implements VirtualSign {

	@Override
	public void show(Player player, Invoker<String[]> callback) {
		if(pending.containsKey(player.getName())) return;
		pending.put(player.getName(), callback);

		PacketContainer packet = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.OPEN_SIGN_EDITOR);

		packet.getIntegers()
		.write(0, 0)
		.write(1, 0)
		.write(2, 0);

		Version.getCurrent().getPacketHandler().sendPacket(player, packet);
	}

}