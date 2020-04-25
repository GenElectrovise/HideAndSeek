package net.clicksminuteper.HideAndSeek.main.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import net.clicksminuteper.HideAndSeek.main.Reference;

public class Palettes {
	public static HashMap<String, BlockPalette> PALETTES = new HashMap<String, BlockPalette>();

	public void addPalette(String name, BlockPalette palette) {
		PALETTES.put(name, palette);
	}

	public static void generatePalettes() {
		Reference.getLogger().info("Generating block palletes!");

		Reference.getLogger().info(Reference.getHideandseek().toString());
		Reference.getLogger().info(Reference.getHideandseek().getConfig().toString());
		Reference.getLogger().info("lobbylength : "
				+ new Integer(Reference.getHideandseek().getConfig().getInt("lobbylength")).toString());
		try {
			@SuppressWarnings("unchecked")
			List<LinkedHashMap<String, ArrayList<String>>> paletteConfigList = (List<LinkedHashMap<String, ArrayList<String>>>) Reference
					.getHideandseek().getConfig().getList("palettes");
			Reference.getLogger().info("Found : " + paletteConfigList);

			for (LinkedHashMap<String, ArrayList<String>> list : paletteConfigList) {
				Reference.getLogger().info(" - " + list);
				BlockPalette newPalette = new BlockPalette();

				for (String key : list.keySet()) {
					ArrayList<String> nestedList = list.get(key);
					Reference.getLogger().info(" - - " + nestedList);
					
					newPalette.name = key;
					for (String blockName : nestedList) {
						Reference.getLogger().info(" - - - " + blockName);
						newPalette.addBlock(blockName);
					}
				}

				PALETTES.put(newPalette.name, newPalette);
			}

		} catch (ClassCastException c) {
			Reference.getLogger().severe("ERROR! CONFIGURATION IS INVALID! BLOCK PALETTES CAN ONLY CONTAIN STRINGS!");
			c.printStackTrace();
		}

		Reference.getLogger().info("Constructed palettes: " + PALETTES);

	}

	public static BlockPalette getPalette(String name) {
		return PALETTES.get(name);
	}
}
