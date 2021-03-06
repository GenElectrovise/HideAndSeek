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

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.clicksminuteper.HideAndSeek.main.Reference;
import net.clicksminuteper.HideAndSeek.main.game.Game;
import net.clicksminuteper.HideAndSeek.main.util.ThreeDCoordinate;

/**
 * Command for joining a game of HideAndSeek
 * 
 * @author GenElectrovise
 *
 */
public class CmdJoin implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		try {
			ThreeDCoordinate pos;
			Player player = (Player) sender;

			// If no args
			if (args[0] == null) {
				// Pos is player's pos
				pos = new ThreeDCoordinate(player.getLocation().getX(), player.getLocation().getY(),
						player.getLocation().getZ());
				player.sendMessage("Joining from pos : " + pos);

				// If has args, try to set pos to args
			} else {
				// Pos is args > Coord
				pos = new ThreeDCoordinate(new Integer(args[0]), new Integer(args[1]), new Integer(args[2]));
			}

			Game nearestGame = Game.nearestGame(pos);
			if (nearestGame.canJoin) {
				nearestGame.addPlayer(player);
				player.sendMessage("Joining HideAndSeek!");
				return true;
			} else {
				player.sendMessage(
						"That game is not joinable right now! Games have a max length of 5 mins plus a 2 minute lobby waiting time, so your maximum wait for this game is 7 minutes! Sorry!");
			}
		} catch (IndexOutOfBoundsException i) {
			sender.sendMessage("Incorrect arguments for command 'join' OR 'join X Y Z'");
			return false;
		} catch (Exception e) {
			Reference.getLogger().warning("Incorrect arguments for command '" + command.toString() + "' sent by "
					+ ((Player) sender).getDisplayName());
		}

		return false;
	}

}
