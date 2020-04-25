package net.clicksminuteper.HideAndSeek.main;

import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;

public class ConfigHandler {
	private FileConfiguration config;

	public ConfigHandler(FileConfiguration config) {
		this.config = config;
	}

	public List<String> getBlockPalette(String name) {
		return config.getStringList("palettes." + name);
	}
	
	public int getLobbyLength() {
		return config.getInt("lobbylength");
	}
	
	public int getGameLength() {
		return config.getInt("gamelength");
	}

}
