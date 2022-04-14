package me.space.authserver.commands;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import me.space.authserver.manager.LoginManager;

public class LoginCMD implements CommandExecutor, Listener {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lb, String[] args) {
		if (!(sender instanceof Player))
			return true;

		Player p = (Player) sender;
		if (LoginManager.inLogin.contains(p.getUniqueId())) {
			if (LoginManager.isRegistered(p)) {
				if(args.length == 1) {
					if(args[0].equals(LoginManager.getPassword(p))) {
						LoginManager.removeLogin(p);
						p.sendMessage("§aLogado!");
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 1, 1);
					} else {
						p.sendMessage("§cSenha incorreta!");
						p.playSound(p.getLocation(), Sound.VILLAGER_NO, 1, 1);
					}
					
					
				} else {
					p.sendMessage("§cUse: /login (senha)");
				}
			} else {
				p.sendMessage("§cEste nick ainda não está registrado!");
			}
		}
		return false;
	}

}
