package me.space.authserver.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class Others implements Listener {
	
	@EventHandler
	public void foodlevel(FoodLevelChangeEvent e) {
		e.setFoodLevel(20);
	}
	
	@EventHandler
	public void onSpawnCreature(EntitySpawnEvent e) {
		if(!(e.getEntity() instanceof Player)) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onDamageEvent(EntityDamageEvent e) {
		e.setDamage(0.0);
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onPlayerExecuteCommand(PlayerCommandPreprocessEvent e) {
		if(e.getMessage().startsWith("/registrar") || e.getMessage().startsWith("/login")) {
			e.setCancelled(false);
		} else {
			e.getPlayer().sendMessage("§cVocê precisa estar logado para executar um comando!");
			e.setCancelled(true);
		}
		
	}
	
	@EventHandler
	public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent e) {
		e.setDamage(0.0);
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		e.setCancelled(true);
	}
	 
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		e.setCancelled(true);
	}
	
}
