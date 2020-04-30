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

package net.clicksminuteper.HideAndSeek.main.item;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.clicksminuteper.HideAndSeek.main.Reference;

public class Constants {

	public static enum ItemConstants {
		BOW(new ItemStack(Material.BOW)), ARROWS(new ItemStack(Material.ARROW, 64)), DISGUISE(new ItemStack(Material.CARVED_PUMPKIN)), UNDISGUISE(new ItemStack(Material.BARRIER));

		private ItemConstants(ItemStack s) {
			this.itemStack = s;
		}

		public ItemStack itemStack;
	}

	public static enum EnchantmentConstants {
		BOW_POWER(Enchantment.ARROW_DAMAGE, 2), BOW_INFINITY(Enchantment.ARROW_INFINITE, 1), BOW_PUNCH(Enchantment.ARROW_KNOCKBACK, 1), VANISHING(Enchantment.VANISHING_CURSE, 1);

		EnchantmentConstants(Enchantment e, int lvl) {
			this.enchantment = e;
			this.level = lvl;
		}

		public Enchantment enchantment;
		public int level;
	}

	public static class EffectConstants {
		public static int gameDuration = Reference.getInstance().getHideAndSeek().getConfig().getInt("gameDuration");

		public static final PotionEffect SEEKER_SPEED = new PotionEffect(PotionEffectType.SPEED, gameDuration, 2, true);
		public static final PotionEffect SEEKER_SLOW = new PotionEffect(PotionEffectType.SLOW, 20 * 20, 25, true);
		public static final PotionEffect SEEKER_BLIND = new PotionEffect(PotionEffectType.BLINDNESS, 20 * 20, 255, true);
		public static final PotionEffect SEEKER_RESISTANCE = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 20, 150, true);

		public static final PotionEffect HIDER_SPEED = new PotionEffect(PotionEffectType.SPEED, gameDuration, 0, true);
		public static final PotionEffect HIDER_JUMP = new PotionEffect(PotionEffectType.JUMP, gameDuration, 2, true);
		public static final PotionEffect HIDER_RESISTANCE = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 20, 150, true);
	}

	public static class Metadata {

		public static ItemMeta hiderBow() {
			ItemMeta bow_meta = Constants.ItemConstants.BOW.itemStack.getItemMeta();
			bow_meta.setDisplayName("Seeker Deleter");
			bow_meta.setUnbreakable(true);
			return bow_meta;
		}

		public static ItemMeta hiderArrows() {
			ItemMeta arrow_meta = Constants.ItemConstants.ARROWS.itemStack.getItemMeta();
			arrow_meta.setDisplayName("Hider's Projectiles of Justice");
			return arrow_meta;
		}

		public static ItemMeta hiderDisguise() {
			ItemMeta disguise_meta = Constants.ItemConstants.DISGUISE.itemStack.getItemMeta();
			disguise_meta.setDisplayName("Disguise-a-tron 3000");
			return disguise_meta;
		}

		public static ItemMeta hiderUndiguise() {
			ItemMeta undisguise_meta = Constants.ItemConstants.UNDISGUISE.itemStack.getItemMeta();
			undisguise_meta.setDisplayName("Disguise Remover");
			return undisguise_meta;
		}
	}
}
