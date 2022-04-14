package me.space.authserver.main;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_8_R1.ChatSerializer;
import net.minecraft.server.v1_8_R1.IChatBaseComponent;
import net.minecraft.server.v1_8_R1.PacketPlayOutChat;

public class Variables {
	
	public static void sendActionBar(Player p, String message) {
		IChatBaseComponent cbc = ChatSerializer.a("{\"text\": \"" + message + "\"}");
		PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc, (byte) 2);
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(ppoc);
		
	}
	
	public static void sendPlayer(Player p, String toServer) {
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(b);
		try {
			out.writeUTF("Connect");
			out.writeUTF(toServer);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		p.sendPluginMessage(Main.getPlugin(), "BungeeCord", b.toByteArray());
	}

}
