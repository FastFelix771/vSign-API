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

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.comphenix.protocol.ProtocolLibrary;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Version {
	
	v1_12(112, new de.unitygaming.bukkit.vsign.packets.v1_12.ProtocolLibHandler(), new de.unitygaming.bukkit.vsign.packets.v1_12.ProtocolLibvSign()),
	v1_11(111, new de.unitygaming.bukkit.vsign.packets.v1_11.ProtocolLibHandler(), new de.unitygaming.bukkit.vsign.packets.v1_11.ProtocolLibvSign()),
	v1_10(110, new de.unitygaming.bukkit.vsign.packets.v1_10.ProtocolLibHandler(), new de.unitygaming.bukkit.vsign.packets.v1_10.ProtocolLibvSign()), 
	v1_9 (99, new de.unitygaming.bukkit.vsign.packets.v1_9.ProtocolLibHandler(), new de.unitygaming.bukkit.vsign.packets.v1_9.ProtocolLibvSign()), 
	v1_8 (88, new de.unitygaming.bukkit.vsign.packets.v1_8.ProtocolLibHandler(), new de.unitygaming.bukkit.vsign.packets.v1_8.ProtocolLibvSign()), 
	v1_7 (77, new de.unitygaming.bukkit.vsign.packets.v1_7.ProtocolLibHandler(), new de.unitygaming.bukkit.vsign.packets.v1_7.ProtocolLibvSign()), 
	v1_6 (66, new de.unitygaming.bukkit.vsign.packets.v1_6.ProtocolLibHandler(), new de.unitygaming.bukkit.vsign.packets.v1_6.ProtocolLibvSign()),
	v1_5 (55, new de.unitygaming.bukkit.vsign.packets.v1_5.ProtocolLibHandler(), new de.unitygaming.bukkit.vsign.packets.v1_5.ProtocolLibvSign()),
	v1_4 (44, null, null),
	v1_3 (33, null, null),
	v1_2 (22, null, null),
	v1_1 (11, null, null), 
	v1_0 ( 0, null, null),
  UNKNOWN(-1, null, null);
	
	@Getter
	private static Version current = fromString(ProtocolLibrary.getProtocolManager().getMinecraftVersion().getVersion());
	
	@Getter(AccessLevel.PRIVATE) 
	private final int integer;

	@Getter
	private final PacketHandler packetHandler;

	@Getter
	private final VirtualSign virtualSign;

	public boolean isBetween(Version newer, Version older) {
		return this.getInteger() <= newer.getInteger() && this.getInteger() >= older.getInteger();
	}

	public boolean isNewerThan(Version version) {
		return version.getInteger() < this.getInteger();
	}

	public boolean isOlderThan(Version version) {
		return version.getInteger() > this.getInteger();
	}

	public static Set<Version> newerThan(Version version) {
		Set<Version> versions = new HashSet<Version>();
		for(Version v : values()) {
			if(v == UNKNOWN) continue;
			if(v.getInteger() > version.getInteger()) versions.add(v);
		}
		return Collections.unmodifiableSet(versions);
	}

	public static Set<Version> olderThan(Version version) {
		Set<Version> versions = new HashSet<Version>();
		for(Version v : values()) {
			if(v == UNKNOWN) continue;
			if(v.getInteger() < version.getInteger()) versions.add(v);
		}
		return Collections.unmodifiableSet(versions);
	}
	
	public static Version fromString(String input) {
		if(input.startsWith("1.12")) return Version.v1_12;
		if(input.startsWith("1.11")) return Version.v1_11;
		if(input.startsWith("1.10")) return Version.v1_10;
		if(input.startsWith("1.9")) return Version.v1_9;
		if(input.startsWith("1.8")) return Version.v1_8;
		if(input.startsWith("1.7")) return Version.v1_7;
		if(input.startsWith("1.6")) return Version.v1_6;
		if(input.startsWith("1.5")) return Version.v1_5;
		if(input.startsWith("1.4")) return Version.v1_4;
		if(input.startsWith("1.3")) return Version.v1_3;
		if(input.startsWith("1.2")) return Version.v1_2;
		if(input.startsWith("1.1")) return Version.v1_1;
		if(input.startsWith("1.0")) return Version.v1_0;

		return Version.UNKNOWN;
	}

}
