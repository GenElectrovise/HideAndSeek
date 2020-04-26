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

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import net.clicksminuteper.HideAndSeek.main.command.CmdDestroyGame;
import net.clicksminuteper.HideAndSeek.main.command.CmdDisguise;
import net.clicksminuteper.HideAndSeek.main.command.CmdJoin;
import net.clicksminuteper.HideAndSeek.main.command.CmdNewGame;
import net.clicksminuteper.HideAndSeek.main.command.CommandRegistry;
import net.clicksminuteper.HideAndSeek.main.enable.DisableHandler;
import net.clicksminuteper.HideAndSeek.main.enable.EnableHandler;
import net.clicksminuteper.HideAndSeek.main.game.listener.ItemDisguiseListener;
import net.clicksminuteper.HideAndSeek.main.game.listener.ItemUndisguiseListener;
import net.clicksminuteper.HideAndSeek.main.game.listener.PlayerDeathListener;

public final class HideAndSeek extends JavaPlugin {
	private EnableHandler enablehandler;
	private DisableHandler disablehandler;
	private CommandRegistry commandregistry;

	@Override
	public void onEnable() {
		Reference.setHideandseek(this, getLogger());
		Reference.set();

		enablehandler = Reference.getEnablehandler();
		disablehandler = Reference.getDisablehandler();
		commandregistry = Reference.getCommandregistry();

		getLogger().warning("Enable handler : " + enablehandler);
		getLogger().warning("Disable handler : " + disablehandler);
		getLogger().warning("Command Registry : " + commandregistry);
		getLogger().warning("HideAndSeek : " + Reference.getHideandseek());
		getLogger().warning("Logger : " + Reference.getLogger());

		enablehandler.handleEnable();

		commandregistry.addToRegister("hnstest", new CmdDisguise(getLogger()));
		commandregistry.addToRegister("newgame", new CmdNewGame(getLogger()));
		commandregistry.addToRegister("join", new CmdJoin());
		commandregistry.addToRegister("destroygame", new CmdDestroyGame());

		commandregistry.registerAllListed();

		Listener itemDisguiseListener = new ItemDisguiseListener();
		Bukkit.getPluginManager().registerEvents(itemDisguiseListener, this);
		Listener itemUndisguiseListener = new ItemUndisguiseListener();
		Bukkit.getPluginManager().registerEvents(itemUndisguiseListener, this);
		Listener playerDeathListener = new PlayerDeathListener();
		Bukkit.getPluginManager().registerEvents(playerDeathListener, this);
		
		saveDefaultConfig();
	}

	@Override
	public void onDisable() {
		disablehandler.handleDisable();
	}

	public CommandRegistry getCommandregistry() {
		return commandregistry;
	}
}
