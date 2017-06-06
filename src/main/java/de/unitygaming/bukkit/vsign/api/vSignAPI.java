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
package de.unitygaming.bukkit.vsign.api;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import de.unitygaming.bukkit.vsign.Version;
import de.unitygaming.bukkit.vsign.events.EventListener;
import de.unitygaming.bukkit.vsign.util.Invoker;

public class vSignAPI {

	private final Plugin plugin;
	
	public vSignAPI(Plugin plugin) {
		this.plugin = plugin;
		
		Bukkit.getPluginManager().registerEvents(new EventListener(this.plugin), this.plugin);
	}
	
	
	/**
	 * Opens a new vSign in the specified players view.
	 * @param player The player that should see the vSign.
	 * @param callback The processor for the input text that comes back.
	 */
	public void open(Player player, Invoker<String[]> callback) {
		if(!check()) return;
		
		Version.getCurrent().getVirtualSign().show(player, callback);
	}
	
	/**
	 * Checks if vSigns will work on the underlying server.
	 * @return <code>true</code> if vSigns will work, <code>false</code> if not.
	 */
	public static boolean check() {
		return Bukkit.getPluginManager().isPluginEnabled("ProtocolLib") && Version.getCurrent() != null && Version.getCurrent() != Version.UNKNOWN && Version.getCurrent().getPacketHandler() != null && Version.getCurrent().getVirtualSign() != null;
	}
	
}
