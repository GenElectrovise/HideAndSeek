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
import net.clicksminuteper.HideAndSeek.main.util.ThreeDCoordinate;

public class PlayerDeathListener implements Listener {

	@EventHandler
	public static void listenForPlayerDeaths(EntityDamageEvent event) {

		Reference.getLogger().info("EntityDamageEvent triggered");

		Player player;
		if (event.getEntity() instanceof Player) {
			player = (Player) event.getEntity();

			Reference.getLogger().info("Damaged Entity is a Player!");

			if (event.getDamage() > player.getHealth()) {

				Reference.getLogger().info("They would die from that damage!");

				event.setCancelled(true);
				double pX = player.getLocation().getX();
				double pY = player.getLocation().getY();
				double pZ = player.getLocation().getZ();

				Game nearestGame = Game.nearestGame(new ThreeDCoordinate(pX, pY, pZ));
				Reference.getLogger().info("Nearest game is at " + nearestGame.origin.x + "," + nearestGame.origin.z);

				if (nearestGame.players.get(player.getName()) != null) {
					Reference.getLogger().info("Player is participating in that game!");
					event.setCancelled(true);
					Reference.getLogger().info("Cancelled damage event");
					nearestGame.players.get(player.getName()).setSeeker(true);
					Reference.getLogger().info("Player is now a seeker");
					player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
					Reference.getLogger().info("Reset their health to max");

					LibsInterface.cmdUndisguise(Reference.getLogger(), player);
					
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tp " + player.getName() + " "
							+ nearestGame.origin.x + " " + nearestGame.origin.y + " " + nearestGame.origin.z);
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
