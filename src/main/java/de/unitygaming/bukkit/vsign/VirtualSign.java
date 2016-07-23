package de.unitygaming.bukkit.vsign;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import de.unitygaming.bukkit.vsign.invoker.Invoker;

public abstract interface VirtualSign {

    public void show(Player player, Invoker<String[]> callback);
    public void setup(Plugin plugin, Player player);
    public void unsetup(Player player);
    
}