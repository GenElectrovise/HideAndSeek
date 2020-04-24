package net.clicksminuteper.HideAndSeek.main.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.clicksminuteper.HideAndSeek.main.Reference;
import net.clicksminuteper.HideAndSeek.main.game.Game;
import net.clicksminuteper.HideAndSeek.main.game.ThreeDCoordinate;

/**
 * Command for joining a game of HideAndSeek
 * @author GenElectrovise
 *
 */
public class CmdJoin implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		try {
			ThreeDCoordinate pos;
			Player player = (Player) sender;

			// If coordinates are not full
			if (args[0] != null & (args[1] == null | args[2] == null)) {
				return false;
			}

			// If no args
			if (args[0] == null) {
				// Pos is player's pos
				pos = new ThreeDCoordinate(player.getLocation().getX(), player.getLocation().getY(),
						player.getLocation().getZ());
				player.sendMessage("Joining from pos : " + pos);

				// If has args, try to set pos to args
			} else {
				// Pos is args > Coord
				pos = new ThreeDCoordinate(new Integer(args[0]), new Integer(args[1]), new Integer(args[2]));
			}

			Game nearestGame = Game.nearestGame(pos);
			if (nearestGame.canJoin) {
				nearestGame.addPlayer(player);
				player.sendMessage("Joining HideAndSeek!");
				return true;
			} else {
				player.sendMessage(
						"That game is not joinable right now! Games have a max length of 5 mins plus a 2 minute lobby waiting time, so your maximum wait for this game is 7 minutes! Sorry!");
			}
		} catch (Exception e) {
			Reference.getLogger().warning("Incorrect arguments for command '" + command.toString() + "' sent by "
					+ ((Player) sender).getDisplayName());
		}

		return false;
	}

}
