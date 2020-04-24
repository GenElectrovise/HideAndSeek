package net.clicksminuteper.HideAndSeek.main.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.clicksminuteper.HideAndSeek.main.game.Game;
import net.clicksminuteper.HideAndSeek.main.game.ThreeDCoordinate;

/**
 * Destroys the Game at the given coordinates, or the nearest Game to those if a
 * Game is not present at those coordinates
 * 
 * @author GenElectrovise
 *
 */
public class CmdDestroyGame implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		int gameX;
		int gameY;
		int gameZ;

		try {
			gameX = new Integer(args[0]);
			gameY = new Integer(args[1]);
			gameZ = new Integer(args[2]);
		} catch (ClassCastException c) {
			sender.sendMessage("Incorrect args for command!");
			return false;
		}

		if (Game.nearestGame(new ThreeDCoordinate(gameX, gameY, gameZ)).destroy())
			return true;

		return false;
	}

}
