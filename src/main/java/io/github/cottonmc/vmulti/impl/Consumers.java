package io.github.cottonmc.vmulti.impl;

import io.github.cottonmc.vmulti.api.EnchantmentBooster;
import io.github.cottonmc.vmulti.api.VMultiAPI;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.EnchantmentScreenHandler;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.List;
import java.util.function.BiConsumer;

public class Consumers {
	
	public static BiConsumer<World, BlockPos> getEnchantingScanner(EnchantmentScreenHandler handler, ItemStack stack) {
		return (world, pos) -> {
			EnchantingTableAccessors accessor = (EnchantingTableAccessors) handler;
			int i = 0;

			int j;
			for(j = -1; j <= 1; ++j) {
				for(int k = -1; k <= 1; ++k) {
					if ((j != 0 || k != 0) && world.isAir(pos.add(k, 0, j)) && world.isAir(pos.add(k, 1, j))) {
						BlockPos newPos = pos.add(k * 2, 0, j * 2);
						BlockState state = world.getBlockState(newPos);
						if (state.isIn(VMultiAPI.ENCHANTMENT_BOOSTERS)) {
							if (state.getBlock() instanceof EnchantmentBooster) {
								i += ((EnchantmentBooster)state.getBlock()).getEnchantmentBoost(world, newPos, state);
							} else {
								i++;
							}
						}

						newPos = pos.add(k * 2, 1, j * 2);
						state = world.getBlockState(newPos);
						if (state.isIn(VMultiAPI.ENCHANTMENT_BOOSTERS)) {
							if (state.getBlock() instanceof EnchantmentBooster) {
								i += ((EnchantmentBooster)state.getBlock()).getEnchantmentBoost(world, newPos, state);
							} else {
								i++;
							}
						}

						if (k != 0 && j != 0) {
							newPos = pos.add(k * 2, 0, j);
							state = world.getBlockState(newPos);
							if (state.isIn(VMultiAPI.ENCHANTMENT_BOOSTERS)) {
								if (state.getBlock() instanceof EnchantmentBooster) {
									i += ((EnchantmentBooster)state.getBlock()).getEnchantmentBoost(world, newPos, state);
								} else {
									i++;
								}
							}

							newPos = pos.add(k * 2, 1, j);
							state = world.getBlockState(newPos);
							if (state.isIn(VMultiAPI.ENCHANTMENT_BOOSTERS)) {
								if (state.getBlock() instanceof EnchantmentBooster) {
									i += ((EnchantmentBooster)state.getBlock()).getEnchantmentBoost(world, newPos, state);
								} else {
									i++;
								}
							}

							newPos = pos.add(k, 0, j * 2);
							state = world.getBlockState(newPos);
							if (state.isIn(VMultiAPI.ENCHANTMENT_BOOSTERS)) {
								if (state.getBlock() instanceof EnchantmentBooster) {
									i += ((EnchantmentBooster)state.getBlock()).getEnchantmentBoost(world, newPos, state);
								} else {
									i++;
								}
							}

							newPos = pos.add(k, 1, j * 2);
							state = world.getBlockState(newPos);
							if (state.isIn(VMultiAPI.ENCHANTMENT_BOOSTERS)) {
								if (state.getBlock() instanceof EnchantmentBooster) {
									i += ((EnchantmentBooster)state.getBlock()).getEnchantmentBoost(world, newPos, state);
								} else {
									i++;
								}
							}
						}
					}
				}
			}

			accessor.vmulti_getRandom().setSeed(accessor.vmulti_getSeed().get());

			for(j = 0; j < 3; ++j) {
				handler.enchantmentPower[j] = EnchantmentHelper.calculateRequiredExperienceLevel(accessor.vmulti_getRandom(), j, i, stack);
				handler.enchantmentId[j] = -1;
				handler.enchantmentLevel[j] = -1;
				if (handler.enchantmentPower[j] < j + 1) {
					handler.enchantmentPower[j] = 0;
				}
			}

			for(j = 0; j < 3; ++j) {
				if (handler.enchantmentPower[j] > 0) {
					List<EnchantmentLevelEntry> list = accessor.vmulti_getEnchantments(stack, j, handler.enchantmentPower[j]);
					if (list != null && !list.isEmpty()) {
						EnchantmentLevelEntry enchantmentLevelEntry = list.get(accessor.vmulti_getRandom().nextInt(list.size()));
						handler.enchantmentId[j] = Registry.ENCHANTMENT.getRawId(enchantmentLevelEntry.enchantment);
						handler.enchantmentLevel[j] = enchantmentLevelEntry.level;
					}
				}
			}

			handler.sendContentUpdates();
		};
	}

}
