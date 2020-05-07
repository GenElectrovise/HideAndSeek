/**
 * HideAndSeek -- A Hide and Seek plugin for Bukkit and Spigot
 *  Copyright (C) 2020 GenElectrovise
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package net.clicksminuteper.HideAndSeek.test.timer;

/**
 * A testing class for perfecting the GameTimer class and GameStage switching.
 * 
 * @author GenElectrovise
 *
 */
public class TimerTest {
	private static int TOTAL_TIME;
	private static int timeRemaining;
	private static final int LOBBY_TIME = 30;
	private static final int GAME_TIME = 240;
	private static final int FINISH_TIME = 30;
	private static Stage stage = Stage.LOBBY;

	public static void main(String[] args) {
		TOTAL_TIME = LOBBY_TIME + GAME_TIME + FINISH_TIME;
		reset();

		while (true) {
			System.out.println("New iteration:");
			evalStage2();
			sayStage();
			decr();
			System.out.println("=");
		}
	}

	/**
	 * 
	 */
	@SuppressWarnings("unused")
	private static void evalStage() {
		System.out.println("Evaluating stage!");
		if (timeRemaining < 1) {
			reset();
		} else if (timeRemaining > GAME_TIME + FINISH_TIME) {
			stage = Stage.LOBBY;
		} else if (timeRemaining > FINISH_TIME) {
			stage = Stage.GAME;
		} else if (timeRemaining > 0) {
			stage = Stage.FINISH;
		} else {
			System.err.println("Unhandled case! Time remaining = " + timeRemaining);
		}
	}

	private static void evalStage2() {
		System.out.println("Changing Stage based on time " + timeRemaining);
		if (timeRemaining == 0) {
			reset();
		} else if (timeRemaining > GAME_TIME + FINISH_TIME) {
			stage = Stage.LOBBY;
		} else if (timeRemaining > FINISH_TIME) {
			stage = Stage.GAME;
		} else if (timeRemaining <= FINISH_TIME) {
			stage = Stage.FINISH;
		} else {
			System.err.println("Unhandled case in changing state! Time = " + timeRemaining);
		}
	}

	/**
	 * 
	 */
	private static void sayStage() {
		switch (stage) {
		case LOBBY:
			System.out.println("Stage : LOBBY");
			break;

		case GAME:
			System.out.println("Stage : GAME");
			break;

		case FINISH:
			System.out.println("Stage : FINISH");
			break;
		}
	}

	/**
	 * 
	 */
	private static void reset() {
		timeRemaining = TOTAL_TIME;
		System.out.println("Resetting timeRemaining to " + TOTAL_TIME);
	}

	/**
	 * 
	 */
	private static void decr() {
		timeRemaining--;
		System.out.println("remaining : " + timeRemaining);
	}

	private static enum Stage {
		LOBBY, GAME, FINISH;
	}
}
