package core;

import java.lang.reflect.Field;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class NMS {
	
	/**
	 * Get Minecraft server class from name
	 * @param name
	 * @return
	 */
	public static Class<?> getNMSClass(String name) {
	    try {
	        return Class.forName("net.minecraft.server." + Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3] + "." + name);
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    }
	    return null;
	}
	
	/**
	 * Send player packets
	 * @param player
	 * @param packet
	 */
	public static void sendPacket(Player player, Object packet) {
	    try {
	        Object handle = player.getClass().getMethod("getHandle").invoke(player);
	        Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
	        
	        playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, packet);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	/**
	 * Set constructor fields
	 * @param edit
	 * @param fieldName
	 * @param value
	 */
	public static void setField(Object edit, String fieldName, Object value) {
		try {
			Field field = edit.getClass().getDeclaredField(fieldName);
			
			field.setAccessible(true);
			field.set(edit, value);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}
