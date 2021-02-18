package event;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import core.Game;
import core.GamePlayer;
import core.ScoreboardManager;
import core.Utils;
import main.Main;

public class PlayerDeath {
	
	/**
	 * Edit player death behavior
	 * @param event
	 */
	public void run(PlayerDeathEvent event) {
		event.setDeathMessage(event.getEntity().getName()+"§e was killed by §r"+event.getEntity().getKiller().getName());
		event.setKeepInventory(true);
		updateScore(event);
		ScoreboardManager.updateScoreBoard();
		Utils.teleportPlayer(Utils.getGamePlayer(event.getEntity().getName()), Game.getSpawnPoint());
		Bukkit.getScheduler().runTaskLater(Main.getPlugin(Main.class), () -> event.getEntity().setVelocity(new Vector(0, 0, 0)), 1L);
	}
	
	/**
	 * Give killer a new arrow
	 * @param killer
	 */
	private void giveArrowOnKill(GamePlayer killer) {
		ItemStack item = new ItemStack(Material.ARROW);
		
		item.setAmount(1);
		killer.getPlayer().getInventory().addItem(item);
	}
	
	/**
	 * Update killer score
	 * @param event
	 */
	private void updateScore(PlayerDeathEvent event) {
		GamePlayer killer = Utils.getGamePlayer(event.getEntity().getKiller().getName());
		
		if (killer.getTarget().getPlayer().getName().equals(event.getEntity().getName()) && !killer.isTargetKilled()) {
			killer.setTargetKilled(true);
			killer.getPlayer().sendMessage("§l§aTARGET ELIMINATED! §r§6+7 POINT");
			killer.setScore(killer.getScore() + 7);
		} else {
			killer.getPlayer().sendMessage("§l§2PLAYER ELIMINATED! §r§6+1 POINT");
			killer.setScore(killer.getScore() + 1);
		}
		giveArrowOnKill(killer);
	}
}
