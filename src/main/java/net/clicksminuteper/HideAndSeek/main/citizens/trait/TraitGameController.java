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

import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import net.citizensnpcs.api.event.DespawnReason;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.trait.Trait;
import net.citizensnpcs.api.trait.trait.Inventory;
import net.clicksminuteper.HideAndSeek.main.HideAndSeek;
import net.clicksminuteper.HideAndSeek.main.event.HideAndSeekTickGamesEvent;
import net.clicksminuteper.HideAndSeek.main.game.Game;
import net.clicksminuteper.HideAndSeek.main.game.Game.GameState;
import net.clicksminuteper.HideAndSeek.main.game.Games;
import net.clicksminuteper.HideAndSeek.main.game.block.BlockPalette;
import net.clicksminuteper.HideAndSeek.main.util.SeekLog;

/**
 * @author GenElectrovise
 *
 */
public class TraitGameController extends Trait {

	private Game game;
	private JavaPlugin plugin;
	private BlockPalette palette;
	private Inventory inventory;

	public TraitGameController() {
		super("hns_gamecontroller");
		this.plugin = JavaPlugin.getPlugin(HideAndSeek.class);
	}

	/**
	 * @return the game
	 */
	public Game getGame() {
		return game;
	}

	/**
	 * Listens for a {@link HideAndSeekTickGamesEvent}. When heard, ticks this
	 * {@link TraitGameController}'s {@link Game}
	 * 
	 * @param event
	 */
	@EventHandler
	public void listenForGameTick(HideAndSeekTickGamesEvent event) {
		SeekLog.debug("Game controller heard TickGamesEvent!");
		if (hasGame()) {
			game.tick();
		}
	}

	/**
	 * @return Whether this {@link TraitGameController} has a {@link Game}
	 */
	private boolean hasGame() {
		if (getGame() != null) {
			return true;
		}
		return false;
	}

	/**
	 * Destroys all assets for this {@link TraitGameController}, including its host
	 * {@link NPC} and contained {@link Game}.
	 * 
	 * @return Whether the destruction was successful.
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
			SeekLog.debug("Clicked GameController (opening inventory on " + event.getClicker() + "): " + this);
			inventory.openInventory(event.getClicker());
		}

	}

	@EventHandler
	public void playerDisconnected(PlayerQuitEvent event) {
		if (game.getPlayers().containsKey(event.getPlayer().getName())) {
			SeekLog.info("Player " + event.getPlayer().getName() + " disconnected so is being removed from the Game at " + getNPC().getEntity().getLocation() + "! Why didn't they want to play....?");
			game.removePlayer(event.getPlayer().getName());
			game.teleportPlayerToExternalLobby(event.getPlayer());
		}
	}

	// Called every tick
	@Override
	public void run() {
		List<Entity> nearbyEntities = getNPC().getEntity().getNearbyEntities(5, 5, 5);
		getNPC().faceLocation(nearbyEntities.get(0).getLocation());
	}

	public void prepareInventoryByState(GameState state) {
		SeekLog.debug("Preparing inventory with GameState " + state);
		switch (state) {
		case UNINITIALISED:
			SeekLog.debug("Preparing inventory " + state);
			this.inventory = TraitGameControllerInventory.generateUninitialisedInventory(this.inventory);
			break;
		case JOINING:
			SeekLog.debug("Preparing inventory " + state);
			this.inventory = TraitGameControllerInventory.generateJoiningInventory(this.inventory);
			break;
		case PLAYING:
			SeekLog.debug("Preparing inventory " + state);
			this.inventory = TraitGameControllerInventory.generatePlayingInventory(this.inventory);
			break;
		case FINISHED:
			SeekLog.debug("Preparing inventory " + state);
			this.inventory = TraitGameControllerInventory.generateFinishedInventory(this.inventory);
			break;
		default:
			SeekLog.error("Unhandled case whilst preparing inventory : " + state);
			break;
		}
	}

	/**
	 * Run code when your trait is attached to a NPC. This is called BEFORE onSpawn,
	 * so npc.getBukkitEntity() will return null This would be a good place to load
	 * configurable defaults for new NPCs.
	 */
	@Override
	public void onAttach() {
		SeekLog.debug(npc.getName() + " has been assigned TraitGameController!");
	}

	/**
	 * Run code when the NPC is despawned. This is called before the entity actually
	 * despawns so npc.getBukkitEntity() is still valid.
	 */
	@Override
	public void onDespawn() {
		SeekLog.info("Game Controller has despawned");
		game.destroy();
		this.game = null;
	}

	/**
	 * Run code when the NPC is spawned. Note that npc.getBukkitEntity() will be
	 * null until this method is called. This is called AFTER onAttach and AFTER
	 * Load when the server is started.
	 */
	@Override
	public void onSpawn() {
		this.game = new Game(this);
		this.getNPC().addTrait(TraitGameControllerInventory.class);
		this.inventory = this.getNPC().getTrait(TraitGameControllerInventory.class);
		SeekLog.info("NPC with TraitGameController has spawned. New Game attached and inventory created.");
	}

	/**
	 * Called on removal. Use to destroy any repeating tasks.
	 */
	@Override
	public void onRemove() {
		game.destroy();
		this.game = null;
		SeekLog.info("Game Controller removed");
	}

	@Override
	public String toString() {
		return "//TODO! New toString() for TraitGameController" + super.toString();
	}

	// getter

	/**
	 * @return the palette
	 */
	public BlockPalette getPalette() {
		return palette;
	}

	/**
	 * @return
	 */
	public Inventory getInventory() {
		return inventory;
	}

	/**
	 * @return the plugin
	 */
	public JavaPlugin getPlugin() {
		return plugin;
	}

	// setter

	/**
	 * @param palette the palette to set
	 */
	public void setPalette(BlockPalette palette) {
		this.palette = palette;
	}

	/**
	 * @param game the game to set
	 */
	public void setGame(Game game) {
		this.game = game;
	}

	/**
	 * @param plugin the plugin to set
	 */
	public void setPlugin(JavaPlugin plugin) {
		this.plugin = plugin;
	}

	/**
	 * @param inventory the inventory to set
	 */
	public void setInventory(Inventory i) {
		this.inventory = i;
	}
}
