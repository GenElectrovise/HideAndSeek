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

import net.clicksminuteper.HideAndSeek.main.game.Games;

/**
 * Destroys the Game at the given Locations, or the nearest Game to those if a
 * Game is not present at those Locations. <br>
 * Usage: <b><i>/destroygame (X Y Z) (message)</i></b>
 * 
 * @author GenElectrovise
 *
 */
public class CmdDestroyGame implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length != 4 || args.length != 0 || args.length != 3) {
			sender.sendMessage("Incorrect number of arguments! (" + args.length + " of necessary 3)");
			return false;
		}

		try {
			Integer iX;
			Integer iY;
			Integer iZ;

			iX = new Integer(args[0]);
			iY = new Integer(args[1]);
			iZ = new Integer(args[2]);

			Location location = new Location(null, iX, iY, iZ);

			
			if (Games.nearestGameController(location).destroy()) {
				return true;
			}

		} catch (NumberFormatException e) {
			sender.sendMessage("One argument passed cannot be parsed to an integer.");
			return false;
		}

		return false;
	}

}
