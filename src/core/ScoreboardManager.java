package core;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

import comparator.PlayerComparator;

public class ScoreboardManager {
	
	/**
	 * Set player name color based on custom team
	 * @param newPlayer
	 */
	public static void addTeam(GamePlayer newPlayer) {
		for (GamePlayer p : Game.players) {
			if (newPlayer == p) {
				Board.addTeam(p, newPlayer, "§a");
			} else {
				Board.addTeam(p, newPlayer, "§c");
				Board.addTeam(newPlayer, p, "§c");
			}
		}
	}
	
	/**
	 * Set target gold name
	 * @param hunter
	 */
	public static void targetColor(GamePlayer hunter) {
		for (GamePlayer p : Game.players) {
			if (hunter.getTarget() == p) {
				Board.addTeam(hunter, p, "§6");
			}
		}
	}
	
	/**
	 * update player score board to keep lead updated
	 */
	public static void updateScoreBoard() {
	    int minutes = Game.gameTime / 60;
 		int seconds = (Game.gameTime) % 60;
 		String time = String.format("%d:%02d", minutes, seconds);
 		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yy");  
 		LocalDateTime now = LocalDateTime.now();  
	    
		Collections.sort(Game.players, new PlayerComparator());
		for (GamePlayer p : Game.players) {
			p.board.setTitle("§f§lBOUNTY HUNTERS");
			p.board.set(15, "§7"+dtf.format(now)+" §8serverid");
			p.board.set(14, " ");
			if (Game.gameTime <= 0) {
				p.board.set(13, "§fGame ended!");
			} else {
				p.board.set(13, "§fTime Left: §a"+time);
			}
			p.board.set(12, "§fYour Points: §a"+p.getScore());
			p.board.set(11, "  ");
			p.board.set(10, "§fTop Players:");
			
			int line = 9;
			for (int i = 0; i < 6; i++) {
				if (i < Game.players.size()) {
					String prefix = "§c";
					if (p == Game.players.get(i)) {
						prefix = "§a";
					}
					p.board.set(line, prefix + Game.players.get(i).getDisplayName() + ": §a" + Game.players.get(i).getScore());
					line--;
				}
			}
			p.board.set(2, "   ");
			p.board.set(1, "§enot.hypixel.net");
		}
	}
}
