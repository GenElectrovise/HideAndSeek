/**
 * HideAndSeek -- A Hide and Seek plugin for Bukkit and Spigot
 *  Copyright (C) 2020 GenElectrovise
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package net.clicksminuteper.HideAndSeek.main.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.citizensnpcs.api.npc.NPC;
import net.clicksminuteper.HideAndSeek.main.citizens.trait.TraitGameController;
import net.clicksminuteper.HideAndSeek.main.game.Game;
import net.clicksminuteper.HideAndSeek.main.game.Games;
import net.clicksminuteper.HideAndSeek.main.util.SeekLog;

/**
 * @author GenElectrovise
 *
 */
public class CmdListGames implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		try {
			if (Games.ACTIVE_GAMES.size() > 0) {
				SeekLog.info("Active Games: ");
				sender.sendMessage("Active Games: ");

				for (NPC npc : Games.ACTIVE_GAMES) {
					if (npc.hasTrait(TraitGameController.class)) {
						Game game = npc.getTrait(TraitGameController.class).getGame();
						SeekLog.info(" - " + game);
						sender.sendMessage(" - " + game);
					}
				}

				return true;
			} else {
				SeekLog.info("There are no active games.");
				sender.sendMessage("There are no active game.");
				return true;
			}
		} catch (Exception e) {
			SeekLog.error("An exception has been generated with the cause : " + e.getCause());
			SeekLog.stacktrace(e.getStackTrace());
		}
		return false;

	}

}
