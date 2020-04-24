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
