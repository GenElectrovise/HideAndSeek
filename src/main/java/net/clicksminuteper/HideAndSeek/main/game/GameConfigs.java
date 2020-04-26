package net.clicksminuteper.HideAndSeek.main.game;

import net.clicksminuteper.HideAndSeek.main.Reference;

public class GameConfigs {
	public int GAME_DURATION;
	public int LOBBY_DURATION;

	public GameConfigs() {
		Reference.getConfighandler().reload();
		GAME_DURATION = Reference.getConfighandler().getGameLength();
		LOBBY_DURATION = Reference.getConfighandler().getLobbyLength();
	}
}
