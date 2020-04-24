package net.clicksminuteper.HideAndSeek.main.game;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import net.clicksminuteper.HideAndSeek.main.Reference;

public class PlayerDeathListener implements Listener {

	@EventHandler
	public static void listenForPlayerDeaths(EntityDamageEvent event) {

		Reference.getLogger().info("EntityDamageEvent triggered");

		Player player;
		if (event.getEntity() instanceof Player) {
			player = (Player) event.getEntity();

			Reference.getLogger().info("Damaged Entity is a Player!");

			if (event.getDamage() > player.getHealth()) {

				Reference.getLogger().info("They would die from that damage!");

				event.setCancelled(true);
				double pX = player.getLocation().getX();
				double pY = player.getLocation().getY();
				double pZ = player.getLocation().getZ();

				Game nearestGame = Game.nearestGame(new ThreeDCoordinate(pX, pY, pZ));
				Reference.getLogger().info("Nearest game is at " + nearestGame.origin.x + "," + nearestGame.origin.z);

				if (nearestGame.players.get(player.getName()) != null) {
					Reference.getLogger().info("Player is participating in that game!");
					event.setCancelled(true);
					Reference.getLogger().info("Cancelled damage event");
					nearestGame.players.get(player.getName()).setSeeker(true);
					Reference.getLogger().info("Player is now a seeker");
					player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
					Reference.getLogger().info("Reset their health to max");
				}
			}
		}
	}
}
