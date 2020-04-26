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

package net.clicksminuteper.HideAndSeek.main.disguise;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

/**
 * Handles dispatching commands relating to Lib's Disguises
 *
 */
public class LibsInterface {
	public static boolean cmdDisguise(Logger logger, CommandSender sender, String blockType) {
		logger.info("ARGS == " + sender.getName() + " : " + blockType);

		return Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(),
				concatDisguisePlayerToFallingBlockCmd(logger, sender.getName(), blockType));
	}

	private static String concatDisguisePlayerToFallingBlockCmd(Logger logger, String playerName, String blockName) {
		String out = "disguiseplayer " + playerName + " FALLING_BLOCK " + blockName.toUpperCase();
		logger.info(out);
		return out;
	}

	public static boolean cmdUndisguise(Logger logger, CommandSender sender) {
		logger.info("ARGS == " + sender.getName());

		return Bukkit.dispatchCommand(sender, "undisguiseplayer " + sender.getName());
	}
}
