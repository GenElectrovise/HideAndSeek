package net.clicksminuteper.HideAndSeek.main.game;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import net.clicksminuteper.HideAndSeek.main.Reference;

public class ItemUndisguiseListener implements Listener {

	@EventHandler
	public void onPlayerRightClick(PlayerInteractEvent event) {
		Player player = event.getPlayer();

		if (player.getInventory().getItemInMainHand() == Game.DISGUISE) {
			Reference.getLogger().info("Undisguising " + player.getDisplayName() + "!");
		}
	}
}
