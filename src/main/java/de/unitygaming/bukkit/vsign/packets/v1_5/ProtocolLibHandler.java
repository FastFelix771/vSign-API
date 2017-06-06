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

import java.lang.reflect.InvocationTargetException;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ConnectionSide;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.injector.GamePhase;

import de.unitygaming.bukkit.vsign.PacketHandler;
import de.unitygaming.bukkit.vsign.util.ReturningInvoker;
import lombok.SneakyThrows;

public class ProtocolLibHandler implements PacketHandler {

	@Override @SneakyThrows(InvocationTargetException.class)
	public void sendPacket(Player player, PacketContainer packet) {
		ProtocolLibrary.getProtocolManager().sendServerPacket(player, packet);
	}

	@Override
	public void addPacketListener(Plugin plugin, final Player player, final PacketType type, final ReturningInvoker<PacketContainer, Boolean> invoker, final boolean dropPacketOnError) {
		ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(plugin, type) {

			@Override
			public void onPacketReceiving(PacketEvent e) {
				if(!(e.getPlayer().getName().equalsIgnoreCase(player.getName()))) return;
				if(!(e.getPacketType() == type)) return;

				try{
					e.setCancelled(invoker.invoke(e.getPacket()));
				} catch(Throwable t) {
					t.printStackTrace();
					e.setCancelled(dropPacketOnError);
				}
			}
		});
	}

	@SuppressWarnings("deprecation")
	@Override
	public void addPacketListener(Plugin plugin, final Player player, final int legacyID, final ReturningInvoker<PacketContainer, Boolean> invoker, final boolean dropPacketOnError) {
		ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(plugin, ConnectionSide.CLIENT_SIDE, GamePhase.PLAYING, legacyID) {

			@Override
			public void onPacketReceiving(PacketEvent e) {
				if(!(e.getPlayer().getName().equalsIgnoreCase(player.getName()))) return;
				if(!(e.getPacketID() == legacyID)) return;

				try{
					e.setCancelled(invoker.invoke(e.getPacket()));
				} catch(Throwable t) {
					t.printStackTrace();
					e.setCancelled(dropPacketOnError);
				}
			}
		});
	}

}
