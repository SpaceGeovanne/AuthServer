package me.space.authserver.captcha;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import me.space.authserver.main.Main;

public class CaptchaGUI {

	public CaptchaGUI(Player p) {
		Inventory inv = Bukkit.createInventory(p, 27, "§7Clique no peixe!");

		new BukkitRunnable() {
			
			@Override
			public void run() {
				
				ItemStack peixe = new ItemStack(Material.RAW_FISH);
				ItemMeta peixem = peixe.getItemMeta();
				peixem.setDisplayName("§aPeixe!");
				peixe.setItemMeta(peixem);
				
				ItemStack apple = new ItemStack(Material.APPLE);
				ItemMeta applem = apple.getItemMeta();
				applem.setDisplayName("§cMaçã!");
				apple.setItemMeta(applem);
				
				ItemStack osso = new ItemStack(Material.BONE);
				ItemMeta ossom = osso.getItemMeta();
				ossom.setDisplayName("§7Osso!");
				osso.setItemMeta(ossom);
				
				inv.setItem(11, osso);
				inv.setItem(13, peixe);
				inv.setItem(15, apple);
				
				p.openInventory(inv);
				cancel();
				
			}
		}.runTaskTimer(Main.getPlugin(), 0, 20);
	}

	

}
