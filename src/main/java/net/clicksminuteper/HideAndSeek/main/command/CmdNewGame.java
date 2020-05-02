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
import net.clicksminuteper.HideAndSeek.main.game.block.Palettes;

/**
 * Usage: <b><i>/newgame palette X Y Z</i></b>
 * 
 * @author GenElectrovise
 *
 */
public class CmdNewGame implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		// If no arguments, fail
		if (args[0] == null) {
			return false;
		}

		// If too many arguments
		if (args[4] != null) {
			sender.sendMessage("Woah, woah, woah! Hold it! That's too many arguments! You have put " + args.length + " arguments, of a maximum of 4!");
			return false;
		}

		// If the palette does not exist, fail
		if (!Palettes.exists(args[0])) {
			sender.sendMessage("The block palette " + args[0] + "does not exist in HideAndSeek's config.yml, or is invalid!");
			return false;
		}

		// If sent by a Player
		if (sender instanceof Player) {
			Player player = (Player) sender;

			Location location = null;

			// If all coordinates populated
			if (args[1] != null && args[2] != null && args[2] != null) {
				Integer locX = null;
				Integer locY = null;
				Integer locZ = null;

				try {
					// Set X
					if (args[1] == "~") {
						locX = player.getLocation().getBlockX();
					} else {
						locX = new Integer(args[1]);
					}

					// Set Y
					if (args[3] == "~") {
						locY = player.getLocation().getBlockX();
					} else {
						locY = new Integer(args[2]);
					}

					// Set Z
					if (args[3] == "~") {
						locZ = player.getLocation().getBlockX();
					} else {
						locZ = new Integer(args[3]);
					}

					// Create location
					location = new Location(((Player) sender).getWorld(), locX, locY, locZ);

					// If an Integer cannot be made from an argument
				} catch (NumberFormatException n) {
					String failed = null;
					String argIn = null;

					if (locX == null) {
						failed = "X";
						argIn = args[1];
					} else if (locY == null) {
						failed = "Y";
						argIn = args[2];
					} else if (locZ == null) {
						failed = "Z";
						argIn = args[3];
					}

					sender.sendMessage(
							"Please provide parseable coordinates! The provided " + failed + " coordinate, which you have tried to set to " + argIn + ", cannot be converted to an integer!");
					sender.sendMessage("Example: /newgame genelectrovise 10 64 20");
					return false;
				}
			}

			// If NOT all of the coordinate arguments are populated
			else {
				int p = 0;
				if (args[1] != null) {
					p++;
				}
				if (args[2] != null) {
					p++;
				}
				if (args[3] != null) {
					p++;
				}

				// If only some arguments populated
				if (p < 3 && p > 0) {
					sender.sendMessage("Coordinate arguments are only semi-complete! Please fill all arguments or do not specify coordinates (Command will use your current position!)");
				}
				// If no coordinates specified
				if (p == 0) {
					// location is player's current location
					location = new Location(player.getWorld(), player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ());
				}
			}

			Games.newGame(location, args[0]);
			return true;
		}

		// If not sent by a Player
		else {
			// If any required arguments are null, fail.
			if (args[0] == null || args[1] == null || args[2] == null || args[3] == null) {
				return false;
			}
			// Otherwise construct location.
			else {
				Integer iX = null;
				Integer iY = null;
				Integer iZ = null;

				try {
					// Set X
					iX = new Integer(args[1]);

					// Set Y
					iY = new Integer(args[2]);

					// Set Z
					iZ = new Integer(args[3]);

					// If an Integer cannot be made from an argument
				} catch (NumberFormatException n) {
					String failed = null;
					String argIn = null;

					if (iX == null) {
						failed = "X";
						argIn = args[1];
					} else if (iY == null) {
						failed = "Y";
						argIn = args[2];
					} else if (iZ == null) {
						failed = "Z";
						argIn = args[3];
					}

					sender.sendMessage(
							"Please provide parseable coordinates! The provided " + failed + " coordinate, which you have tried to set to " + argIn + ", cannot be converted to an integer!");
					sender.sendMessage("Example: /newgame genelectrovise 10 64 20");
					return false;
				}

				Location location = new Location(null, iX, iY, iZ);
				Games.newGame(location, args[0]);
				return true;
			}
		}
	}

}
