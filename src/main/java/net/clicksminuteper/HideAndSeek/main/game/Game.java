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

package net.clicksminuteper.HideAndSeek.main.game;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.clicksminuteper.HideAndSeek.main.Reference;
import net.clicksminuteper.HideAndSeek.main.citizens.trait.TraitGameController;
import net.clicksminuteper.HideAndSeek.main.game.block.BlockPalette;
import net.clicksminuteper.HideAndSeek.main.item.Constants;
import net.clicksminuteper.HideAndSeek.main.util.SeekLog;

public class Game {
	private TraitGameController gameController;
	public HashMap<String, PlayerData> players;
	public boolean joinable;
	public Location origin;
	public BlockPalette palette;
	public GameTimer timer;
	public GameConfig gameConfig;
	public GameState gameState;

	/**
	 * Runs and contains data for a {@link Game}. Controlled by a
	 * {@link TraitGameController}.
	 * 
	 * @param traitGameController The enclosing {@link TraitGameController} which
	 *                            owns this {@link Game}
	 */
	public Game(TraitGameController traitGameController) {
		this.gameController = traitGameController;
		this.players = new HashMap<String, PlayerData>();
		this.joinable = false;
		this.origin = new Location(gameController.getNPC().getEntity().getWorld(), gameController.getNPC().getEntity().getLocation().getX(), gameController.getNPC().getEntity().getLocation().getY(),
				gameController.getNPC().getEntity().getLocation().getZ());
		this.palette = traitGameController.getPalette();
		this.gameConfig = new GameConfig();
		this.timer = new GameTimer(gameConfig.GAME_DURATION + gameConfig.LOBBY_DURATION + gameConfig.FINISH_DURATION);
		this.gameState = GameState.UNINITIALISED;

		SeekLog.info("New Game created by NPC >> " + toString());
	}

	/**
	 * Ticks the next cycle of this Game
	 */
	public void tick() {
		SeekLog.debug("Ticking Game held by " + gameController);

		changeGameState();

		switch (gameState) {
		case UNINITIALISED:
			gameState = GameState.JOINING;
			setJoinable(false);
			break;

		case JOINING:
			setJoinable(true);
			joining();
			break;

		case PLAYING:
			setJoinable(false);
			break;

		case FINISHED:
			setJoinable(false);
			break;

		default:
			gameState = GameState.UNINITIALISED;
			setJoinable(false);
			break;
		}

		timer.decr();
	}

	/**
	 * Handles the <code>{@link GameState}.JOINING</code> phase of this Game
	 */
	private void joining() {
		for (PlayerData data : players.values()) {
			Player player = data.getHost();
			player.setLevel(timer.totalDuration - timer.timeRemaining);
		}
	}

	/**
	 * Updates the {@link GameState} of this {@link Game} based on the time
	 * remaining.
	 */
	private void changeGameState() {
		if (timer.timeRemaining > gameConfig.GAME_DURATION + gameConfig.FINISH_DURATION) {
			gameState = GameState.JOINING;
		} else if (timer.timeRemaining > gameConfig.FINISH_DURATION && timer.timeRemaining < timer.totalDuration - gameConfig.LOBBY_DURATION) {
			gameState = GameState.PLAYING;
		} else if (timer.timeRemaining < gameConfig.FINISH_DURATION && timer.timeRemaining > 0) {
			gameState = GameState.FINISHED;
		} else if (timer.timeRemaining == 0 || timer.timeRemaining < 0) {
			reset();
		} else {
			SeekLog.error("Unhandled case in changing state!" + timer);
		}
	}

	/**
	 * Resets this {@link Game} by removing all {@link Player}s and resetting the
	 * {@link GameTimer}
	 */
	private void reset() {
		SeekLog.info("Resetting Game " + this);
		for (String playerName : players.keySet()) {
			removePlayer(playerName);
		}
	}

	/**
	 * Adds a player to this Game.
	 * 
	 * @param player The player to add
	 * @return Whether the player was successfully added.
	 */
	public boolean addPlayer(Player player) {
		if (isJoinable()) {
			players.put(player.getName(), new PlayerData(player));
			return true;
		}
		return false;
	}

	/**
	 * Tries to remove the given Player from this Game.
	 * 
	 * @param player The Player to remove.
	 * @return Whether the Player was removed. (Returns false if the Player was not
	 *         in the Game)
	 */
	public boolean removePlayer(Player player) {
		return removePlayer(player.getName());
	}

