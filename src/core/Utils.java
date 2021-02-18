package core;

import java.util.List;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Utils {

	/**
	 * Game custom player object from real player name
	 * @param name
	 * @return
	 */
	public static GamePlayer getGamePlayer(String name) {
		for (GamePlayer p : Game.players) {
			if (p.getPlayer().getName().equals(name)) {
				return (p);
			}
		}
		return (null);
	}
	
	/**
	 * Give player essential stuff
	 * @param player
	 */
	public static void giveStuff(GamePlayer player) {
		ItemStack sword = new ItemStack(Material.WOOD_SWORD);
		ItemStack bow = new ItemStack(Material.BOW);
		ItemStack arrow = new ItemStack(Material.ARROW);
		ItemStack compass = new ItemStack(Material.COMPASS);
		ItemMeta compassMeta = sword.getItemMeta();
		ItemMeta swordMeta = sword.getItemMeta();
		ItemMeta bowMeta = sword.getItemMeta();
		
		player.getPlayer().getInventory().clear();
		swordMeta.spigot().setUnbreakable(true);
		sword.setItemMeta(swordMeta);
		sword.setAmount(1);
		
		bowMeta.spigot().setUnbreakable(true);
		bowMeta.setDisplayName("§aSniper§r §7(One Shot Kill)");
		bow.setItemMeta(bowMeta);
		bow.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 10);
		bow.setAmount(1);
		
		arrow.setAmount(1);
		
		compassMeta.setDisplayName("§aTarget Tracker");
		compass.setItemMeta(compassMeta);
		compass.setAmount(1);
		
        player.getPlayer().getInventory().addItem(sword);
        player.getPlayer().getInventory().addItem(bow);
        player.getPlayer().getInventory().addItem(arrow);
        player.getPlayer().getInventory().addItem(compass);
	}
	
	/**
	 * Teleport player on a random spawn point
	 * @param player
	 * @param spawnpoint
	 */
	public static void teleportPlayer(GamePlayer player, List<Location> spawnpoint) {
		Random rand = new Random();
		Location spawn = spawnpoint.get(rand.nextInt(spawnpoint.size()));
		
		player.getPlayer().setSaturation(100000);
		player.getPlayer().setHealth(20);
		player.getPlayer().teleport(spawn);
		giveStuff(player);
	}
	
	/**
	 * get random target
	 * @param player
	 */
	public static void getTarget(GamePlayer player) {
		Random rand = new Random();
		
		player.setTarget(player);
		while(player.getTarget() == player) {
			GamePlayer target = Game.players.get(rand.nextInt(Game.players.size()));
			player.setTarget(target);
		}
		player.getPlayer().sendMessage("§c§lTARGET ACQUIRED: §r§6"+player.getTarget().getPlayer().getName());
		ScoreboardManager.targetColor(player);
	}
	
	/**
	 * Center text in chat
	 * @param text
	 * @return
	 */
	public static String centerText(String text) {
	    int maxWidth = 80;
		int spaces = (int) Math.round((maxWidth-1.4*ChatColor.stripColor(text).length())/2);
		
	    return StringUtils.repeat(" ", spaces)+text;
	}
	
	/**
	 * Get distance between two location;
	 * @param l1
	 * @param l2
	 * @return
	 */
	public static int distance(Location l1, Location l2) {
		return (int) (Math.pow((l1.getX() - l2.getX()), 2) + Math.pow((l1.getY() - l2.getY()), 2) + Math.pow((l1.getZ() - l2.getZ()), 2));
	}
}
