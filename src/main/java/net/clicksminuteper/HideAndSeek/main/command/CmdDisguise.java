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

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.clicksminuteper.HideAndSeek.main.disguise.LibsInterface;
import net.clicksminuteper.HideAndSeek.main.util.SeekLog;

public class CmdDisguise implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player target;

		try {

			// If player does not exist with given name, return false, else set target
			if (Bukkit.getPlayer(args[0]) == null) {
				return false;
			} else {
				target = Bukkit.getPlayer(args[0]);
				SeekLog.info("Target : " + target.getDisplayName());
			}

			SeekLog.info("Block Type : " + args[1]);
			// If disguise cmd succeeds, return true
			if (LibsInterface.cmdDisguise(target, args[1])) {
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

}
