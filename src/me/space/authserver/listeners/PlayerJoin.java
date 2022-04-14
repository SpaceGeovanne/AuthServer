package me.space.authserver.listeners;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import me.space.authserver.database.MySQL;
import me.space.authserver.main.Main;
import me.space.authserver.manager.LoginManager;

public class PlayerJoin implements Listener {
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		e.setJoinMessage(null);
		Player p = e.getPlayer();
		
		if(!MySQL.isAlive()) {
			p.kickPlayer("   §c§lAUTHSERVER   " + "\n"
							+ "    §7Erro ao carregar sua conta!   " + "\n" +
						"   §7O reparo automático foi iniciado, pedimos desculpas desde já pelo ocorrido!");
			Bukkit.getConsoleSender().sendMessage("[AuthServer - Manager] Detectamos uma falha neste servidor, iniciando reparo automático!");
			new BukkitRunnable() {
				
				@Override
				public void run() {
					cancel();
					Bukkit.shutdown();
				}
			}.runTaskTimer(Main.getPlugin(), 0, 30);
			return;
		}
		if(!LoginManager.hasPlayer(p)) {
			LoginManager.setPlayer(p);
			p.kickPlayer("§c§lCONTA - AUTHSERVER" + "\n"
					+ "     §7Sua conta foi verificada, relogue e divirta-se!");
			return;
		}
		
		p.setMaxHealth(2);
		p.setHealth(2);
		p.setFoodLevel(20);
		p.teleport(p.getWorld().getSpawnLocation());
		p.setGameMode(GameMode.ADVENTURE);
		p.sendMessage("");
		
		
		LoginManager.captcha(p);
		
		
	}

}
