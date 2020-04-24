package net.clicksminuteper.HideAndSeek.main.game;

import java.util.Random;

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

		if (player.getInventory().getItemInMainHand() == Game.DISGUISE) {
			Reference.getLogger().info("Disguising " + player.getDisplayName() + "!");
			
			Game game = Game.nearestGame(new ThreeDCoordinate(player.getLocation().getX(), player.getLocation().getY(),
					(player.getLocation().getZ())));
			BlockPallete pallete = game.getPallete();
			String block = pallete.getBlocks().get(new Random().nextInt(pallete.getBlocks().size() - 1));

			LibsInterface.cmdDisguise(Reference.getLogger(), player, block);
		}

	}
}
