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

package net.clicksminuteper.HideAndSeek.main.util;

import java.util.logging.Logger;

import net.clicksminuteper.HideAndSeek.main.Reference;

public class SeekLog {

	protected static Logger logger;
	public static boolean debubEnabled = Reference.getInstance().getHideAndSeek().getConfig().getBoolean("debugEnabled");

	private static String getLastInStack() {
		StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();

		// [0] is most recent in stack
		String className = stackTraceElements[2].getFileName();
		int lineNumber = stackTraceElements[2].getLineNumber();
		String methodName = stackTraceElements[2].getMethodName();

		String out = ("{" + className) + (" | " + lineNumber) + (" | " + methodName + "}");

		return out;
	}

	public static void info(String msg) {
		logger.info(getLastInStack() + " : " + msg);
	}

	public static void warning(String msg) {
		logger.warning(getLastInStack() + " : " + msg);
	}

	public static void error(String msg) {
		logger.severe(getLastInStack() + " : " + msg);
	}

	public static void debug(String msg) {
		if (debubEnabled) {
			logger.info("[DEBUG] " + getLastInStack() + " : " + msg);
		}
	}

	/**
	 * @param logger the Logger instance to use for logging methods.
	 */
	public static void setLogger(Logger logger) {
		SeekLog.logger = logger;
	}
}
