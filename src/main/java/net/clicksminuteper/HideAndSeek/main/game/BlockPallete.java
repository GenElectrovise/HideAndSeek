package net.clicksminuteper.HideAndSeek.main.game;

import java.util.ArrayList;
import java.util.List;

public class BlockPallete {
	public ArrayList<String> BLOCKS = new ArrayList<String>();

	public BlockPallete(List<String> blockPallete) {
		for (String blockName : blockPallete) {
			BLOCKS.add(blockName.toUpperCase().replace(" ", "_"));
		}
	}
	
	public ArrayList<String> getBlocks() {
		return BLOCKS;
	}

}
