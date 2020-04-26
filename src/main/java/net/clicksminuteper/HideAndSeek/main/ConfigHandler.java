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

package net.clicksminuteper.HideAndSeek.main;

import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;

public class ConfigHandler {
	private FileConfiguration config;

	public ConfigHandler(FileConfiguration config) {
		this.config = config;
	}
	
	public void reload() {
		config = Reference.getHideandseek().getConfig();
	}
	
	public FileConfiguration getConfig() {
		return config;
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

	public boolean getDebugEnabled() {
		return config.getBoolean("debugEnabled");
	}

}
