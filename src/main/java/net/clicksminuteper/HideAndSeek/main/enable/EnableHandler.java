package net.clicksminuteper.HideAndSeek.main.enable;

import java.util.logging.Logger;

import net.clicksminuteper.HideAndSeek.main.game.Palettes;

/**
 * Handles the enabling of the HideAndSeek plugin
 * 
 * @author GenElectrovise
 *
 */
public class EnableHandler {
	private Logger logger;

	public EnableHandler(Logger logger) {
		this.logger = logger;
	}

	public void handleEnable() {
		logger.info("HideAndSeek.onEnable() : HideAndSeek Plugin is enabled ...");
		
		Palettes.generatePalettes();
	}

}
