package net.clicksminuteper.HideAndSeek.main.game;

import org.bukkit.entity.Player;

public class PlayerData {
	private Player host;
	private boolean isSeeker = false;

	public PlayerData(Player player) {
		this.host = player;
	}

	public Player getHost() {
		return host;
	}

	public boolean isSeeker() {
		return isSeeker;
	}
	
	public void setSeeker(boolean isSeeker) {
		this.isSeeker = isSeeker;
	}
}
