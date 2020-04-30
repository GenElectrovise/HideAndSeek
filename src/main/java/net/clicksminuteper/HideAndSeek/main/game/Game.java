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

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.clicksminuteper.HideAndSeek.main.citizens.trait.TraitGameController;
import net.clicksminuteper.HideAndSeek.main.item.Constants;
import net.clicksminuteper.HideAndSeek.main.util.SeekLog;

public class Game {
	private TraitGameController gameController;
	public HashMap<String, PlayerData> players = new HashMap<String, PlayerData>();
	public boolean canJoin = false;
	public GameData gamedata;

	/**
	 * Runs and contains data for a {@link Game}. Controlled by a {@link TraitGameController}.
	 * @param traitGameController The enclosing {@link TraitGameController} which owns this {@link Game}
	 */
	public Game(TraitGameController traitGameController) {
		this.gameController = traitGameController;
		SeekLog.info("New Game created by NPC at " + gameController.getNPC().getEntity().getLocation());
	}

	public void run() {
		SeekLog.error("How I run Game?");
	}

	public boolean destroy() {

		SeekLog.error("Game destruction incomplete!");

		return false;
	}

	@SuppressWarnings("unused")
	private void gamePlay(int seconds) {
		SeekLog.error("Game play not added!");
	}

	@SuppressWarnings("unused")
	private void preparePlayers() {
		SeekLog.error("No player prep!");
	}

	/**
	 * Teleports {@link Player}s to the origin of this Game
	 */
	@SuppressWarnings("unused")
	private void teleportPlayerToStartLocation() {
		SeekLog.error("Teleportation not implemented");
	}

	/**
	 * Applies effects to seekers
	 * 
	 * @param data
	 */
	public void seekerEffects(PlayerData data) {
		SeekLog.error("No seeker effects");
	}

	/**
	 * Applies effects to hiders
	 * 
	 * @param data
	 */
	public void hiderEffects(PlayerData data) {
		SeekLog.error("No hider effects");
	}

	/**
	 * Gives the seekers their items
	 * 
	 * @param data
	 */
	public void seekerItems(PlayerData data) {
		SeekLog.info(data.getHost().getDisplayName() + " is a seeker! (Items)");
		// give diamond sword
		ItemStack DIAMOND_SWORD = new ItemStack(Material.DIAMOND_SWORD);

		DIAMOND_SWORD.addEnchantment(Enchantment.DAMAGE_ALL, 2);
		DIAMOND_SWORD.addEnchantment(Enchantment.VANISHING_CURSE, 1);

		ItemMeta sword_meta = DIAMOND_SWORD.getItemMeta();
		sword_meta.setDisplayName("Hider Remover");

		data.getHost().getInventory().addItem(DIAMOND_SWORD);
	}

	/**
	 * Gives the hiders their items
	 * 
	 * @param data
	 */
	public void hiderItems(PlayerData data) {
		SeekLog.info(data.getHost().getDisplayName() + " is a seeker! (Items)");

		ItemStack bow = Constants.ItemConstants.BOW.itemStack;
		ItemStack arrows = Constants.ItemConstants.BOW.itemStack;
		ItemStack disguise = Constants.ItemConstants.BOW.itemStack;
		ItemStack undisguise = Constants.ItemConstants.BOW.itemStack;

		bow.setItemMeta(Constants.Metadata.hiderBow());
		arrows.setItemMeta(Constants.Metadata.hiderArrows());
		disguise.setItemMeta(Constants.Metadata.hiderDisguise());
		undisguise.setItemMeta(Constants.Metadata.hiderUndiguise());

		data.getHost().getInventory().addItem(bow);
		data.getHost().getInventory().addItem(arrows);
		data.getHost().getInventory().addItem(disguise);
		data.getHost().getInventory().addItem(undisguise);
	}

	public void allowPlayersToJoin(int seconds) {

	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Game{");

		builder.append(" Origin{" + gamedata.getOrigin() + "}");

		builder.append(" Players[");
		for (PlayerData data : players.values()) {
			builder.append(data.getHost().getDisplayName() + ", ");
		}
		builder.append("]");
		builder.append(" " + gamedata.getPalette().toString());
		builder.append(" canJoin:" + canJoin);

		builder.append("}");
		return builder.toString();
	}

	public void addPlayer(Player player) {
		// TODO Auto-generated method stub
		
	}
}
