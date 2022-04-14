package me.space.authserver.main;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import me.space.authserver.commands.LoginCMD;
import me.space.authserver.commands.RegisterCMD;
import me.space.authserver.database.MySQL;

public class Main extends JavaPlugin implements PluginMessageListener {

	public static Plugin plugin;
	public static Main main;

	public static Main getMain() {
		return main;
	}

	public static Plugin getPlugin() {
		return plugin;
	}

	@Override
	public void onEnable() {
		main = this;
		plugin = this;

		Listeners.RegistrarEventos();
		getCommand("login").setExecutor(new LoginCMD());
		getCommand("registrar").setExecutor(new RegisterCMD());

		Bukkit.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		
		Bukkit.getMessenger().registerIncomingPluginChannel(this, "Return", this);

		for (Player todos : Bukkit.getOnlinePlayers()) {
			todos.kickPlayer("§cConexão perdida!");
		}

		Bukkit.getWorlds().forEach(mundo -> {
			System.out.println("[AuthServer - Mundos] Um total de " + mundo.getEntities().size()
					+ " entidades do mundo " + mundo.getName() + " foram removidos(as)!");
			mundo.getEntities().forEach(entidade -> {
				entidade.remove();
			});
		});

		MySQL.startConnection();
		if (MySQL.isAlive()) {
			System.out.println("[AuthServer] Servidor de Login iniciado corretamente!");
		} else {
			System.out.println("[AuthServer] Servidor de Login iniciado incorretamente!");
		}

	}

	@Override
    public void onPluginMessageReceived(String channel, Player p, byte[] message) {
       
        DataInputStream in = new DataInputStream(new ByteArrayInputStream(message));
       
        try {
            String sub = in.readUTF(); // Sub-Channel
            if (sub.equals("command")) { // As in bungee part we gave the sub-channel name "command", here we're checking it sub-channel really is "command", if it is we do the rest of code.
                String cmd = in.readUTF(); // Command we gave in Bungee part.
                System.out.println("[GlobalExecute] Received a command message from BungeeCord, executing it.");
                getServer().dispatchCommand(getServer().getConsoleSender(), cmd); // Executing the command!!
               
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

	@Override
	public void onDisable() {
		MySQL.stop();
	}

}
