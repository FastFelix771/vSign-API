package de.unitygaming.bukkit.vsign.packets.v1_6;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;

import de.unitygaming.bukkit.vsign.Version;
import de.unitygaming.bukkit.vsign.VirtualSign;
import de.unitygaming.bukkit.vsign.invoker.Invoker;
import de.unitygaming.bukkit.vsign.invoker.ReturningInvoker;
import lombok.SneakyThrows;

@SuppressWarnings("deprecation")
public class ProtocolLibvSign implements VirtualSign {

    protected static final HashMap<String, Invoker<String[]>> pending = new HashMap<>();

    @Override @SneakyThrows
    public void show(Player player, Invoker<String[]> callback) {
        if(pending.containsKey(player.getName())) return;
        pending.put(player.getName(), callback);

        PacketContainer packet = ProtocolLibrary.getProtocolManager().createPacket(133);
        
        packet.getIntegers()
        .write(0, 0)
        .write(1, 0)
        .write(2, 0)
        .write(3, 0);

        Version.getCurrent().getPacketHandler().sendPacket(player, packet);
    }

    @Override
    public void setup(Plugin plugin, final Player player) {

    	Version.getCurrent().getPacketHandler().addPacketListener(plugin, player, PacketType.Play.Client.UPDATE_SIGN, new ReturningInvoker<PacketContainer, Boolean>() {
            
            @Override
            public Boolean invoke(PacketContainer packet) {
                if(!pending.containsKey(player.getName())) return false;
                pending.remove(player.getName()).invoke(packet.getStringArrays().read(0));
                return true;
            }
        }, true);

    }

    @Override
    public void unsetup(Player player) {
        pending.remove(player.getName());
    }

}