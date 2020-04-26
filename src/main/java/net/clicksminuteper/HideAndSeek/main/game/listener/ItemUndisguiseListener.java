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

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import net.clicksminuteper.HideAndSeek.main.Reference;
import net.clicksminuteper.HideAndSeek.main.disguise.LibsInterface;
import net.clicksminuteper.HideAndSeek.main.util.SeekLog;

public class ItemUndisguiseListener implements Listener {

	@EventHandler
	public void onPlayerRightClick(PlayerInteractEvent event) {
		Player player = event.getPlayer();

		if (player.getInventory().getItemInMainHand().getType() == Material.BARRIER) {
			SeekLog.info("Undisguising " + player.getDisplayName() + "!");
			LibsInterface.cmdUndisguise(Reference.getLogger(), player);
		}
	}
}
