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

package net.clicksminuteper.HideAndSeek.main.game.listener;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.clicksminuteper.HideAndSeek.main.Reference;
import net.clicksminuteper.HideAndSeek.main.disguise.LibsInterface;
import net.clicksminuteper.HideAndSeek.main.game.Game;
import net.clicksminuteper.HideAndSeek.main.game.Games;
import net.clicksminuteper.HideAndSeek.main.util.SeekLog;

public class PlayerDeathListener implements Listener {

	@EventHandler
	public static void listenForPlayerDeaths(EntityDamageEvent event) {

		SeekLog.info("EntityDamageEvent triggered");

		Player player;
		if (event.getEntity() instanceof Player) {
			player = (Player) event.getEntity();

			SeekLog.info("Damaged Entity is a Player!");

			if (event.getDamage() > player.getHealth()) {

				SeekLog.info("They would die from that damage!");

				event.setCancelled(true);
				double pX = player.getLocation().getX();
				double pY = player.getLocation().getY();
				double pZ = player.getLocation().getZ();

				Game nearestGame = Games.nearestGame(new Location(player.getWorld(), pX, pY, pZ));
				SeekLog.info("Nearest game is at " + nearestGame.gamedata.getOrigin().getX() + ","
						+ nearestGame.gamedata.getOrigin().getZ());

				if (nearestGame.players.get(player.getName()) != null) {
					SeekLog.info("Player is participating in that game!");
					event.setCancelled(true);
					SeekLog.info("Cancelled damage event");
					nearestGame.players.get(player.getName()).setSeeker(true);
					SeekLog.info("Player is now a seeker");
					player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
					SeekLog.info("Reset their health to max");

					LibsInterface.cmdUndisguise(Reference.getLogger(), player);

					Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
							"tp " + player.getName() + " " + nearestGame.gamedata.getOrigin().getX() + " "
									+ nearestGame.gamedata.getOrigin().getY() + " " + nearestGame.gamedata.getOrigin().getZ());
					player.sendMessage("You are a seeker! Have some seeker items!");

					player.getInventory().clear();

					// give diamond sword
					ItemStack DIAMOND_SWORD = new ItemStack(Material.DIAMOND_SWORD);

					DIAMOND_SWORD.addEnchantment(Enchantment.DAMAGE_ALL, 2);
					DIAMOND_SWORD.addEnchantment(Enchantment.VANISHING_CURSE, 1);

					ItemMeta sword_meta = DIAMOND_SWORD.getItemMeta();
					sword_meta.setDisplayName("Hider Remover");

					player.getInventory().addItem(DIAMOND_SWORD);
				}
			}
		}
	}
}
