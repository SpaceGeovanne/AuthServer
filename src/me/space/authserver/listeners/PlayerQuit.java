package me.space.authserver.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import me.space.authserver.manager.LoginManager;

public class PlayerQuit implements Listener {
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		e.setQuitMessage(null);
		Player p = e.getPlayer();
		
		if(LoginManager.inLogin.contains(p.getUniqueId()) || LoginManager.onCaptcha.contains(p.getUniqueId())) {
			LoginManager.onCaptcha.remove(p.getUniqueId());
			LoginManager.inLogin.remove(p.getUniqueId());
		} 
		
		
 	}

}
