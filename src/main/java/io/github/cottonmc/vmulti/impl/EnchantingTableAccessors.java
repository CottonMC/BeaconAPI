package io.github.cottonmc.vmulti.impl;

import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.Property;

import java.util.List;
import java.util.Random;

public interface EnchantingTableAccessors {
	Random vmulti_getRandom();
	Property vmulti_getSeed();
	List<EnchantmentLevelEntry> vmulti_getEnchantments(ItemStack stack, int slot, int enchantingPower);
}
