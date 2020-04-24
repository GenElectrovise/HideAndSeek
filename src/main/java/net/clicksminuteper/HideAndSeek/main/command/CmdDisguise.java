package net.clicksminuteper.HideAndSeek.main.command;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.clicksminuteper.HideAndSeek.main.disguise.LibsInterface;

public class CmdDisguise implements CommandExecutor {
	private Logger logger;

	public CmdDisguise(Logger logger) {
		this.logger = logger;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player target;

		try {

			// If player does not exist with given name, return false, else set target
			if (Bukkit.getPlayer(args[0]) == null) {
				return false;
			} else {
				target = Bukkit.getPlayer(args[0]);
				logger.info("Target : " + target.getDisplayName());
			}

			logger.info("Block Type : " + args[1]);
			// If disguise cmd succeeds, return true
			if (LibsInterface.cmdDisguise(logger, target, args[1])) {
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

}
