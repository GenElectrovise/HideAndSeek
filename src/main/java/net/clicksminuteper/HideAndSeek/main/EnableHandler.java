/*
 * HideAndSeek -- A Hide and Seek plugin for Bukkit and Spigot
    Copyright (C) 2020 GenElectrovise

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package net.clicksminuteper.HideAndSeek.main;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.trait.TraitInfo;
import net.clicksminuteper.HideAndSeek.main.citizens.trait.TraitGameController;
import net.clicksminuteper.HideAndSeek.main.citizens.trait.TraitNitwit;
import net.clicksminuteper.HideAndSeek.main.command.CommandRegistry;
import net.clicksminuteper.HideAndSeek.main.game.Games;
import net.clicksminuteper.HideAndSeek.main.game.block.Palettes;
import net.clicksminuteper.HideAndSeek.main.util.SeekLog;

/**
 * Handles the enabling and disabling of the HideAndSeek plugin
 * 
 * @author GenElectrovise
 *
 */
public class EnableHandler {

	public void handleEnable() {
		SeekLog.info("HideAndSeek.onEnable() : HideAndSeek Plugin is enabled ...");

		// Generate BlockPalettes from config
		Palettes.generatePalettes();

		// Ensure Citizens is properly enabled, then registers Traits.
		citizens();

		// Add commands
		CommandRegistry.prepare();

		// Start the game ticking cycle
		activeGameTickingCycle();
	}

	/**
	 * Starts the event cycle to tick Game-s
	 */
	private void activeGameTickingCycle() {
		// Create the HideAndSeekTickGamesEvent cycle, then start after 200 game ticks
		// (20 seconds)
		BukkitRunnable startTickCycle = new BukkitRunnable() {
			@Override
			public void run() {
				Games.createGamesTickCycle();
			}
		};
		SeekLog.debug("Created repeatable task : " + startTickCycle);
		startTickCycle.runTaskLater(Reference.getInstance().getHideAndSeek(), 200L);
		SeekLog.debug("Running game tick cycle!");
	}

	/**
	 * Ensures Citizens is in a working state
	 */
	private void citizens() {
		if (Bukkit.getServer().getPluginManager().getPlugin("Citizens") == null || Bukkit.getServer().getPluginManager().getPlugin("Citizens").isEnabled() == false) {
			SeekLog.error("Citizens 2.0 not found or not enabled");
			Bukkit.getServer().getPluginManager().disablePlugin(Reference.getInstance().getHideAndSeek());
			return;
		}

		// Register your trait with Citizens.
		CitizensAPI.getTraitFactory().registerTrait(TraitInfo.create(TraitNitwit.class).withName("hns_nitwit"));
		CitizensAPI.getTraitFactory().registerTrait(TraitInfo.create(TraitGameController.class).withName("hns_gamecontroller"));
	}

	public void handleDisable() {
		SeekLog.info("HideAndSeek.onDisable() : HideAndSeek Plugin has been disabled ...");
	}
}
