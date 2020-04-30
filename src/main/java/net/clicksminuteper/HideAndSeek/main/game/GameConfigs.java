package net.clicksminuteper.HideAndSeek.main.game;

import net.clicksminuteper.HideAndSeek.main.Reference;

public class GameConfigs {
	public int GAME_DURATION;
	public int LOBBY_DURATION;

	public GameConfigs() {
		GAME_DURATION = Reference.getInstance().getHideAndSeek().getConfig().getInt("gameDuration");
		LOBBY_DURATION = Reference.getInstance().getHideAndSeek().getConfig().getInt("lobbyDuration");
	}
}
