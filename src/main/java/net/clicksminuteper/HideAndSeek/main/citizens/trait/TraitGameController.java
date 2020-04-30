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
package net.clicksminuteper.HideAndSeek.main.citizens.trait;

import org.bukkit.event.EventHandler;

import net.citizensnpcs.api.trait.Trait;
import net.clicksminuteper.HideAndSeek.main.event.HideAndSeekTickGamesEvent;
import net.clicksminuteper.HideAndSeek.main.game.Game;
import net.clicksminuteper.HideAndSeek.main.util.SeekLog;

/**
 * @author GenElectrovise
 *
 */
public class TraitGameController extends Trait {
	
	private Game game;
	
	public TraitGameController() {
		super("hns_gamecontroller");
		this.game = new Game(this);
	}
	
	/**
	 * @return the game
	 */
	public Game getGame() {
		return game;
	}
	
	@EventHandler
	public void listenForGameTick(HideAndSeekTickGamesEvent event) {
		SeekLog.debug("Game controller heard TickGamesEvent!");
	}

}