	/**
	 * Tries to remove the given Player from this Game.
	 * 
	 * @param playerName The name of the Player to remove.
	 * @return Whether the Player of the given name was removed. (Returns false if
	 *         no Player was was found in in the Game with the given name)
	 */
	public boolean removePlayer(String playerName) {
		try {
			players.remove(playerName);

			if (gameState == GameState.PLAYING || gameState == GameState.FINISHED) {
				FileConfiguration config = Reference.getInstance().getHideAndSeek().getConfig();
				ConfigurationSection configSection = config.getConfigurationSection("externalLobbyLocation");
				Bukkit.getPlayer(playerName).getLocation().setWorld(Bukkit.getWorld(configSection.getString("worldName")));
				Bukkit.getPlayer(playerName).getLocation().setX(configSection.getInt("x"));
				Bukkit.getPlayer(playerName).getLocation().setY(configSection.getInt("y"));
				Bukkit.getPlayer(playerName).getLocation().setZ(configSection.getInt("z"));
			}

			return true;
		} catch (NullPointerException n) {
			SeekLog.warning("The player " + playerName + " does not exist in the Game at " + origin);
			SeekLog.debug("Extended error debug! >> " + this);
			return false;
		}
	}

	/**
	 * Destroys this Game totally and utterly.... <br>
	 * Well not really. And object can't really destroy itself, but it <i>can</i>
	 * prepare it content for their imminent demise! <br>
	 * In this case, it removes every Player from its memory and teleports them to
	 * the external lobby.
	 * 
	 * @return Whether the destruction was successful.
	 */
	public boolean destroy() {
		try {
			for (PlayerData data : players.values()) {
				Player player = data.getHost();
				teleportPlayerToExternalLobby(player);
				removePlayer(player);
			}
			return true;
		} catch (Exception e) {
			SeekLog.stacktrace(e.getStackTrace());
		}
		return false;
	}

	public void teleportPlayerToExternalLobby(Player player) {
		FileConfiguration config = Reference.getInstance().getHideAndSeek().getConfig();
		ConfigurationSection configSection = config.getConfigurationSection("externalLobbyLocation");
		player.getLocation().setWorld(Bukkit.getWorld(configSection.getString("worldName")));
		player.getLocation().setX(configSection.getInt("x"));
		player.getLocation().setY(configSection.getInt("y"));
		player.getLocation().setZ(configSection.getInt("z"));
	}

	@SuppressWarnings("unused")
	private void preparePlayers() {
		SeekLog.error("No player prep!");
	}

	/**
	 * Teleports {@link Player}s to the origin of this Game
	 */
	@SuppressWarnings("unused")
	private void teleportPlayersToStartLocation() {
		SeekLog.error("Teleportation not implemented");
	}

	/**
	 * Applies effects to seekers
	 * 
	 * @param data
	 */
	public void applyEffectsToSeeker(PlayerData data) {
		SeekLog.error("No seeker effects");
	}

	/**
	 * Applies effects to hiders
	 * 
	 * @param data
	 */
	public void applyEffectsToHider(PlayerData data) {
		SeekLog.error("No hider effects");
	}

	// Items

	/**
	 * Gives the seekers their items
	 * 
	 * @param data
	 */
	public void giveSeekerItems(PlayerData data) {
		SeekLog.info(data.getHost().getDisplayName() + " is a seeker! (Items)");
		ItemStack DIAMOND_SWORD = new ItemStack(Material.DIAMOND_SWORD);

		DIAMOND_SWORD.addEnchantment(Enchantment.DAMAGE_ALL, 2);
		DIAMOND_SWORD.addEnchantment(Enchantment.VANISHING_CURSE, 1);

		ItemMeta sword_meta = DIAMOND_SWORD.getItemMeta();
		sword_meta.setDisplayName("Hider Remover");

		data.getHost().getInventory().addItem(DIAMOND_SWORD);
	}

