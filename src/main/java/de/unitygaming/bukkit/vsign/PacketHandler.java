package de.unitygaming.bukkit.vsign;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;

import de.unitygaming.bukkit.vsign.invoker.ReturningInvoker;

public abstract interface PacketHandler {
    
    public void sendPacket(Player player, PacketContainer packet);
    public void addPacketListener(Plugin plugin, Player player, PacketType type, ReturningInvoker<PacketContainer, Boolean> invoker, boolean dropPacketOnError);
    
}