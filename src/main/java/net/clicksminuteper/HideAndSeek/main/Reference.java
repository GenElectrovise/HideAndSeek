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

import net.clicksminuteper.HideAndSeek.main.command.CommandRegistry;
import net.clicksminuteper.HideAndSeek.main.util.SeekLog;

public class Reference {
	private static Reference instance;
	private HideAndSeek hideAndSeek;
	private CommandRegistry commandRegistry;
	private EnableHandler enableHandler;

	public void set(HideAndSeek hideAndSeek) {
		this.hideAndSeek = hideAndSeek;
		SeekLog.setLogger(hideAndSeek.getLogger());
		commandRegistry = new CommandRegistry();
		enableHandler = new EnableHandler();

		SeekLog.info("Reference class populated!");
	}

	/**
	 * @param commandRegistry the commandRegistry to set
	 */
	public void setCommandRegistry(CommandRegistry commandRegistry) {
		this.commandRegistry = commandRegistry;
	}

	/**
	 * @param enableHandler the enableHandler to set
	 */
	public void setEnableHandler(EnableHandler enableHandler) {
		this.enableHandler = enableHandler;
	}

	/**
	 * @param hideAndSeek the hideAndSeek to set
	 */
	public void setHideAndSeek(HideAndSeek hideAndSeek) {
		this.hideAndSeek = hideAndSeek;
	}

	public HideAndSeek getHideAndSeek() {
		return hideAndSeek;
	}

	public CommandRegistry getCommandRegistry() {
		return commandRegistry;
	}

	public EnableHandler getEnableHandler() {
		return enableHandler;
	}

	public Reference() {
		Reference.instance = this;
	}

	public static Reference getInstance() {
		return instance;
	}
}
