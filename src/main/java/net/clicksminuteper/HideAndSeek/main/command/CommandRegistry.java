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
