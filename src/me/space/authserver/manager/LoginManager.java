package me.space.authserver.manager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.mysql.jdbc.PreparedStatement;

import me.space.authserver.captcha.CaptchaGUI;
import me.space.authserver.database.MySQL;
import me.space.authserver.main.Main;
import me.space.authserver.main.Variables;

public class LoginManager extends MySQL {

	public static List<UUID> inLogin = new ArrayList<>();
	public static HashMap<Player, Integer> cooldownToLogin = new HashMap<>();
	public static List<UUID> onCaptcha = new ArrayList<>();
	
	public static void captcha(Player p) {
		onCaptcha.add(p.getUniqueId());
		Map<Player, Integer> timer = new HashMap<>(); 
		new CaptchaGUI(p);
		timer.put(p, 15);
		
		
		new BukkitRunnable() {
			
			@Override
			public void run() {
				if(!onCaptcha.contains(p.getUniqueId())) {
					this.cancel();
					timer.remove(p);
					if(p != null) {
						startLogin(p);
					}
					return;
				}
				timer.put(p, timer.get(p) - 1);
				if(timer.get(p) == 0) {
					p.kickPlayer("§cVocê não completou o captcha!");
				}
			}
		}.runTaskTimer(Main.getPlugin(), 0, 20);
	}

	public static void startLogin(Player p) {
		inLogin.add(p.getUniqueId());
		cooldownToLogin.put(p, 30);

		p.sendMessage("");

		new BukkitRunnable() {

			@Override
			public void run() {
				if (!inLogin.contains(p.getUniqueId())) {
					this.cancel();
					if(p != null) {
						Variables.sendActionBar(p, "§aAutenticado!");
						p.sendMessage("§a§lLOBBY §7Você está sendo enviado para o lobby!");
						System.out.println("[AuthServer] O jogador " + p.getName() + " logou!");
						Variables.sendPlayer(p, "lobby");
					}
					return;
				}
				if (cooldownToLogin.containsKey(p) && cooldownToLogin.get(p) == 0) {
					this.cancel();
					p.kickPlayer("§cVocê demorou muito para se logar!");
				}
				checkLogin(p);
			}
		}.runTaskTimer(Main.getPlugin(), 0, 20);
	}

	public static void checkLogin(Player p) {
		if (inLogin.contains(p.getUniqueId())) {
			if (isRegistered(p)) {

				if(cooldownToLogin.get(p) == 29) {
					p.sendMessage("§a§lLOGIN §7Use: /login (senha) para se logar!");
				}
				cooldownToLogin.put(p, cooldownToLogin.get(p) - 1);
				if (cooldownToLogin.get(p) == 25 || cooldownToLogin.get(p) == 20
						|| cooldownToLogin.get(p) == 15 || cooldownToLogin.get(p) == 10
						|| cooldownToLogin.get(p) == 5) {
					p.sendMessage("§a§lLOGIN §7Use: /login (senha) para se logar!");
					p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 1);
				}

				if (cooldownToLogin.get(p) >= 20) {
					Variables.sendActionBar(p, "§aVocê tem " + cooldownToLogin.get(p) + " segundos para se logar!");
				} else if (cooldownToLogin.get(p) >= 10 && cooldownToLogin.get(p) < 20) {
					Variables.sendActionBar(p, "§eVocê tem " + cooldownToLogin.get(p) + " segundos para se logar!");
				} else if (cooldownToLogin.get(p) < 10) {
					Variables.sendActionBar(p, "§cVocê tem " + cooldownToLogin.get(p) + " segundos para se logar!");
				} else if (cooldownToLogin.get(p) < 5) {
					p.playSound(p.getLocation(), Sound.NOTE_PIANO, 1, 1);
				}

			} else {

				cooldownToLogin.put(p, cooldownToLogin.get(p) - 1);
				if(cooldownToLogin.get(p) == 29) {
					p.sendMessage("§a§lLOGIN §7Use: /registrar (senha) (senha) para se logar!");
				}
				if (cooldownToLogin.get(p) == 25 || cooldownToLogin.get(p) == 20
						|| cooldownToLogin.get(p) == 15 || cooldownToLogin.get(p) == 10
						|| cooldownToLogin.get(p) == 5) {
					p.sendMessage("§a§lLOGIN §7Use: /registrar (senha) (senha) para se logar!");
					p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 1);
				}

				if (cooldownToLogin.get(p) >= 20) {
					Variables.sendActionBar(p, "§aVocê tem " + cooldownToLogin.get(p) + " segundos para se logar!");
				} else if (cooldownToLogin.get(p) >= 10 && cooldownToLogin.get(p) < 20) {
					Variables.sendActionBar(p, "§eVocê tem " + cooldownToLogin.get(p) + " segundos para se logar!");
				} else if (cooldownToLogin.get(p) < 10) {
					Variables.sendActionBar(p, "§cVocê tem " + cooldownToLogin.get(p) + " segundos para se logar!");
				}
			}
		}
	}

	public static boolean hasPlayer(Player p) {
		PreparedStatement stm = null;
		try {
			stm = (PreparedStatement) con.prepareStatement("SELECT * FROM `authserver` WHERE `player` = ?");
			stm.setString(1, p.getName());
			ResultSet rs = stm.executeQuery();
			while (rs.next()) {
				return true;
			}
			return false;
		} catch (SQLException e) {
			return false;
		}
	}

	public static void setPlayer(Player p) {
		PreparedStatement stm = null;

		try {
			stm = (PreparedStatement) con
					.prepareStatement("INSERT INTO `authserver`(`player`, `password`) VALUES (?,?)");
			stm.setString(1, p.getName());
			stm.setString(2, "default");
			stm.executeUpdate();
			System.out.println("[AuthServer - LoginManager] A conta do jogador " + p.getName()
					+ " foi registrada ao banco de dados!");
		} catch (SQLException e) {
			e.printStackTrace();

		}
	}

	public static boolean isRegistered(Player p) {
		if (getPassword(p).equals("default")) {
			return false;
		} else {
			return true;
		}
	}

	public static String getPassword(Player p) {
		if (hasPlayer(p)) {
			PreparedStatement stm = null;

			try {
				stm = (PreparedStatement) con.prepareStatement("SELECT * FROM `authserver` WHERE `player` = ?");
				stm.setString(1, p.getName());
				ResultSet rs = stm.executeQuery();
				while (rs.next()) {
					return rs.getString("password");
				}
				return null;
			} catch (SQLException e) {
				return null;
			}
		} else {
			return null;
		}

	}

	public static void removeLogin(Player p) {
		cooldownToLogin.remove(p);
		inLogin.remove(p.getUniqueId());
	}

	public static void saveLogin(Player p, String password) {
		if (!isRegistered(p)) {
			PreparedStatement stm = null;

			try {
				stm = (PreparedStatement) con
						.prepareStatement("UPDATE `authserver` SET `password` = ? WHERE `player` = ?");
				stm.setString(1, password);
				stm.setString(2, p.getName());
				stm.executeUpdate();
			} catch (SQLException e) {
			}
		}
	}

}
