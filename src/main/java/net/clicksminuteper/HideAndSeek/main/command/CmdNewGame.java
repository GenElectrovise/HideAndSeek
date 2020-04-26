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

import java.util.logging.Logger;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.clicksminuteper.HideAndSeek.main.Reference;
import net.clicksminuteper.HideAndSeek.main.game.Game;
import net.clicksminuteper.HideAndSeek.main.game.GameData;
import net.clicksminuteper.HideAndSeek.main.game.Games;
import net.clicksminuteper.HideAndSeek.main.game.block.BlockPalette;
import net.clicksminuteper.HideAndSeek.main.game.block.Palettes;
import net.clicksminuteper.HideAndSeek.main.util.SeekLog;

public class CmdNewGame implements CommandExecutor {
	private Logger logger;

	public CmdNewGame(Logger logger) {
		this.logger = logger;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length != 2 && !(sender instanceof Player)) {
			return false;
		}
		if (new Integer(args[0]) == null || new Integer(args[1]) == null) {
			return false;
		}

		BlockPalette palette = Palettes.getPalette(args[3]);
		SeekLog.info("Palette name: " + args[3]);
		SeekLog.info("Palette object: " + palette);

		if (!(sender instanceof Player)) {
			sender.sendMessage("This command can only be run by a player, as a World object must be availables!");
			return false;
		}

		Player player = (Player) sender;

		if (args[0] == null) {

			double dGameX = player.getLocation().getX();
			double dGameY = player.getLocation().getY();
			double dGameZ = player.getLocation().getZ();
			int iGameX = (int) Math.floor(dGameX);
			int iGameY = (int) Math.floor(dGameY);
			int iGameZ = (int) Math.floor(dGameZ);

			SeekLog.info(
					player.getDisplayName() + " has created a new game of HideAndSeek at : " + iGameX + "," + iGameZ);

			Game game = Games.newGame(new GameData(new Location(player.getWorld(), iGameX, iGameY, iGameZ), palette));
			game.run();

			player.sendMessage("New HideAndSeek game created! " + game);
			return true;
		} else {
			Integer iGameX = new Integer(args[0]);
			Integer iGameY = new Integer(args[1]);
			Integer iGameZ = new Integer(args[2]);

			SeekLog.info("Creating new game of HideAndSeek at : " + iGameX + "," + iGameZ);
			SeekLog.info("Logger : " + logger);
			SeekLog.info("HideAndSeek : " + Reference.getHideandseek());
			SeekLog.info("2dCoord : " + new Location(player.getWorld(), iGameX, iGameY, iGameZ));

			Location coordIn = new Location(player.getWorld(), iGameX, iGameY, iGameZ);

			GameData data = new GameData(coordIn, palette);
			Games.newGame(data);
			return true;
		}
	}

}
