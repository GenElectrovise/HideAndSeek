package net.clicksminuteper.HideAndSeek.main.game;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import net.clicksminuteper.HideAndSeek.main.Reference;
import net.clicksminuteper.HideAndSeek.main.disguise.LibsInterface;

public class ItemUndisguiseListener implements Listener {

	@EventHandler
	public void onPlayerRightClick(PlayerInteractEvent event) {
		Player player = event.getPlayer();

		if (player.getInventory().getItemInMainHand().getType() == Material.BARRIER) {
			Reference.getLogger().info("Undisguising " + player.getDisplayName() + "!");
			LibsInterface.cmdUndisguise(Reference.getLogger(), player);
		}
	}
}
