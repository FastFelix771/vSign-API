package de.unitygaming.bukkit.vsign.api;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import de.unitygaming.bukkit.vsign.Version;
import de.unitygaming.bukkit.vsign.events.EventListener;
import de.unitygaming.bukkit.vsign.invoker.Invoker;

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