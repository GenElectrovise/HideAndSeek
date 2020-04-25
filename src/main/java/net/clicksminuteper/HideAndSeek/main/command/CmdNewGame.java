package net.clicksminuteper.HideAndSeek.main.command;

import java.util.logging.Logger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.clicksminuteper.HideAndSeek.main.Reference;
import net.clicksminuteper.HideAndSeek.main.game.BlockPalette;
import net.clicksminuteper.HideAndSeek.main.game.Game;
import net.clicksminuteper.HideAndSeek.main.game.Palettes;
import net.clicksminuteper.HideAndSeek.main.game.ThreeDCoordinate;

public class CmdNewGame implements CommandExecutor {
	private Logger logger;

	public CmdNewGame(Logger logger) {
		this.logger = logger;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length != 2 && !(sender instanceof Player)) {
			return false;
		}
		if (new Integer(args[0]) == null || new Integer(args[1]) == null) {
			return false;
		}

		BlockPalette palette = Palettes.getPalette(args[3]);
		Reference.getLogger().info("Palette name: " + args[3]);
		Reference.getLogger().info("Palette object: " + palette);

		if (args[0] == null && sender instanceof Player) {
			Player player = (Player) sender;

			double dGameX = player.getLocation().getX();
			double dGameY = player.getLocation().getY();
			double dGameZ = player.getLocation().getZ();
			int iGameX = (int) Math.floor(dGameX);
			int iGameY = (int) Math.floor(dGameY);
			int iGameZ = (int) Math.floor(dGameZ);

			logger.info(
					player.getDisplayName() + " has created a new game of HideAndSeek at : " + iGameX + "," + iGameZ);

			Game game = Game.newGame(new Game(logger, new ThreeDCoordinate(iGameX, iGameY, iGameZ), palette));
			game.run();
			
			player.sendMessage("New HideAndSeek game created! " + game);
			return true;
		} else {
			Integer iGameX = new Integer(args[0]);
			Integer iGameY = new Integer(args[1]);
			Integer iGameZ = new Integer(args[2]);

			logger.info("Creating new game of HideAndSeek at : " + iGameX + "," + iGameZ);
			logger.info("Logger : " + logger);
			logger.info("HideAndSeek : " + Reference.getHideandseek());
			logger.info("2dCoord : " + new ThreeDCoordinate(iGameX, iGameY, iGameZ));

			Logger loggerIn = logger;
			ThreeDCoordinate coordIn = new ThreeDCoordinate(iGameX, iGameY, iGameZ);

			Game game = new Game(loggerIn, coordIn, palette);
			Game.newGame(game);
			game.run();
			return true;
		}
	}

}
