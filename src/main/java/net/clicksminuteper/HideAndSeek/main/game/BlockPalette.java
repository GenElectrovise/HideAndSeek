package net.clicksminuteper.HideAndSeek.main.game;

import java.util.ArrayList;
import java.util.List;

public class BlockPalette {
	public ArrayList<String> BLOCKS = new ArrayList<String>();

	public BlockPalette(List<String> blockPalette) {
		for (String blockName : blockPalette) {
			BLOCKS.add(blockName.toUpperCase().replace(" ", "_"));
		}
	}

	public ArrayList<String> getBlocks() {
		return BLOCKS;
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
