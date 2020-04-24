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
import net.clicksminuteper.HideAndSeek.main.game.ItemDisguiseListener;
import net.clicksminuteper.HideAndSeek.main.game.ItemUndisguiseListener;
import net.clicksminuteper.HideAndSeek.main.game.PlayerDeathListener;

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
