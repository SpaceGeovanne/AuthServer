package me.space.authserver.commands;

import org.bukkit.event.Listener;

import me.space.authserver.manager.LoginManager;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RegisterCMD implements CommandExecutor, Listener {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lb, String[] args) {
		if(!(sender instanceof Player)) return true;
		
		Player p = (Player) sender;
		if(LoginManager.inLogin.contains(p.getUniqueId())) {
			if(LoginManager.isRegistered(p)) {
				p.sendMessage("§cEste nick já está registrado!");
			} else {
				if(args.length == 1) {
					LoginManager.saveLogin(p, args[0]);
					LoginManager.removeLogin(p);
					p.sendMessage("§aRegistrado!");
				} else {
					p.sendMessage("§cUse: /registrar (senha)");
				}
			}
		}
		return false;
	}
	
	

}
