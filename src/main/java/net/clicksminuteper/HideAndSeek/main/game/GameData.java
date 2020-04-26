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

package net.clicksminuteper.HideAndSeek.main.game;

import org.bukkit.Location;

import net.clicksminuteper.HideAndSeek.main.game.block.BlockPalette;

public class GameData {

	private Location origin;
	private BlockPalette palette;
	
	public GameData(Location origin, BlockPalette palette) {
		this.origin = origin;
		this.palette = palette;
	}
	
	public Location getOrigin() {
		return origin;
	}
	
	public BlockPalette getPalette() {
		return palette;
	}
	
	public void setOrigin(Location origin) {
		this.origin = origin;
	}
	
	public void setPalette(BlockPalette palette) {
		this.palette = palette;
	}

}
