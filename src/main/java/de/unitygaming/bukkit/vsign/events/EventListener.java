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
package de.unitygaming.bukkit.vsign.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

import de.unitygaming.bukkit.vsign.Version;
import de.unitygaming.bukkit.vsign.api.vSignAPI;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EventListener implements Listener {
	
	private final Plugin plugin;

	@EventHandler(priority = EventPriority.LOWEST)
	private void login(PlayerLoginEvent e) {
		if(!vSignAPI.check()) return;
		
		Version.getCurrent().getVirtualSign().setup(plugin, e.getPlayer());
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	private void logout(PlayerQuitEvent e) {
		if(!vSignAPI.check()) return;
		
		Version.getCurrent().getVirtualSign().unsetup(e.getPlayer());
	}
	
}
