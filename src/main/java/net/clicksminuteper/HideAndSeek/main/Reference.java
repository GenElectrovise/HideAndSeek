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

import java.util.logging.Logger;

import net.clicksminuteper.HideAndSeek.main.command.CommandRegistry;
import net.clicksminuteper.HideAndSeek.main.enable.DisableHandler;
import net.clicksminuteper.HideAndSeek.main.enable.EnableHandler;

public class Reference {
	private static HideAndSeek hideandseek;
	private static Logger logger;
	private static CommandRegistry commandregistry;
	private static EnableHandler enablehandler;
	private static DisableHandler disablehandler;
	private static ConfigHandler confighandler;

	public static void set() {
		logger = hideandseek.getLogger();

		logger.info("Setting important references! (Already set logger)");
		commandregistry = new CommandRegistry(logger, hideandseek);
		enablehandler = new EnableHandler(logger);
		disablehandler = new DisableHandler(logger);
		confighandler = new ConfigHandler(hideandseek.getConfig());
	}

	public static HideAndSeek setHideandseek(HideAndSeek hideandseek, Logger logger) {
		logger.info("Setting HideAndSeek instance : " + hideandseek);
		Reference.hideandseek = hideandseek;
		return hideandseek;
	}

	public static HideAndSeek getHideandseek() {
		return hideandseek;
	}

	public static Logger getLogger() {
		return logger;
	}

	public static CommandRegistry getCommandregistry() {
		return commandregistry;
	}

	public static EnableHandler getEnablehandler() {
		return enablehandler;
	}

	public static DisableHandler getDisablehandler() {
		return disablehandler;
	}
	
	public static ConfigHandler getConfighandler() {
		return confighandler;
	}
}
