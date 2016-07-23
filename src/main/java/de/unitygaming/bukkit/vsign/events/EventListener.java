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