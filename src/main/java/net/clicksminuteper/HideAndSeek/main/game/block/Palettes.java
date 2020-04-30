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
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import net.clicksminuteper.HideAndSeek.main.HideAndSeek;
import net.clicksminuteper.HideAndSeek.main.Reference;
import net.clicksminuteper.HideAndSeek.main.util.SeekLog;

public class Palettes {
	public static HashMap<String, BlockPalette> PALETTES = new HashMap<String, BlockPalette>();

	public void addPalette(String name, BlockPalette palette) {
		PALETTES.put(name, palette);
	}

	public static void generatePalettes() {
		SeekLog.info("Generating block palletes!");

		SeekLog.info(Reference.getInstance().getHideAndSeek().toString());
		SeekLog.info(Reference.getInstance().getHideAndSeek().getConfig().toString());
		SeekLog.info("lobbylength : "
				+ new Integer(Reference.getInstance().getHideAndSeek().getConfig().getInt("lobbylength")).toString());
		try {
			@SuppressWarnings("unchecked")
			List<LinkedHashMap<String, ArrayList<String>>> paletteConfigList = (List<LinkedHashMap<String, ArrayList<String>>>) Reference.getInstance().getHideAndSeek().getConfig().getList("palettes");
			SeekLog.info("Found : " + paletteConfigList);

			for (LinkedHashMap<String, ArrayList<String>> list : paletteConfigList) {
				SeekLog.info(" - " + list);
				BlockPalette newPalette = new BlockPalette();

				for (String key : list.keySet()) {
					ArrayList<String> nestedList = list.get(key);
					SeekLog.info(" - - " + nestedList);

					newPalette.name = key;
					for (String blockName : nestedList) {
						SeekLog.info(" - - - " + blockName);
						newPalette.addBlock(blockName);
					}
				}

				PALETTES.put(newPalette.name, newPalette);
			}

		} catch (ClassCastException c) {
			SeekLog.error("ERROR! CONFIGURATION IS INVALID! BLOCK PALETTES CAN ONLY CONTAIN STRINGS!");
			c.printStackTrace();
		}

		SeekLog.info("Constructed palettes: " + PALETTES);

	}

	public static BlockPalette getPalette(String name) {
		return PALETTES.get(name);
	}
}