	/**
	 * Gives the hiders their items
	 * 
	 * @param data
	 */
	public void giveHiderItems(PlayerData data) {
		SeekLog.info(data.getHost().getDisplayName() + " is a seeker! (Items)");

		ItemStack bow = Constants.ItemConstants.BOW.itemStack;
		ItemStack arrows = Constants.ItemConstants.BOW.itemStack;
		ItemStack disguise = Constants.ItemConstants.BOW.itemStack;
		ItemStack undisguise = Constants.ItemConstants.BOW.itemStack;

		bow.setItemMeta(Constants.Metadata.hiderBow());
		arrows.setItemMeta(Constants.Metadata.hiderArrows());
		disguise.setItemMeta(Constants.Metadata.hiderDisguise());
		undisguise.setItemMeta(Constants.Metadata.hiderUndiguise());

		data.getHost().getInventory().addItem(bow);
		data.getHost().getInventory().addItem(arrows);
		data.getHost().getInventory().addItem(disguise);
		data.getHost().getInventory().addItem(undisguise);
	}

	// toString

	/**
	 * 
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Game{");

		builder.append(" Origin{" + origin + "}");

		builder.append(" Players[");
		for (PlayerData data : players.values()) {
			builder.append(data.getHost().getDisplayName() + ", ");
		}
		builder.append("]");
		builder.append(" " + palette);
		builder.append(" canJoin:" + joinable);

		builder.append("}");
		return builder.toString();
	}

	// Getters

	/**
	 * @return the gameConfig
	 */
	public GameConfig getGameConfig() {
		return gameConfig;
	}

	/**
	 * @return the gameController
	 */
	public TraitGameController getGameController() {
		return gameController;
	}

	/**
	 * @return the gameState
	 */
	public GameState getGameState() {
		return gameState;
	}

	/**
	 * @return the joinable
	 */
	public boolean isJoinable() {
		return joinable;
	}

	/**
	 * @return the origin
	 */
	public Location getOrigin() {
		return origin;
	}

	/**
	 * @return the palette
	 */
	public BlockPalette getPalette() {
		return palette;
	}

	/**
	 * @return the players
	 */
	public HashMap<String, PlayerData> getPlayers() {
		return players;
	}

	/**
	 * @return the timer
	 */
	public GameTimer getTimer() {
		return timer;
	}

	// Setters

	/**
	 * @param gameConfig the gameConfig to set
	 */
	public void setGameConfig(GameConfig gameConfig) {
		this.gameConfig = gameConfig;
	}

	/**
	 * @param gameController the gameController to set
	 */
	public void setGameController(TraitGameController gameController) {
		this.gameController = gameController;
	}

	/**
	 * @param gameState the gameState to set
	 */
	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}

	/**
	 * @param joinable the joinable to set
	 */
	public void setJoinable(boolean joinable) {
		this.joinable = joinable;
	}

	/**
	 * @param origin the origin to set
	 */
	public void setOrigin(Location origin) {
		this.origin = origin;
	}

	/**
	 * @param palette the palette to set
	 */
	public void setPalette(BlockPalette palette) {
		this.palette = palette;
	}

	/**
	 * @param players the players to set
	 */
	public void setPlayers(HashMap<String, PlayerData> players) {
		this.players = players;
	}

	/**
	 * @param timer the timer to set
	 */
	public void setTimer(GameTimer timer) {
		this.timer = timer;
	}

	// Subclasses

	/**
	 * Doesn't really actually do timing, but handles the time remaining for the
	 * enclosing Game.
	 * 
	 * @author GenElectrovise
	 *
	 */
	public class GameTimer {
		int totalDuration;
		int timeRemaining;

		public GameTimer(int totalDuration) {
			this.totalDuration = totalDuration;
		}

		/**
		 * @param totalDuration the totalDuration to set
		 */
		public void setTotalDuration(int totalDuration) {
			this.totalDuration = totalDuration;
		}

		/**
		 * @param timeRemaining the timeRemaining to set
		 */
		public void setTimeRemaining(int timeRemaining) {
			this.timeRemaining = timeRemaining;
		}

		/**
		 * Resets the remaining time to the totalDuration.
		 * 
		 * @return The remaining time on this {@link GameTimer}
		 */
		public int reset() {
			timeRemaining = totalDuration;
			return timeRemaining;
		}

		/**
		 * Decrements the timeRemaining.
		 * 
		 * @return The timeRemaining after the decrement.
		 */
		public int decr() {
			return timeRemaining--;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("GameTimer{");

			builder.append("totalDuration:" + totalDuration);
			builder.append(" timeRemaining:" + timeRemaining);

			builder.append("}");
			return builder.toString();
		}
	}

	/**
	 * Represents the current stage of game-play.
	 * 
	 * @author GenElectrovise
	 *
	 */
	public enum GameState {
		UNINITIALISED, JOINING, PLAYING, FINISHED;
	}
}
