package core;

import java.io.File;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

public class MapSelector {
	
	private static final int MAP_COUNT = 3;
	
	/**
	 * Randomly select a map
	 * @return
	 */
	private static Maps getMap() {
		Random rand = new Random();
		int rng = rand.nextInt(MAP_COUNT);
		
		switch(rng) {
			case 0: return Maps.NORMAL;
			case 1: return Maps.NETHER;
			case 2: return Maps.END;
			default: return Maps.NORMAL;
		}
	}
	
	/**
	 * Get map name
	 * @param map
	 * @return
	 */
	private String getMapName(Maps map) {
		switch(map) {
			case NORMAL: return map.toString();
			case NETHER: return map.toString();
			case END: return map.toString();
			default : return "NORMAL";
		}
	}
	
	/**
	 * Get map respawn points
	 * @param config
	 * @param spawnpoint
	 */
	private void getRespawn(YamlConfiguration config, List<Location> spawnpoint) {
		spawnpoint.clear();
		Set<String> respawns = config.getConfigurationSection("respawn").getKeys(false);
		
		for (String resp : respawns) {
			String pos[] = config.getString("respawn."+resp).split(", ");
			float x = Float.valueOf(pos[0]);
			float y = Float.valueOf(pos[1]);
			float z = Float.valueOf(pos[2]);
			spawnpoint.add(new Location(Bukkit.getWorld(Game.WORLD_NAME), x, y, z, 0, 0));
		}
	}
	
	/**
	 * Initialize important value such as spawn point
	 * from world configuration map 
	 */
	public void init(List<Location> spawnpoint) {
		Maps map = getMap();
		String fileName = getMapName(map);
		YamlConfiguration config = YamlConfiguration.loadConfiguration(new File("./plugins/BountyHunter/"+fileName+".yml"));
		
		getRespawn(config, spawnpoint);
	}
	
}
