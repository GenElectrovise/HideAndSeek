/**
 * HideAndSeek -- A Hide and Seek plugin for Bukkit and Spigot
 *  Copyright (C) 2020 GenElectrovise
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package net.clicksminuteper.HideAndSeek.main.citizens.trait;

import org.bukkit.event.EventHandler;
import org.bukkit.plugin.java.JavaPlugin;

import net.citizensnpcs.api.event.DespawnReason;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.trait.Trait;
import net.clicksminuteper.HideAndSeek.main.HideAndSeek;
import net.clicksminuteper.HideAndSeek.main.event.HideAndSeekTickGamesEvent;
import net.clicksminuteper.HideAndSeek.main.game.Game;
import net.clicksminuteper.HideAndSeek.main.game.Games;
import net.clicksminuteper.HideAndSeek.main.util.SeekLog;

/**
 * @author GenElectrovise
 *
 */
public class TraitGameController extends Trait {

	private Game game;
	private JavaPlugin plugin;

	public TraitGameController() {
		super("hns_gamecontroller");
		this.game = new Game(this);
		this.plugin = JavaPlugin.getPlugin(HideAndSeek.class);
	}

	/**
	 * @return the game
	 */
	public Game getGame() {
		return game;
	}

	@EventHandler
	public void listenForGameTick(HideAndSeekTickGamesEvent event) {
		SeekLog.debug("Game controller heard TickGamesEvent!");
	}

	/**
	 * @return
	 */
	public boolean destroy() {
		try {
			Games.destroyGameObj(this);
			getNPC().despawn(DespawnReason.REMOVAL);
			getNPC().destroy();
			return true;
		} catch (Exception e) {
			SeekLog.error("Error whilst destroying Game : " + getGame());
			SeekLog.exception(e);
			return false;
		}
	}

	/**
	 * Automatically registered as a Listener. Handle a click on this NPC.
	 * 
	 * @param event
	 */
	@EventHandler
	public void click(NPCRightClickEvent event) {
		if (event.getNPC() == this.getNPC()) {
			event.getClicker().sendMessage("Oops! Looks like the game mechanics aren't implemented yet! Come back next version!");
		}

	}

	// Called every tick
	@Override
	public void run() {
		SeekLog.debug("Game controller running...");
	}

	/**
	 * Run code when your trait is attached to a NPC. This is called BEFORE onSpawn,
	 * so npc.getBukkitEntity() will return null This would be a good place to load
	 * configurable defaults for new NPCs.
	 */
	@Override
	public void onAttach() {
		plugin.getServer().getLogger().info(npc.getName() + " has been assigned Nitwit!");
	}

	/**
	 * Run code when the NPC is despawned. This is called before the entity actually
	 * despawns so npc.getBukkitEntity() is still valid.
	 */
	@Override
	public void onDespawn() {
		SeekLog.info("Game Controller has despawned");
	}

	/**
	 * Run code when the NPC is spawned. Note that npc.getBukkitEntity() will be
	 * null until this method is called. This is called AFTER onAttach and AFTER
	 * Load when the server is started.
	 */
	@Override
	public void onSpawn() {
		this.game = new Game(this);
		SeekLog.info("Game Controller has spawned");
	}

	/**
	 * Called on removal. Use to destroy any repeating tasks.
	 */
	@Override
	public void onRemove() {
		SeekLog.info("Game Controller removed");
	}
}
