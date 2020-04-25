package net.clicksminuteper.HideAndSeek.main.disguise;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

/**
 * Handles dispatching commands relating to Lib's Disguises
 *
 */
public class LibsInterface {
	public static boolean cmdDisguise(Logger logger, CommandSender sender, String blockType) {
		logger.info("ARGS == " + sender.getName() + " : " + blockType);

		return Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(),
				concatDisguisePlayerToFallingBlockCmd(logger, sender.getName(), blockType));
	}

	private static String concatDisguisePlayerToFallingBlockCmd(Logger logger, String playerName, String blockName) {
		String out = "disguiseplayer " + playerName + " FALLING_BLOCK " + blockName.toUpperCase();
		logger.info(out);
		return out;
	}

	public static boolean cmdUndisguise(Logger logger, CommandSender sender) {
		logger.info("ARGS == " + sender.getName());

		return Bukkit.dispatchCommand(sender, "undisguiseplayer " + sender.getName());
	}
}
