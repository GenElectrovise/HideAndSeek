package net.clicksminuteper.HideAndSeek.main.enable;

import java.util.logging.Logger;

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

		/*
		 * // For storing data on players HashMap<String, Integer> playerList = new
		 * HashMap<String, Integer>(); for (Player player :
		 * Bukkit.getServer().getOnlinePlayers()) { playerList.put(player.getName(),
		 * playerData(player)); }
		 */
	}

}
