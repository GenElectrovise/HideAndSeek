package net.clicksminuteper.HideAndSeek.main.game;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.server.v1_15_R1.Blocks;

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
