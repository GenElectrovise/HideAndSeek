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

package net.clicksminuteper.HideAndSeek.main.enable;

import java.util.logging.Logger;

import org.bukkit.Bukkit;

import net.clicksminuteper.HideAndSeek.main.Reference;
import net.clicksminuteper.HideAndSeek.main.citizens.trait.TraitNitwit;
import net.clicksminuteper.HideAndSeek.main.game.block.Palettes;
import net.clicksminuteper.HideAndSeek.main.util.SeekLog;

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
		SeekLog.info("HideAndSeek.onEnable() : HideAndSeek Plugin is enabled ...");

		Palettes.generatePalettes();

		citizens();

	}

	private void citizens() {
		if (Bukkit.getServer().getPluginManager().getPlugin("Citizens") == null
				|| Bukkit.getServer().getPluginManager().getPlugin("Citizens").isEnabled() == false) {
			SeekLog.error("Citizens 2.0 not found or not enabled");
			Bukkit.getServer().getPluginManager().disablePlugin(Reference.getHideandseek());
			return;
		}

		// Register your trait with Citizens.
		net.citizensnpcs.api.CitizensAPI.getTraitFactory().registerTrait(
				net.citizensnpcs.api.trait.TraitInfo.create(TraitNitwit.class).withName("hns_gamecontroller"));
	}

}
