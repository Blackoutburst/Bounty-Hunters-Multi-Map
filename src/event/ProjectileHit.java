package event;

import org.bukkit.event.entity.ProjectileHitEvent;

public class ProjectileHit {
	
	/**
	 * Edit player move behavior
	 * @param event
	 */
	public void run(ProjectileHitEvent event) {
		event.getEntity().remove();
	}
}
