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

import org.bukkit.command.CommandExecutor;

import net.clicksminuteper.HideAndSeek.main.Reference;
import net.clicksminuteper.HideAndSeek.main.util.SeekLog;

public class CommandRegistry {

	public static HashMap<String, CommandExecutor> commandsIn = new HashMap<String, CommandExecutor>();

	/**
	 * Registers all listed commands from the <i>commmandsIn</i> HashMap
	 */
	public static void registerAllListed() {
		for (String name : commandsIn.keySet()) {
			Reference.getInstance().getHideAndSeek().getCommand(name).setExecutor(commandsIn.get(name));
			SeekLog.info("Registering HideAndSeek commmand with name : " + name);
		}
	}

	public static void addToRegister(String name, CommandExecutor executor) {
		commandsIn.put(name, executor);
	}

	public static void prepare() {
		CommandRegistry.addToRegister("newgame", new CmdNewGame());
		CommandRegistry.addToRegister("join", new CmdJoin());
		CommandRegistry.addToRegister("destroygame", new CmdDestroyGame());
		CommandRegistry.addToRegister("listgames", new CmdListGames());
		CommandRegistry.addToRegister("nitwit", new CmdNitwit());

		CommandRegistry.registerAllListed();
	}

}
