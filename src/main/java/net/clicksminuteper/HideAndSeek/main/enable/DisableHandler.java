package net.clicksminuteper.HideAndSeek.main.enable;

import java.util.logging.Logger;

/**
 * Handles the disabling of the HideAndSeek plugin
 * @author GenElectrovise
 *
 */
public class DisableHandler {
	private Logger logger;
	
	public DisableHandler(Logger logger) {
		this.logger = logger;
	}

	public void handleDisable() {
		logger.info("HideAndSeek.onDisable() : HideAndSeek Plugin has been disabled ...");
	}

}
