/*******************************************************************************
 * Copyright (C) 2017 Felix Drescher-Hackel
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package de.unitygaming.bukkit.vsign.packets.v1_7;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;

import de.unitygaming.bukkit.vsign.Version;
import de.unitygaming.bukkit.vsign.VirtualSign;
import de.unitygaming.bukkit.vsign.util.Invoker;
import de.unitygaming.bukkit.vsign.util.ReturningInvoker;

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
	
	@Override
	public void setup(Plugin plugin, final Player player) {

		Version.getCurrent().getPacketHandler().addPacketListener(plugin, player, PacketType.Play.Client.UPDATE_SIGN, new ReturningInvoker<PacketContainer, Boolean>() {

			@Override
			public Boolean invoke(PacketContainer packet) {
				if(!pending.containsKey(player.getName())) return false;
				pending.remove(player.getName()).invoke(packet.getStringArrays().read(0));
				return true;
			}
		}, true);

	}

}
