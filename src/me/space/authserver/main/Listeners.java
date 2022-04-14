package me.space.authserver.main;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import me.space.authserver.commands.LoginCMD;
import me.space.authserver.commands.RegisterCMD;
import me.space.authserver.listeners.InventoryEvents;
import me.space.authserver.listeners.Others;
import me.space.authserver.listeners.PlayerJoin;
import me.space.authserver.listeners.PlayerQuit;

public class Listeners {
	
	public static void RegistrarEventos() {
		List<Listener> eventos = new ArrayList<>();
		eventos.add(new PlayerJoin());
		eventos.add(new LoginCMD());
		eventos.add(new RegisterCMD());
		eventos.add(new InventoryEvents());
		eventos.add(new PlayerQuit());
		eventos.add(new Others());
		eventos.forEach(e -> {
			Bukkit.getPluginManager().registerEvents(e, Main.getPlugin());
			Bukkit.getConsoleSender().sendMessage("[AuthServer - Evento] O evento " + e.getClass().getName().replace("me.space.authserver", "").replace(".listeners.", "").replace(".commands.", "") + " foi carregado!");
		});
	}

}
