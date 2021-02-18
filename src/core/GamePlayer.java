package core;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class GamePlayer {
	
	protected Player player;
	protected GamePlayer target;
	protected Board board;
	protected int Score;
	protected boolean targetKilled;
	
	public GamePlayer (Player player, Board board) {
		this.player = player;
		this.board = board;
		this.Score = 0;
		this.targetKilled = false;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public int getScore() {
		return Score;
	}

	public void setScore(int score) {
		Score = score;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public GamePlayer getTarget() {
		return target;
	}

	public void setTarget(GamePlayer target) {
		this.target = target;
	}

	public boolean isTargetKilled() {
		return targetKilled;
	}

	public void setTargetKilled(boolean targetKilled) {
		this.targetKilled = targetKilled;
	}
	
	public String getDisplayName() {
		return this.player.getDisplayName();
	}
	
	public String getName() {
		return this.player.getName();
	}
	
	public Player getTargetedPlayer() {
		return this.target.getPlayer();
	}
	
	public Location getTargetLocation() {
		return this.target.getPlayer().getLocation();
	}
}
