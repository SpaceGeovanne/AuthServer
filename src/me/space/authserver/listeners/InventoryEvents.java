package me.space.authserver.listeners;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import me.space.authserver.captcha.CaptchaGUI;
import me.space.authserver.manager.LoginManager;

public class InventoryEvents implements Listener {
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent evt) throws Exception {
		if (evt.getInventory().getName().equalsIgnoreCase("§7Clique no peixe!")) {
			Player player = (Player) evt.getWhoClicked();
			evt.setCancelled(true);

			if (evt.getWhoClicked().equals(player)) {

				if (evt.getClickedInventory() != null && evt.getClickedInventory().equals(evt.getInventory())) {
					ItemStack item = evt.getCurrentItem();

					if (item != null && item.getType() != Material.AIR) {
						if (evt.getSlot() == 13) {
							player.playSound(player.getLocation(), Sound.ORB_PICKUP, 1F, 1F);
							player.sendMessage("§aO seu captcha foi validado!");
							LoginManager.onCaptcha.remove(player.getUniqueId());
							player.closeInventory();
						} else {
							player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1F, 1F);
						}
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onCloseInventoy(InventoryCloseEvent e) {
		Player p = (Player) e.getPlayer();
		if(LoginManager.onCaptcha.contains(p.getUniqueId())) {
			new CaptchaGUI(p);
		}
	}

}
