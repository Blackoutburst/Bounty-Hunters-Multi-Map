package core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import comparator.PlayerComparator;
import main.Main;

public class Game {
	
	public static final String WORLD_NAME = "world";
	public static int gameTime = 0;
	public static boolean running = false;
	public static Location spawn;
	public static List<GamePlayer> players = new ArrayList<GamePlayer>();
	
	private static List<Location> spawnpoint = new ArrayList<Location>();

	private int gameScheduler = 0;
	private int targetScheduler = 0;

	
	/**
	 * Initialize important game value
	 */
	public void init() {
		spawn = new Location(Bukkit.getWorld(WORLD_NAME), -120.5f, 82.5f, 272.5f, 0, 0);
	}
	
	/**
	 * Start the game
	 * @return
	 */
	public boolean start() {
		if (players.size() < 2) {
			return true;
		}
		gameTime = 20;
		running = true;
		new MapSelector().init(spawnpoint);
		setPlayer();
		gameUpdater();
		targetUpdater();
		return true;
	}
	
	/**
	 * Stop the game
	 * @return
	 */
	public boolean stop() {
		running = false;
		gameTime = 0;
		for (GamePlayer p : players) {
			p.getPlayer().setHealth(20);
			p.getPlayer().getInventory().clear();
			p.getPlayer().teleport(spawn);
		}
		return true;
	}
	
	/**
	 * Set player score to 0 and teleport them to a random spawn point
	 */
	private void setPlayer() {
		for (GamePlayer player : players) {
			player.setScore(0);
			Utils.teleportPlayer(player, spawnpoint);
		}
	}
	
	/**
	 * Update the game every seconds
	 */
	private void gameUpdater() {
		gameScheduler = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(Main.class), new Runnable(){
            @Override
            public void run(){
            	gameTime--;
            	if (gameTime <= 0) {
            		endGame();
            	}
            	ScoreboardManager.updateScoreBoard();
            }
		}, 0L,  20L);
	}
	
	/**
	 * Update the target once every 30 seconds
	 */
	private void targetUpdater() {
		targetScheduler = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(Main.class), new Runnable(){
            @Override
            public void run(){
            	for (GamePlayer p : players) {
            		ScoreboardManager.addTeam(p);
            	}
            	for (GamePlayer p : players) {
            		p.setTarget(null);
            		p.setTargetKilled(false);
            		Utils.getTarget(p);
            	}
            }
		}, 0L,  (20L * 30L));
	}
	
	/**
	 * End game
	 */
	private void endGame() {
		running = false;
		stopUpdater();
		broadcastGameResult();
		resetPlayerStatus();
	}
	
	/**
	 * Stop game schedulers
	 */
	private void stopUpdater() {
		Bukkit.getScheduler().cancelTask(gameScheduler);
		Bukkit.getScheduler().cancelTask(targetScheduler);
	}
	
	/**
	 * Broadcast game results in chat
	 */
	private void broadcastGameResult() {
		Collections.sort(players, new PlayerComparator());
		Bukkit.broadcastMessage("§a§l§m---------------------------------------------");
		Bukkit.broadcastMessage(Utils.centerText("§f§lBounty Hunters"));
		Bukkit.broadcastMessage(" ");
		try{Bukkit.broadcastMessage(Utils.centerText("§e§l1st Place §r§7- §r"+players.get(0).getPlayer().getName()+" §r§7 - "+players.get(0).getScore()+" Points"));}catch(Exception e) {}
		try{Bukkit.broadcastMessage(Utils.centerText("§e§l2nd Place §r§7- §r"+players.get(1).getPlayer().getName()+" §r§7 - "+players.get(1).getScore()+" Points"));}catch(Exception e) {}
		try{Bukkit.broadcastMessage(Utils.centerText("§e§l3rd Place §r§7- §r"+players.get(2).getPlayer().getName()+" §r§7 - "+players.get(2).getScore()+" Points"));}catch(Exception e) {}
		Bukkit.broadcastMessage(" ");
		Bukkit.broadcastMessage("§a§l§m---------------------------------------------");
		ScoreboardManager.updateScoreBoard();
	}
	
	/**
	 * Set back player to a normal state
	 */
	private void resetPlayerStatus() {
		for (GamePlayer p : players) {
			p.getPlayer().setHealth(20);
			p.getPlayer().getInventory().clear();
			p.getPlayer().teleport(spawn);
			ScoreboardManager.addTeam(p);
			p.setScore(0);
		}
	}
	
	/**
	 * Return every available spawn point 
	 * @return
	 */
	public static List<Location> getSpawnPoint() {
		return spawnpoint;
	}
}
