package event;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class EntityDamageByEntity {
	
	/**
	 * Edit player damage behavior
	 * @param event
	 */
	public void run(EntityDamageByEntityEvent event) {
		if (event.getCause() == DamageCause.PROJECTILE && event.getDamager() instanceof Arrow) {
			Arrow arrow = (Arrow) event.getDamager();
			Player shooter = (Player) arrow.getShooter();
			
			if (shooter == event.getEntity()) {
				event.setCancelled(true);
				return;
			}
			event.setDamage(100);
		}
	}
}
