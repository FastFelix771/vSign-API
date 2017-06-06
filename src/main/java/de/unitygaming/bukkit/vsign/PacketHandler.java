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
package de.unitygaming.bukkit.vsign;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;

import de.unitygaming.bukkit.vsign.util.ReturningInvoker;

public abstract interface PacketHandler {
    
    public void sendPacket(Player player, PacketContainer packet);
    public void addPacketListener(Plugin plugin, Player player, PacketType type, ReturningInvoker<PacketContainer, Boolean> invoker, boolean dropPacketOnError);
    public void addPacketListener(Plugin plugin, Player player, int legacyID, ReturningInvoker<PacketContainer, Boolean> invoker, boolean dropPacketOnError);
    
}
