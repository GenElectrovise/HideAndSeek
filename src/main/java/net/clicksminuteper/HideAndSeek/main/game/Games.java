package net.clicksminuteper.HideAndSeek.main.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.clicksminuteper.HideAndSeek.main.Reference;
import net.clicksminuteper.HideAndSeek.main.citizens.trait.TraitGameController;
import net.clicksminuteper.HideAndSeek.main.event.HideAndSeekTickGamesEvent;
import net.clicksminuteper.HideAndSeek.main.game.block.Palettes;
import net.clicksminuteper.HideAndSeek.main.util.SeekLog;

public class Games {
	public static final ArrayList<NPC> ACTIVE_GAMES = new ArrayList<NPC>();
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

	public static NPC newGame(Location loc, String paletteName) {
		NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, "Hide and Seek Master");
		npc.spawn(loc);
		npc.addTrait(TraitGameController.class);
		npc.getTrait(TraitGameController.class).setPalette(Palettes.getPalette(paletteName));
		ACTIVE_GAMES.add(npc);
		return npc;
	}

	public static TraitGameController nearestGameController(Location l) {
		SeekLog.debug("Coords to find nearest game to : " + l.toString());

		HashMap<Double, TraitGameController> distances = new HashMap<Double, TraitGameController>();
		for (NPC npc : ACTIVE_GAMES) {
			if (npc.hasTrait(TraitGameController.class)) {
				Game game = npc.getTrait(TraitGameController.class).getGame();

				Location g = game.getOrigin();
				Location pt3 = new Location(g.getWorld(), g.getX(), l.getY(), l.getZ());

				// Find horizontal hypotenuse
				double distPt3AToC = pt3.getX() - l.getX();
				double distGToPt3A = pt3.getZ() - g.getZ();
				double horizontalHyp = Math.sqrt((distGToPt3A * distGToPt3A) + (distPt3AToC * distPt3AToC));

				double distPt3BToC = horizontalHyp;
				double heightDifference = l.getY() - g.getY();
				double verticalHyp = Math.sqrt((heightDifference * heightDifference) + (distPt3BToC * distPt3BToC));

				distances.put(verticalHyp, npc.getTrait(TraitGameController.class));

			}
		}

		SeekLog.debug("Distances : " + distances.keySet().toString());
		SeekLog.debug("ACTIVE_GAMES : " + ACTIVE_GAMES);
		double smallest = Collections.min(distances.keySet());
		return distances.get(new Double(smallest));
	}

	public static final void createGamesTickCycle() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(Reference.getInstance().getHideAndSeek(), new Runnable() {

			@Override
			public void run() {
				HideAndSeekTickGamesEvent tickGamesEvent = new HideAndSeekTickGamesEvent();
				Bukkit.getServer().getPluginManager().callEvent(tickGamesEvent);

			}
		}, 0, 20L);
	}

	/**
	 * @param traitGameController
	 */
	public static void destroyGameObj(TraitGameController traitGameController) {
		ACTIVE_GAMES.remove(traitGameController.getNPC());
		for (PlayerData data : traitGameController.getGame().players.values()) {
			ACTIVE_PLAYERS.remove(data.getHost());
		}
	}
}
