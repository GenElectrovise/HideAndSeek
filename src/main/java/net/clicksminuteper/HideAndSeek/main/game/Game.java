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
		this.setGameState(GameState.UNINITIALISED);

		SeekLog.debug("Game " + this);
		SeekLog.debug("Game Controller " + gameController);
		SeekLog.debug("Players " + players);
		SeekLog.debug("Joinable? " + false);
		SeekLog.debug("Origin " + origin);
		SeekLog.debug("BlockPalette " + palette);
		SeekLog.debug("GameTimer " + timer);
		SeekLog.debug("GameConfig " + gameConfig);
		SeekLog.debug("GameState " + gameState);

		SeekLog.info("New Game created by NPC: " + toString());
	}

	/**
	 * Ticks the next cycle of this Game
	 */
	public void tick() {
		SeekLog.debug("Ticking Game held by " + gameController);

		changeGameState();

		switch (gameState) {
		case UNINITIALISED:
			SeekLog.debug("GameState == UNINITIALIED > JOINING");
			setGameState(GameState.JOINING);
			setJoinable(false);
			gameController.prepareInventoryByState(gameState);
			break;

		case JOINING:
			SeekLog.debug("GameState == JOINING");
			setJoinable(true);
			joining();
			gameController.prepareInventoryByState(gameState);
			break;

		case PLAYING:
			SeekLog.debug("GameState == PLAYING");
			setJoinable(false);
			playing();
			gameController.prepareInventoryByState(gameState);
			break;

		case FINISHED:
			SeekLog.debug("GameState == FINISHED");
			setJoinable(false);
			finished();
			gameController.prepareInventoryByState(gameState);
			break;

		default:
			SeekLog.debug("GameState == DEFAULT > UNINITIALISED");
			setGameState(GameState.UNINITIALISED);
			setJoinable(false);
			break;
		}

		timer.decr();
	}

	/**
	 * Handles the <code>{@link GameState}.JOINING</code> phase of this Game
	 */
	private void joining() {
		SeekLog.debug("Players now JOINING");

		for (PlayerData data : players.values()) {
			SeekLog.debug("Setting " + data.getHost().getName() + "'s exp levels to " + (timer.totalDuration - timer.timeRemaining));
			Player player = data.getHost();
			player.setLevel(timer.totalDuration - timer.timeRemaining);
		}

		boolean shouldAnnounceAcceleratedGameStart = false;
		boolean shouldAnnounceWaitingForMorePlayers = false;

		// If the playing players is at least the minimum size, accelerate the game
		// start.
		if (players.values().size() >= gameConfig.MINIMUM_PLAYERS) {
			timer.decr();
			shouldAnnounceAcceleratedGameStart = true;
		} else {
			timer.reset();
			shouldAnnounceWaitingForMorePlayers = true;
		}

		// If shouldAnnounceAcceleratedGameStart, announce to all players who haven't
		// already been announced to
		if (shouldAnnounceAcceleratedGameStart) {
			for (PlayerData data : players.values()) {
				if (!data.hasHadAcceleratedGameStartAnnounced()) {
					data.getHost().sendTitle("Hide and Seek", "Enough players have joined! Game starting soon!", 20, 100, 20);
					data.setHasHadAcceleratedGameStartAnnounced(true);
				}
			}
		} else { // Otherwise set all hasBeenAnnouncedTo to false
			for (PlayerData data : players.values()) {
				data.setHasHadAcceleratedGameStartAnnounced(false);
			}
		}

		// If shouldAnnounceWaitingForMorePlayers, announce to all players who haven't
		// already been announced to
		if (shouldAnnounceWaitingForMorePlayers) {
			for (PlayerData data : players.values()) {
				if (!data.hasHadWaitingForMorePlayersAnnounced()) {
					data.getHost().sendTitle("Hide and Seek", "Not enough players! Game start delayed...", 20, 100, 20);
					data.setHasHadWaitingForMorePlayersAnnounced(true);
				}
			}
		} else {
			for (PlayerData data : players.values()) {
				data.setHasHadWaitingForMorePlayersAnnounced(false);
			}
		}

		// If the timer is in the last five seconds and there aren't enough players,
		// reset the timer and apologise.
		if (timer.timeRemaining < gameConfig.GAME_DURATION + gameConfig.FINISH_DURATION + 5) {
			if (players.size() < gameConfig.MINIMUM_PLAYERS) {
				timer.reset();
				for (PlayerData data : players.values()) {
					data.getHost().sendTitle("Hide and Seek", "We don't have enough players! Game start postponed!", 20, 100, 20);
				}
			}
		}
	}

	private void playing() {
		SeekLog.error("No playing behavior");

		// Tally up all remaining hiders
		int hiderTally = 0;
		for (PlayerData data : players.values()) {
			if (!data.isSeeker()) {
				hiderTally++;
			}
		}
		SeekLog.debug("hiderTally: " + hiderTally);
		// If total hiders == 0, change the gameState to GameState.FINISHED, as there
		// are no hiders remaining.
		if (hiderTally == 0) {
			setGameState(GameState.FINISHED);
		}
	}

	private void finished() {
		SeekLog.error("No finished behavior");
	}

	/**
	 * Updates the {@link GameState} of this {@link Game} based on the time
	 * remaining.
	 */
	private void changeGameState() {
		SeekLog.debug("Changing GamesState based on GameTimer " + timer);

		if (timer.timeRemaining < 1) {
			reset();
		} else if (timer.timeRemaining > gameConfig.GAME_DURATION + gameConfig.FINISH_DURATION || players.values().size() < 3) {
			setGameState(GameState.JOINING);
		} else if (timer.timeRemaining > gameConfig.FINISH_DURATION) {
			setGameState(GameState.PLAYING);
		} else if (timer.timeRemaining <= gameConfig.FINISH_DURATION) {
			setGameState(GameState.FINISHED);
		} else {
			SeekLog.error("Unhandled case while changing GameState! Using GameTime + " + timer);
		}
	}

	/**
	 * Resets this {@link Game} by removing all {@link Player}s and resetting the
	 * {@link GameTimer}
	 */
	private void reset() {
		SeekLog.info("Resetting GameTimer for Game: " + this);
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
		SeekLog.debug("Trying to add Player " + player.getName());
		if (isJoinable()) {
			SeekLog.debug(" - Game is joinable");
			players.put(player.getName(), new PlayerData(player));
			SeekLog.debug(" - Added " + player.getName());
			return true;
		}
		SeekLog.debug(" - Game not joinable");
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
			SeekLog.debug("Removed Player " + playerName);

			if (gameState == GameState.PLAYING || gameState == GameState.FINISHED) {
				SeekLog.debug("GameState is " + gameState);
				teleportPlayerToExternalLobby(Bukkit.getPlayer(playerName));
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
		SeekLog.debug("Destroying Game.");
		SeekLog.debug(" - Handling Players");
		try {
			for (PlayerData data : players.values()) {
				Player player = data.getHost();
				SeekLog.debug("Handling Player " + player.getName());

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
		SeekLog.debug("Teleported " + player.getName() + " to external Lobby at :");
		ConfigurationSection configSection = config.getConfigurationSection("externalLobbyLocation");
		SeekLog.debug(" - External Lobby Location: ");

		String worldName = configSection.getString("worldName");
		int x = configSection.getInt("x");
		int y = configSection.getInt("y");
		int z = configSection.getInt("z");

		SeekLog.debug(" - - World Name: " + worldName);
		SeekLog.debug(" - - X: " + x);
		SeekLog.debug(" - - Y: " + y);
		SeekLog.debug(" - - Z: " + z);

		player.getLocation().setWorld(Bukkit.getWorld(worldName));
		player.getLocation().setX(x);
		player.getLocation().setY(y);
		player.getLocation().setZ(z);
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
		builder.append(" GameController:" + gameController);
		builder.append(" BlockPalette: " + palette);
		builder.append(" joinable:" + joinable);
		builder.append(" GameTimer:" + timer);
		builder.append(" GameConfig:" + gameConfig);
		builder.append(" GameState:" + gameState);

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
		SeekLog.debug("Chaning GameState from " + this.gameState + " to " + gameState);
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
			reset();
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
	public static enum GameState {
		UNINITIALISED, JOINING, PLAYING, FINISHED;
	}
}
