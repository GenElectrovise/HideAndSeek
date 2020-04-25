package net.clicksminuteper.HideAndSeek.main.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.clicksminuteper.HideAndSeek.main.Reference;

public class Game {
	private static final int TOTAL_GAME_DURATION_SECONDS = 300;
	private static final int TOTAL_GAME_DURATION_TICKS = TOTAL_GAME_DURATION_SECONDS * 20;
	public ThreeDCoordinate origin;
	public HashMap<String, PlayerData> players = new HashMap<String, PlayerData>();
	public static HashMap<ThreeDCoordinate, Game> activeGames = new HashMap<ThreeDCoordinate, Game>();
	public boolean canJoin = false;
	private Logger logger;
	public static ArrayList<Player> playersInAllGames = new ArrayList<Player>();
	private BlockPalette palette;

	public static final ItemStack BOW = new ItemStack(Material.BOW);
	public static final ItemStack ARROWS = new ItemStack(Material.ARROW, 64);
	public static final ItemStack DISGUISE = new ItemStack(Material.CARVED_PUMPKIN);
	public static final ItemStack UNDISGUISE = new ItemStack(Material.BARRIER);

	public Game(Logger logger, ThreeDCoordinate origin, String paletteName) {
		this.logger = logger;
		this.origin = origin;
		this.palette = new BlockPalette(Reference.getConfighandler().getBlockPalette(paletteName));
	}

	/**
	 * Adds a player to this {@link Game}
	 * 
	 * @param player {@link Player} to add
	 * @return Their newly generated {@link PlayerData}
	 */
	public PlayerData addPlayer(Player player) {
		playersInAllGames.add(player);
		return players.put(player.getName(), new PlayerData(player));
	}

	/**
	 * Removes a Player from this Game
	 * 
	 * @param player {@link Player} to remove
	 * @return Their {@link PlayerData}
	 */
	public PlayerData removePlayer(Player player) {
		playersInAllGames.add(player);
		return players.remove(player.getName());
	}

	/**
	 * Creates a new entry in the list of Games
	 * 
	 * @param game A {@link Game} to be added
	 * @return The entered Game
	 */
	public static Game newGame(Game game) {
		return activeGames.put(game.origin, game);
	}

	/**
	 * Runs a {@link Game}
	 */
	public void run() {
		logger.info("Allowing players to join!");
		allowPlayersToJoin(Reference.getConfighandler().getLobbyLength());
	}

	/**
	 * Destroys this {@link Game}
	 * 
	 * @return Whether the {@link Game} was destroyed successfully
	 */
	public boolean destroy() {
		try {
			for (String name : this.players.keySet()) {
				playersInAllGames.remove(players.get(name).getHost());
			}
			activeGames.remove(this.origin);
			return true;
		} catch (Exception e) {
			logger.severe("EXCEPTION DESTROYING GAME!");
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Determines the play time.
	 * 
	 * @param seconds
	 */
	private void gamePlay(int seconds) {
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Reference.getHideandseek(), new Runnable() {
			public void run() {
				logger.info("Game at " + origin.x + origin.z + "ended!");
				destroy();
			}
		}, (seconds * 20));
	}

	/**
	 * Gives {@link Player}s items and effects and teleports them to the starting
	 * location
	 */
	private void preparePlayers() {

		// Make list of players in game
		ArrayList<PlayerData> playersDataList = new ArrayList<PlayerData>();
		for (PlayerData data : players.values()) {
			playersDataList.add(data);
		}

		try {
			// Make a random player the first seeker
			Random rand = new Random();
			int seekerIndex = 0;
			if (players.size() > 1) {
				seekerIndex = rand.nextInt(players.size() - 1);
			}
			playersDataList.get(seekerIndex).setSeeker(true);
		} catch (NullPointerException n) {
			logger.warning("Players list for " + this + "is empty!");
			n.printStackTrace();
		}

		// Give players effects
		for (PlayerData data : playersDataList) {
			if (data.isSeeker()) {
				seekerEffects(data);
			} else {
				hiderEffects(data);
			}
		}

		// Give players items
		for (PlayerData data : playersDataList) {
			if (data.isSeeker()) {
				seekerItems(data);
			} else {
				hiderItems(data);
			}
		}

		teleportPlayerToStartLocation();
	}

	/**
	 * Teleports {@link Player}s to the origin of this Game
	 */
	private void teleportPlayerToStartLocation() {
		logger.info("Teleporting!");
		// Tp players to start location
		for (PlayerData data : players.values()) {
			Player player = data.getHost();

			String cmd = "tp " + player.getName() + " " + origin.x + " " + origin.y + " " + origin.z;
			logger.info("cmd : " + cmd);

			logger.info(data.getHost().getDisplayName() + " is a seeker!");
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd);
		}
	}

