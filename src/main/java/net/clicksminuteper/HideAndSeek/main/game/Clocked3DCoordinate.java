package net.clicksminuteper.HideAndSeek.main.game;

import org.bukkit.entity.Player;

public class Clocked3DCoordinate extends ThreeDCoordinate {
	private Player player;
	private int timer;
	private int iterations;

	public Clocked3DCoordinate(double x, double y, double z, Player player, int iterations) {
		super(x, y, z);
		this.iterations = iterations;
		this.player = player;
	}

	/**
	 * Returns whether a player has been still for the number of iterations.
	 * 
	 * @return
	 */
	public boolean timedOut() {
		
		if (player.getLocation().getBlockX() == x && player.getLocation().getBlockZ() == z) {
			timer++;
		} else {
			timer = 0;
		}

		if (timer > iterations) {
			timer = 0;
			return true;
		}

		return false;
	}

	@Override
	public String toString() {
		return "{" + x + "," + y + "," + z + " iterations:" + iterations + " timer:" + timer + "}";
	}

}
