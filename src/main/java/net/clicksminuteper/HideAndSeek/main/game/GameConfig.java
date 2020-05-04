package net.clicksminuteper.HideAndSeek.main.game;

import net.clicksminuteper.HideAndSeek.main.Reference;

public class GameConfig {
	public int LOBBY_DURATION;
	public int GAME_DURATION;
	public int FINISH_DURATION;

	public GameConfig() {
		LOBBY_DURATION = Reference.getInstance().getHideAndSeek().getConfig().getInt("lobbyDuration");
		GAME_DURATION = Reference.getInstance().getHideAndSeek().getConfig().getInt("gameDuration");
		FINISH_DURATION = Reference.getInstance().getHideAndSeek().getConfig().getInt("finishDuration");
	}
}
