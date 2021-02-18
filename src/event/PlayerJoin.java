package event;

import org.bukkit.GameMode;
import org.bukkit.event.player.PlayerJoinEvent;

import core.Board;
import core.Game;
import core.GamePlayer;
import core.ScoreboardManager;

public class PlayerJoin {
	
	/**
	 * Edit player join behavior
	 * @param event
	 */
	public void run(PlayerJoinEvent event) {
		Board board = new Board(event.getPlayer(), "§a", "§r");
		GamePlayer newPlayer = new GamePlayer(event.getPlayer(), board);
		
		Game.players.add(newPlayer);
		ScoreboardManager.addTeam(newPlayer);
		ScoreboardManager.updateScoreBoard();
		
		event.getPlayer().setSaturation(100000);
		event.getPlayer().teleport(Game.spawn);
		event.getPlayer().setGameMode(GameMode.ADVENTURE);
	}
}
