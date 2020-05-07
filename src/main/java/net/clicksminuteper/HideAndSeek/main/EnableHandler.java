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
import net.clicksminuteper.HideAndSeek.main.citizens.trait.TraitGameControllerInventory;
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
		SeekLog.info("HideAndSeek plugin enabling with debug logging : " + (SeekLog.debugEnabled ? "on" : "off") + " (" + SeekLog.debugEnabled + ")");

		// Generate BlockPalettes from config
		Palettes.generatePalettes();

		// Ensure Citizens is properly enabled, then registers Traits.
		citizens();

		// Add GameControllers
		loadGameControllersFromConfig();

		// Add commands
		CommandRegistry.prepare();

		// Start the game ticking cycle
		activeGameTickingCycle();
	}

	/**
	 * Reads config.yml to find active GameControllers in the world.
	 */
	private void loadGameControllersFromConfig() {
		/*
		 * ArrayList<UUID> uuids = new ArrayList<UUID>(); ArrayList<NPC> npcs = new
		 * ArrayList<NPC>();
		 * 
		 * SeekLog.debug("Loading game controllers...");
		 * 
		 * // Get all entities with the NPC metadata tag for (World world :
		 * Bukkit.getWorlds()) { SeekLog.debug("World : " + world.getName());
		 * 
		 * for (Entity entity : world.getEntities()) { SeekLog.debug(" - " +
		 * entity.getName());
		 * 
		 * if (entity.hasMetadata("NPC")) { SeekLog.debug("Entity had metadata NPC : " +
		 * entity.getLocation()); SeekLog.debug("Added UUID : " + entity.getUniqueId());
		 * uuids.add(entity.getUniqueId()); } } }
		 * 
		 * // For every NPCRegistry, add any UUIDs contained in it belonging to NPCs for
		 * (NPCRegistry registry : CitizensAPI.getNPCRegistries()) {
		 * SeekLog.debug("Registry : " + registry.toString());
		 * 
		 * for (UUID uuid : uuids) { SeekLog.debug("UUID : " + uuid);
		 * 
		 * if (registry.getByUniqueId(uuid) != null) { NPC npc =
		 * registry.getByUniqueId(uuid); SeekLog.debug("NPC : " + npc.getFullName() +
		 * " at " + npc.getEntity().getLocation());
		 * 
		 * if (npc.hasTrait(TraitGameController.class)) { npcs.add(npc);
		 * SeekLog.debug("Added game controller at " + npc.getEntity().getLocation()); }
		 * else { SeekLog.debug("Did not add npc at " + npc.getEntity().getLocation());
		 * } } } }
		 */
		
		
		
		/*
		 * SeekLog.info("Finding game controllers..."); FileConfiguration config =
		 * Reference.getInstance().getHideAndSeek().getConfig();
		 * 
		 * ConfigurationSection games = config.getConfigurationSection("games");
		 * 
		 * for (String gameName : games.getKeys(false)) { ConfigurationSection
		 * gameSection = games.getConfigurationSection(gameName);
		 * 
		 * String worldName = gameSection.getString("worldName");
		 * SeekLog.debug(" - worldName : " + worldName); Integer x =
		 * gameSection.getInt("x"); SeekLog.debug(" - x : " + x); Integer y =
		 * gameSection.getInt("y"); SeekLog.debug(" - y : " + y); Integer z =
		 * gameSection.getInt("z"); SeekLog.debug(" - z : " + z); String palette =
		 * gameSection.getString("palette"); SeekLog.debug(" - palette : " + palette);
		 * 
		 * Games.newGame(new Location(Bukkit.getWorld(worldName), x, y, z), palette); }
		 */

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
		SeekLog.debug("Will begin GamesTickCycle in 20 seconds");
		startTickCycle.runTaskLater(Reference.getInstance().getHideAndSeek(), 200L);
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
		CitizensAPI.getTraitFactory().registerTrait(TraitInfo.create(TraitGameControllerInventory.class).withName("hns_gamecontroller-inventory"));
	}

	public void handleDisable() {
		SeekLog.info("HideAndSeek.onDisable() : HideAndSeek Plugin has been disabled ...");

		/*
		 * // Save GameController locations for (NPC npc : Games.ACTIVE_GAMES) {
		 * SeekLog.debug("Saving game controller at " + npc.getEntity().getLocation());
		 * String gameName = npc.getTrait(TraitGameController.class).getGameName();
		 * String worldName = npc.getEntity().getLocation().getWorld().getName();
		 * Integer x = npc.getEntity().getLocation().getBlockX(); Integer y =
		 * npc.getEntity().getLocation().getBlockX(); Integer z =
		 * npc.getEntity().getLocation().getBlockX(); String palette =
		 * npc.getEntity().getLocation().getWorld().getName();
		 * 
		 * FileConfiguration config =
		 * Reference.getInstance().getHideAndSeek().getConfig(); config.set("games." +
		 * gameName + ".worldName", worldName); config.set("games." + gameName + ".x",
		 * x); config.set("games." + gameName + ".y", y); config.set("games." + gameName
		 * + ".z", z); config.set("games." + gameName + ".palette", palette);
		 * 
		 * SeekLog.debug(" - worldName : " + worldName); SeekLog.debug(" - x : " + x);
		 * SeekLog.debug(" - y : " + y); SeekLog.debug(" - z : " + z);
		 * SeekLog.debug(" - palette : " + palette); }
		 * 
		 * // Destroy all Games if (Games.ACTIVE_GAMES.size() > 0) { for (NPC npc :
		 * Games.ACTIVE_GAMES) { npc.destroy(); } }
		 */
	}
}
