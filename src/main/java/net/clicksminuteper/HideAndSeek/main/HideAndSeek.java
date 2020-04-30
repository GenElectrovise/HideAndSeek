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

import org.bukkit.plugin.java.JavaPlugin;

import net.clicksminuteper.HideAndSeek.main.command.CommandRegistry;
import net.clicksminuteper.HideAndSeek.main.util.SeekLog;

public final class HideAndSeek extends JavaPlugin {
	
	private EnableHandler enableHandler = new EnableHandler();

	@Override
	public void onEnable() {
		new Reference();
		Reference.getInstance().setHideAndSeek(this);
		Reference.getInstance().setCommandRegistry(new CommandRegistry());
		Reference.getInstance().setEnableHandler(new EnableHandler());
		SeekLog.setLogger(getLogger());
		
		SeekLog.debug("Debug enabled!");
		SeekLog.info("Info!");
		SeekLog.warning("Warning!");
		SeekLog.error("Error!");
		
		enableHandler.handleEnable();
		
		saveDefaultConfig();
	}

	@Override
	public void onDisable() {
		
	}
}