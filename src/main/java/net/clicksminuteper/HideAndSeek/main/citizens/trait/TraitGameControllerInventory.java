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
package net.clicksminuteper.HideAndSeek.main.citizens.trait;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.citizensnpcs.api.trait.trait.Inventory;

/**
 * A only semi-bodged class for handling a {@link TraitGameController}'s
 * inventory.
 * 
 * @author GenElectrovise
 *
 */
public class TraitGameControllerInventory extends Inventory {

	public void setInventory(Inventory inv) {
		this.setContents(inv.getContents());
	}

	/**
	 * @return JOINING_INVENTORY
	 */
	public static Inventory generateJoiningInventory(Inventory inv) {
		// New
		ItemStack leave = new ItemStack(Material.BARRIER);
		ItemStack join = new ItemStack(Material.EMERALD);
		ItemStack help = new ItemStack(Material.GOLD_NUGGET);

		// Names
		ItemMeta leaveMeta = leave.getItemMeta();
		leaveMeta.setDisplayName("Leave Game");

		ItemMeta joinMeta = leave.getItemMeta();
		joinMeta.setDisplayName("Join Game");

		ItemMeta helpMeta = leave.getItemMeta();
		helpMeta.setDisplayName("Help");

		// Lore
		ArrayList<String> helpLore = new ArrayList<String>();
		helpLore.add("HideAndSeek by GenElectrovise");
		helpLore.add("If you want to play, press the emerald above!");
		helpLore.add("If you don't, press the cross!");
		helpLore.add("The idea is simple: one player starts as a seeker and the others start as hiders");
		helpLore.add("The hiders can diguise themselves as blocks using the Disguise-a-Tron 3000 item they will be given in their hotbar");
		helpLore.add("The seekers' job is to find the hiders.");
		helpLore.add("When the seeker finds and kills a hider, the hider becomes a seeker!");
		helpLore.add("If the time runs out and there are still hiders, those hiders win!");
		helpLore.add("... but if all of the hiders are found and killed, the seekers win!");
		helpLore.add("So! Want to play? Press the emerald above!");

		// Set
		leave.setItemMeta(leaveMeta);
		join.setItemMeta(joinMeta);
		help.setItemMeta(helpMeta);

		// Add
		inv.setItem(13, leave);
		inv.setItem(14, help);
		inv.setItem(15, join);

		return inv;
	}

	/**
	 * @return UNINITIALISED_INVENTORY
	 */
	public static Inventory generateUninitialisedInventory(Inventory inv) {

		// New
		ItemStack nope = new ItemStack(Material.SKELETON_SKULL);

		// Names
		ItemMeta nopeMeta = nope.getItemMeta();
		nopeMeta.setDisplayName("Nope!");

		// Lore
		ArrayList<String> nopeLore = new ArrayList<String>();
		nopeLore.add("This game has not even *started* to start yet! Please reopen this menu, and if this persists, please contact a member of this server's staff.");
		nopeMeta.setLore(nopeLore);

		// Set
		nope.setItemMeta(nopeMeta);

		// Add
		inv.setItem(14, nope);

		return inv;
	}

	/**
	 * @return PLAYING_INVENTORY
	 */
	public static Inventory generatePlayingInventory(Inventory inv) {

		// New
		ItemStack leave = new ItemStack(Material.BARRIER);
		ItemStack help = new ItemStack(Material.GOLD_NUGGET);

		// Names
		ItemMeta leaveMeta = leave.getItemMeta();
		leaveMeta.setDisplayName("Leave Game");

		ItemMeta helpMeta = help.getItemMeta();
		helpMeta.setDisplayName("Help");

		// Lore
		ArrayList<String> nopeLore = new ArrayList<String>();
		nopeLore.add("Nooooo! Please don't go!");
		leaveMeta.setLore(nopeLore);

		ArrayList<String> helpLore = new ArrayList<String>();
		helpLore.add("HideAndSeek by GenElectrovise");
		helpLore.add("If you want to play, press the emerald above!");
		helpLore.add("If you don't, press the cross!");
		helpLore.add("The idea is simple: one player starts as a seeker and the others start as hiders");
		helpLore.add("The hiders can diguise themselves as blocks using the Disguise-a-Tron 3000 item they will be given in their hotbar");
		helpLore.add("The seekers' job is to find the hiders.");
		helpLore.add("When the seeker finds and kills a hider, the hider becomes a seeker!");
		helpLore.add("If the time runs out and there are still hiders, those hiders win!");
		helpLore.add("... but if all of the hiders are found and killed, the seekers win!");
		helpLore.add("So! Want to play? Press the emerald above!");
		helpMeta.setLore(helpLore);

		// Set
		leave.setItemMeta(leaveMeta);
		help.setItemMeta(helpMeta);

		// Add
		inv.setItem(13, leave);
		inv.setItem(15, help);

		return inv;
	}

	public static Inventory generateFinishedInventory(Inventory inv) {

		// New
		ItemStack leave = new ItemStack(Material.BARRIER);

		// Names
		ItemMeta leaveMeta = leave.getItemMeta();
		leaveMeta.setDisplayName("Leave");

		// Lore
		ArrayList<String> leaveLore = new ArrayList<String>();
		leaveLore.add("Thanks for playing HideAndSeek by GenElectrovise!");
		leaveLore.add("You can check out the source code for this plugin on my GitHub repository! (Just search GenElectrovise and find HideAndSeek!)");
		leaveMeta.setLore(leaveLore);

		// Set
		leave.setItemMeta(leaveMeta);

		// Add
		inv.setItem(14, leave);

		return inv;
	}
}
