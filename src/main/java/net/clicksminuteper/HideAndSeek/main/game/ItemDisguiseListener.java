package net.clicksminuteper.HideAndSeek.main.game;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import net.clicksminuteper.HideAndSeek.main.Reference;
import net.clicksminuteper.HideAndSeek.main.disguise.LibsInterface;

public class ItemDisguiseListener implements Listener {

	@EventHandler
	public void onPlayerRightClick(PlayerInteractEvent event) {
		Player player = event.getPlayer();

		if (Game.playersInAllGames.contains(player)) {
			if (player.getInventory().getItemInMainHand() == Game.DISGUISE
					|| (player.getInventory().getItemInMainHand().getType() == Material.CARVED_PUMPKIN)) {

				Reference.getLogger().info("Heard right click from participating player! Player was holding a "
						+ player.getInventory().getItemInMainHand().getType() + ", so will disguise them randomly");

				Reference.getLogger().info("Disguising " + player.getDisplayName() + "!");

				Game nearestGame = Game.nearestGame(new ThreeDCoordinate(player.getLocation().getX(),
						player.getLocation().getY(), (player.getLocation().getZ())));
				
				BlockPalette palette = nearestGame.getPalette();

				Reference.getLogger().info(nearestGame.getPalette().toString());

				String block = palette.getBlocks().get(new Random().nextInt(palette.getBlocks().size() - 1));

				Reference.getLogger()
						.info("Heard right click! Player was holding a CARVED_PUMPKIN, so will disguise them randomly");

				LibsInterface.cmdDisguise(Reference.getLogger(), player, block);
			}

		}
	}
}