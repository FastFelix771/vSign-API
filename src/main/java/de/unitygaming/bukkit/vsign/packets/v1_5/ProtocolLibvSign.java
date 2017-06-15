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
package de.unitygaming.bukkit.vsign.packets.v1_5;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;

import de.unitygaming.bukkit.vsign.Version;
import de.unitygaming.bukkit.vsign.VirtualSign;
import de.unitygaming.bukkit.vsign.util.Invoker;
import de.unitygaming.bukkit.vsign.util.ReturningInvoker;

public class ProtocolLibvSign implements VirtualSign {

	protected static final HashMap<String, Invoker<String[]>> pending = new HashMap<String, Invoker<String[]>>();

	@Override
	public void show(Player player, Invoker<String[]> callback) {
		if(pending.containsKey(player.getName())) return;
		pending.put(player.getName(), callback);

		@SuppressWarnings("deprecation")
		PacketContainer packet = ProtocolLibrary.getProtocolManager().createPacket(130);

		packet.getIntegers()
		.write(0, 0)
		.write(1, 0)
		.write(2, 0);

		Version.getCurrent().getPacketHandler().sendPacket(player, packet);
	}

	@Override
	public void setup(final Plugin plugin, final Player player) {

		Version.getCurrent().getPacketHandler().addPacketListener(plugin, player, 130, new ReturningInvoker<PacketContainer, Boolean>() {

			@SuppressWarnings("deprecation")
			@Override
			public Boolean invoke(final PacketContainer packet) {
				if(!pending.containsKey(player.getName())) return false;

				Bukkit.getScheduler().runTask(plugin, new BukkitRunnable() {

					@Override
					public void run() {
						pending.remove(player.getName()).invoke(packet.getStringArrays().read(0));
					}

				});

				return true;
			}
		}, true);

	}

	@Override
	public void unsetup(Player player) {
		pending.remove(player.getName());
	}

}
