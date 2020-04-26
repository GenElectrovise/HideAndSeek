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

package net.clicksminuteper.HideAndSeek.main.command;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.clicksminuteper.HideAndSeek.main.game.Games;

/**
 * Destroys the Game at the given Locations, or the nearest Game to those if a
 * Game is not present at those Locations
 * 
 * @author GenElectrovise
 *
 */
public class CmdDestroyGame implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		int gameX;
		int gameY;
		int gameZ;

		if (!(sender instanceof Player)) {
			return false;
		}

		Player player = (Player) sender;

		try {
			gameX = new Integer(args[0]);
			gameY = new Integer(args[1]);
			gameZ = new Integer(args[2]);
		} catch (ClassCastException c) {
			sender.sendMessage("Incorrect args for command!");
			return false;
		}

		if (Games.nearestGame(new Location(player.getWorld(), gameX, gameY, gameZ)).destroy())
			return true;

		return false;
	}

}
