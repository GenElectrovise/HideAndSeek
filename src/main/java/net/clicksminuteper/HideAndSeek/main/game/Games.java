package net.clicksminuteper.HideAndSeek.main.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import net.clicksminuteper.HideAndSeek.main.util.SeekLog;

public class Games {
	public static final ArrayList<Game> ACTIVE_GAMES = new ArrayList<Game>();
	public static final HashMap<Player, PlayerData> ACTIVE_PLAYERS = new HashMap<Player, PlayerData>();

	public static void addActivePlayer(Player player) {
		ACTIVE_PLAYERS.put(player, new PlayerData(player));
	}

	public static void removeActivePlayer(Player player) {
		ACTIVE_PLAYERS.remove(player);
	}

	public static boolean isPlayerPlaying(Player player) {
		return ACTIVE_PLAYERS.containsKey(player);
	}

	public static void addGame(Game game) {
		ACTIVE_GAMES.add(game);
	}

	public static void removeGame(Game game) {
		ACTIVE_GAMES.remove(game);
	}

	public static Game newGame(GameData data) {
		// TODO Auto-generated method stub
		return null;
	}

	public static Game nearestGame(Location l) {
		SeekLog.debug("Coords to find nearest game to : " + l.toString());

		HashMap<Double, Game> distances = new HashMap<Double, Game>();
		for (Game game : ACTIVE_GAMES) {
			GameData data = game.gamedata;
			Location g = data.getOrigin();
			Location pt3 = new Location(g.getWorld(), g.getX(), l.getY(), l.getZ());

			// Find horizontal hypotenuse
			double distPt3AToC = pt3.getX() - l.getX();
			double distGToPt3A = pt3.getZ() - g.getZ();
			double horizontalHyp = Math.sqrt((distGToPt3A * distGToPt3A) + (distPt3AToC * distPt3AToC));

			double distPt3BToC = horizontalHyp;
			double heightDifference = l.getY() - g.getY();
			double verticalHyp = Math.sqrt((heightDifference * heightDifference) + (distPt3BToC * distPt3BToC));

			distances.put(verticalHyp, game);
		}

		double smallest = Collections.min(distances.keySet());
		return distances.get(new Double(smallest));
	}
}