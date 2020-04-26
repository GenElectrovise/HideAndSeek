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

package net.clicksminuteper.HideAndSeek.main.command;

import java.util.HashMap;
import java.util.logging.Logger;

import org.bukkit.command.CommandExecutor;

import net.clicksminuteper.HideAndSeek.main.HideAndSeek;

public class CommandRegistry {

	public static HashMap<String, CommandExecutor> commandsIn = new HashMap<String, CommandExecutor>();
	public Logger logger;
	public HideAndSeek hideandseek;

	public CommandRegistry(Logger logger, HideAndSeek hideandseek) {
		this.logger = logger;
		this.hideandseek = hideandseek;
	}

	/**
	 * Registers all listed commands from the <i>commmandsIn</i> HashMap
	 */
	public void registerAllListed() {
		for (String name : commandsIn.keySet()) {
			hideandseek.getCommand(name).setExecutor(commandsIn.get(name));
			logger.info("Registering HideAndSeek commmand with name : " + name);
		}
	}
	
	public void addToRegister(String name, CommandExecutor executor) {
		commandsIn.put(name, executor);
	}
	
	public HideAndSeek getHideandseek() {
		return hideandseek;
	}

}
