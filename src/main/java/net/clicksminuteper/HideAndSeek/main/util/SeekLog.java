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

import net.clicksminuteper.HideAndSeek.main.Reference;

public class SeekLog {

	public static boolean debubEnabled = Reference.getConfighandler().getDebugEnabled();

	private static String getLastInStack() {
		StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
		int length = stackTraceElements.length;

		String className = stackTraceElements[length - 1].getClassName();
		int lineNumber = stackTraceElements[length - 1].getLineNumber();
		String methodName = stackTraceElements[length - 1].getMethodName();

		return new StringBuilder().append("{" + className).append(" | " + lineNumber).append(" | " + methodName + "}")
				.toString();
	}

	public static void info(String msg) {
		SeekLog.info(getLastInStack() + " : " + msg);
	}

	public static void warning(String msg) {
		Reference.getLogger().warning(getLastInStack() + " : " + msg);
	}

	public static void error(String msg) {
		Reference.getLogger().severe(getLastInStack() + " : " + msg);
	}

	public static void debug(String msg) {
		if (debubEnabled) {
			SeekLog.info("[DEBUG] " + getLastInStack() + " : " + msg);
		}
	}
}