	/**
	 * Applies effects to seekers
	 * 
	 * @param data
	 */
	private void seekerEffects(PlayerData data) {
		logger.info(data.getHost().getDisplayName() + " is a seeker! (Potions)");
		// give speed 3, 20 seconds blindness & slowness
		data.getHost().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, TOTAL_GAME_DURATION_TICKS, 2, false));
		data.getHost().addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 20, 25, false));
		data.getHost().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 20, 2, false));
	}

	/**
	 * Applies effects to hiders
	 * 
	 * @param data
	 */
	private void hiderEffects(PlayerData data) {
		logger.info(data.getHost().getDisplayName() + " is a hider! (Potions)");
		// give speed 1, jump 3
		data.getHost().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, TOTAL_GAME_DURATION_TICKS, 0, false));
		data.getHost().addPotionEffect(new PotionEffect(PotionEffectType.JUMP, TOTAL_GAME_DURATION_TICKS, 2, false));
	}

	/**
	 * Gives the seekers their items
	 * 
	 * @param data
	 */
	private void seekerItems(PlayerData data) {
		logger.info(data.getHost().getDisplayName() + " is a seeker! (Items)");
		// give diamond sword
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
	private void hiderItems(PlayerData data) {
		logger.info(data.getHost().getDisplayName() + " is a seeker! (Items)");
		// give bow, 64 arrows

		BOW.addEnchantment(Enchantment.ARROW_DAMAGE, 2);
		BOW.addEnchantment(Enchantment.ARROW_INFINITE, 1);
		BOW.addEnchantment(Enchantment.ARROW_KNOCKBACK, 1);
		BOW.addEnchantment(Enchantment.VANISHING_CURSE, 1);
		ItemMeta bow_meta = BOW.getItemMeta();
		bow_meta.setUnbreakable(true);
		bow_meta.setDisplayName("Seeker Deleter");

		ItemMeta arrow_meta = ARROWS.getItemMeta();
		arrow_meta.setDisplayName("Hider's Projectiles of Justice");

		ItemMeta disguise_meta = DISGUISE.getItemMeta();
		disguise_meta.setDisplayName("Disguise-a-tron");

		ItemMeta undisguise_meta = UNDISGUISE.getItemMeta();
		undisguise_meta.setDisplayName("Disguise Remover");

		data.getHost().getInventory().addItem(BOW);
		data.getHost().getInventory().addItem(ARROWS);
		data.getHost().getInventory().addItem(DISGUISE);
		data.getHost().getInventory().addItem(UNDISGUISE);
	}

	public void allowPlayersToJoin(int seconds) {
		canJoin = true;
		logger.info("Game joinable");

		logger.info("Schedualling wait... of " + seconds + " seconds");

		logger.info("HideAndSeek " + Reference.getHideandseek());
		logger.info("Bukkit Server : " + Bukkit.getServer());
		logger.info("Bukkit Scheduler : " + Bukkit.getServer().getScheduler());

		Runnable run = new Runnable() {
			public void run() {
				canJoin = false;
				logger.info("Game no longer joinable!");

				StringBuilder builder = new StringBuilder();
				builder.append("Game at " + origin.toString() + " created with players ");
				for (String name : players.keySet()) {
					builder.append(name + " ");
				}

				logger.info(builder.toString());

				// Next phase
				logger.info("Starting game!");
				preparePlayers();
				logger.info("Gameplay commences!");
				gamePlay(Reference.getConfighandler().getGameLength());
			}
		};
		logger.info(new Integer(Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Reference.getHideandseek(),
				run, (seconds * 20))).toString());
		logger.info("Please wait for the game to start...");
	}

	public static Game nearestGame(ThreeDCoordinate c) {
		double pX = c.x;
		double pZ = c.z;
		Reference.getLogger().info("Player coords for finding nearest game : " + pX + "," + pZ);

		HashMap<Double, ThreeDCoordinate> distances = new HashMap<Double, ThreeDCoordinate>();
		for (ThreeDCoordinate coord : Game.activeGames.keySet()) {
			double xDist = Math.sqrt((coord.x - pX) * (coord.x - pX));
			double zDist = Math.sqrt((coord.z - pZ) * (coord.z - pZ));
			Reference.getLogger().info("Z-Dist : " + zDist);
			Reference.getLogger().info("Z-Dist : " + xDist);

			double hypDist = Math.sqrt((xDist * xDist) + (zDist * zDist));
			Reference.getLogger().info("Hyp-Dist : " + hypDist);
			distances.put(hypDist, coord);
		}

		double smallestDist = Collections.min(distances.keySet());
		Reference.getLogger().info("Smallest : " + smallestDist);

		ThreeDCoordinate nearestGameCoordinate = distances.get(smallestDist);
		Reference.getLogger().info("At coord : " + nearestGameCoordinate);

		Game nearestGame = activeGames.get(nearestGameCoordinate);
		return nearestGame;
	}

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
		builder.append(" canJoin:" + canJoin);

		builder.append("}");
		return builder.toString();
	}

	public BlockPalette getPalette() {
		return palette;
	}
}
