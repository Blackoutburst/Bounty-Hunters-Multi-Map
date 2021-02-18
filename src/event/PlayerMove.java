package event;

import java.lang.reflect.Constructor;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

import core.Game;
import core.NMS;
import core.Utils;

public class PlayerMove {
	
	/**
	 * Edit player move behavior
	 * @param event
	 */
	public void run(PlayerMoveEvent event) {
		if (!Game.running) {return;}
		event.getPlayer().setCompassTarget(Utils.getGamePlayer(event.getPlayer().getName()).getTargetLocation());
		String str = buildTargetString(event.getPlayer(), Utils.getGamePlayer(event.getPlayer().getName()).getTargetedPlayer());
		
		try {
			sendString(event.getPlayer(), str);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Build target distance string
	 * @param player
	 * @param target
	 * @return
	 */
	private String buildTargetString(Player player, Player target) {
		String str = "§cTarget: §6";
		
		str += target.getName();
		str += " §7- ";
		str += "§a"+Utils.distance(player.getLocation(), target.getLocation())+"m";
		return str;
	}
	
	/**
	 * Send target distance string through packet
	 * @param player
	 * @param str
	 * @throws Exception
	 */
	private void sendString(Player player, String str) throws Exception {
		Constructor<?> ChatComponentText;
		Class<?> typeChatComponentText = NMS.getNMSClass("ChatComponentText");
		Object targetDisantcePacket = NMS.getNMSClass("PacketPlayOutChat").getDeclaredConstructor().newInstance();
		
		ChatComponentText = typeChatComponentText.getConstructor(String.class);
		
		NMS.setField(targetDisantcePacket, "a", ChatComponentText.newInstance(str));
		NMS.setField(targetDisantcePacket, "b", (byte)(2));
		NMS.sendPacket(player, targetDisantcePacket);
	}
}
