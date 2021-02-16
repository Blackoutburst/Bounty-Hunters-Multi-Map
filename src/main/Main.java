package main;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

import core.Game;
import event.EntityDamageByEntity;
import event.PlayerDeath;
import event.PlayerJoin;
import event.PlayerMove;
import event.ProjectileHit;

public class Main extends JavaPlugin implements Listener {
	
	private Game game = new Game();
	
	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
		game.init();
	}
		
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    	switch(command.getName().toLowerCase()) {
    		case "play": return game.start();
    		case "l": return game.stop();
    		default: return false;
    	}
    }
    
    @Override
    public void onDisable() {}
		
	@EventHandler
 	public void onPlayerJoin(PlayerJoinEvent event) {
		new PlayerJoin().run(event);
	}

	@EventHandler
	public void onArrowHit(ProjectileHitEvent event){
		new ProjectileHit().run(event);
	}
	
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent event){
		new EntityDamageByEntity().run(event);
	}
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		new PlayerDeath().run(event);
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		new PlayerMove().run(event);
	}
}
