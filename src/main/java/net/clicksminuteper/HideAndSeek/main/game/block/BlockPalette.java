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

package net.clicksminuteper.HideAndSeek.main.game.block;

import java.util.ArrayList;
import java.util.List;

public class BlockPalette {
	public ArrayList<String> BLOCKS = new ArrayList<String>();
	public String name;

	public BlockPalette(List<String> blockPalette) {
		for (String blockName : blockPalette) {
			BLOCKS.add(blockName.toUpperCase().replace(" ", "_"));
		}
	}
	
	public BlockPalette(String... blocks) {
		for(String block : blocks) {
			BLOCKS.add(block);
		}
	}

	public ArrayList<String> getBlocks() {
		return BLOCKS;
	}
	
	public void addBlock(String block) {
		BLOCKS.add(block);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();

		builder.append("BlockPalette:[");

		for (String str : BLOCKS) {
			builder.append(str + ", ");
		}

		builder.append("]");

		return builder.toString();
	}

}
