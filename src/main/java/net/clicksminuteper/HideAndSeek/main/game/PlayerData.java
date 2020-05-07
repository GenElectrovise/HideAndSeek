/*
 * HideAndSeek -- A Hide and Seek plugin for Bukkit and Spigot
    Copyright (C) 2020 GenElectrovise

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package net.clicksminuteper.HideAndSeek.main.game;

import org.bukkit.entity.Player;

public class PlayerData {
	private Player host;
	private boolean isSeeker = false;
	private boolean hasHadAcceleratedGameStartAnnounced = false;
	private boolean hasHadWaitingForMorePlayersAnnounced = false;

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

	/**
	 * @return the hasHadAcceleratedGameStartAnnounced
	 */
	public boolean hasHadAcceleratedGameStartAnnounced() {
		return hasHadAcceleratedGameStartAnnounced;
	}

	/**
	 * @param hasHadAcceleratedGameStartAnnounced the
	 *                                            hasHadAcceleratedGameStartAnnounced
	 *                                            to set
	 */
	public void setHasHadAcceleratedGameStartAnnounced(boolean hasHadAcceleratedGameStartAnnounced) {
		this.hasHadAcceleratedGameStartAnnounced = hasHadAcceleratedGameStartAnnounced;
	}
	
	/**
	 * @return the hasHadWaitingForMorePlayersAnnounced
	 */
	public boolean hasHadWaitingForMorePlayersAnnounced() {
		return hasHadWaitingForMorePlayersAnnounced;
	}
	
	/**
	 * @param hasHadWaitingForMorePlayersAnnounced the hasHadWaitingForMorePlayersAnnounced to set
	 */
	public void setHasHadWaitingForMorePlayersAnnounced(boolean hasHadWaitingForMorePlayersAnnounced) {
		this.hasHadWaitingForMorePlayersAnnounced = hasHadWaitingForMorePlayersAnnounced;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PlayerData{");

		builder.append("HostName:" + host.getName());
		builder.append(" isSeeker:" + isSeeker);
		builder.append(" hasHadAcceleratedGameStartAnnounced:" + hasHadAcceleratedGameStartAnnounced);

		builder.append("}");
		return builder.toString();
	}
}
