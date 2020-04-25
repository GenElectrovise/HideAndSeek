package net.clicksminuteper.HideAndSeek.main.game;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import net.clicksminuteper.HideAndSeek.main.Reference;

public class PlayerMovementListener implements Listener {

	public static final HashMap<Player, ThreeDCoordinate> PLAYER_POSITIONS = new HashMap<Player, ThreeDCoordinate>();

	/**
	 * Player VS requires check
	 */
	public static final HashMap<Player, Boolean> SCHEDUELED_PLAYER_CHECKS = new HashMap<Player, Boolean>();

	@EventHandler
	public static void schedualTasks(PlayerMoveEvent event) {
		// Reference.getLogger().info("=+++=");
		// Reference.getLogger().info("Player " + event.getPlayer().getName() + "
		// moved!");

		if (Game.playersInAllGames.contains(event.getPlayer())) {

			// Reference.getLogger().info("Player was in game!");
			if (PLAYER_POSITIONS.containsKey(event.getPlayer())) {

				// Reference.getLogger().info("PLAYER_POSITIONS : " + PLAYER_POSITIONS);
				// .getLogger().info("SCHEDUELED_PLAYER_CHECKS : " + SCHEDUELED_PLAYER_CHECKS);

				if (SCHEDUELED_PLAYER_CHECKS.get(event.getPlayer()) == true) {
					// Reference.getLogger().info("Will schedual checkup!");
					schedualCheck(event.getPlayer(), 20);
					SCHEDUELED_PLAYER_CHECKS.put(event.getPlayer(), false);

					PLAYER_POSITIONS.put(event.getPlayer(), new ThreeDCoordinate(event.getPlayer().getLocation().getX(),
							event.getPlayer().getLocation().getY(), event.getPlayer().getLocation().getZ()));
				}

			} else {
				PLAYER_POSITIONS.put(event.getPlayer(), new ThreeDCoordinate(event.getPlayer().getLocation().getX(),
						event.getPlayer().getLocation().getY(), event.getPlayer().getLocation().getZ()));
				SCHEDUELED_PLAYER_CHECKS.put(event.getPlayer(), true);
				// Reference.getLogger().info("Player was not on list, have added");
			}
		}
		// Reference.getLogger().info("=---=");
	}

	private static void schedualCheck(final Player player, int i) {
		// Reference.getLogger().info("Scheduelling pos check for " + player.getName());
		Bukkit.getScheduler().runTaskLater(Reference.getHideandseek(), new Runnable() {

			@Override
			public void run() {
				check(player);
				SCHEDUELED_PLAYER_CHECKS.put(player, true);
			}
		}, 120);

	}

	private static void check(Player player) {
		// Reference.getLogger().info("Checking current vs old" + player.getName());
		// Reference.getLogger()
		// .info(" - " + player.getLocation().getBlockX() + " vs " +
		// Math.floor(PLAYER_POSITIONS.get(player).x));
		// Reference.getLogger()
		// .info(" - " + player.getLocation().getBlockZ() + " vs " +
		// Math.floor(PLAYER_POSITIONS.get(player).z));

		if (player.getLocation().getBlockX() == Math.floor(PLAYER_POSITIONS.get(player).x)
				&& player.getLocation().getBlockZ() == Math.floor(PLAYER_POSITIONS.get(player).z)) {
			teleportPlayer(player);
		}
	}

	private static void teleportPlayer(Player player) {

		double newX = player.getLocation().getBlockX() + 0.5;
		double newZ = player.getLocation().getBlockZ() + 0.5;

		String command = "tp " + player.getName() + " " + newX + " " + player.getLocation().getY() + " " + newZ;
		// Reference.getLogger().info("TELEPORTING " + player.getName() + " : " +
		// command);
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
	}
}
