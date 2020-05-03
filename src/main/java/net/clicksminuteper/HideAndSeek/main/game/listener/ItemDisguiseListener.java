/*
 * HideAndSeek -- A Hide and Seek plugin for Bukkit and Spigot
    Copyright (C) 2020 GenElectrovise

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package net.clicksminuteper.HideAndSeek.main.game.listener;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import net.clicksminuteper.HideAndSeek.main.disguise.LibsInterface;
import net.clicksminuteper.HideAndSeek.main.game.Game;
import net.clicksminuteper.HideAndSeek.main.game.Games;
import net.clicksminuteper.HideAndSeek.main.game.block.BlockPalette;
import net.clicksminuteper.HideAndSeek.main.item.Constants;
import net.clicksminuteper.HideAndSeek.main.util.SeekLog;

public class ItemDisguiseListener implements Listener {

	@EventHandler
	public void onPlayerRightClick(PlayerInteractEvent event) {
		Player player = event.getPlayer();

		if (Games.ACTIVE_PLAYERS.containsKey(player)) {
			if (player.getInventory().getItemInMainHand() == Constants.ItemConstants.DISGUISE.itemStack
					|| (player.getInventory().getItemInMainHand().getType() == Material.CARVED_PUMPKIN)) {

				SeekLog.info("Heard right click from participating player! Player was holding a "
						+ player.getInventory().getItemInMainHand().getType() + ", so will disguise them randomly");

				SeekLog.info("Disguising " + player.getDisplayName() + "!");

				Game nearestGame = Games.nearestGameController(new Location(player.getWorld(), player.getLocation().getX(),
						player.getLocation().getY(), (player.getLocation().getZ()))).getGame();

				BlockPalette palette = nearestGame.gamedata.getPalette();

				SeekLog.info(palette.toString());

				Random random = new Random();

				String block = palette.getBlocks().get(random.nextInt(palette.getBlocks().size() - 1));

				LibsInterface.cmdDisguise(player, block);
			}

		}
	}
}