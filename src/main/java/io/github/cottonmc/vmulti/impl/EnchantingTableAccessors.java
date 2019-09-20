package io.github.cottonmc.vmulti.impl;

import net.minecraft.container.Property;
import net.minecraft.enchantment.InfoEnchantment;
import net.minecraft.item.ItemStack;

import java.util.List;
import java.util.Random;

public interface EnchantingTableAccessors {
	Random vmulti_getRandom();
	Property vmulti_getSeed();
	List<InfoEnchantment> vmulti_getEnchantments(ItemStack stack, int slot, int enchantingPower);
}
