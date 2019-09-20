package io.github.cottonmc.vmulti.impl;

import io.github.cottonmc.vmulti.api.EnchantmentBooster;
import io.github.cottonmc.vmulti.api.VMultiAPI;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.container.EnchantingTableContainer;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.InfoEnchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class Consumers {

	public static BiConsumer<World, BlockPos> getEnchantingScanner(EnchantingTableContainer container, ItemStack stack) {
		return (world, pos) -> {
			EnchantingTableAccessors accessor = (EnchantingTableAccessors)container;
			int power = 0;

			int i;
			for(i = -1; i <= 1; ++i) {
				for(int j = -1; j <= 1; ++j) {
					if ((i != 0 || j != 0) && world.isAir(pos.add(j, 0, i)) && world.isAir(pos.add(j, 1, i))) {
						List<BlockPos> positions = new ArrayList<>();
						positions.add(pos.add(j * 2, 0, i * 2));
						positions.add(pos.add(j * 2, 1, i * 2));

						if (j != 0 && i != 0) {
							positions.add(pos.add(j * 2, 0, i));
							positions.add(pos.add(j * 2, 1, i));
							positions.add(pos.add(j, 0, i * 2));
							positions.add(pos.add(j, 1, i * 2));
						}

						power += getEnchBoost(world, positions);
					}
				}
			}

			accessor.vmulti_getRandom().setSeed(accessor.vmulti_getSeed().get());

			for(i = 0; i < 3; ++i) {
				container.enchantmentPower[i] = EnchantmentHelper.calculateEnchantmentPower(accessor.vmulti_getRandom(), i, power, stack);
				container.enchantmentId[i] = -1;
				container.enchantmentLevel[i] = -1;
				if (container.enchantmentPower[i] < i + 1) {
					container.enchantmentPower[i] = 0;
				}
			}

			for(i = 0; i < 3; ++i) {
				if (container.enchantmentPower[i] > 0) {
					List<InfoEnchantment> enchants = accessor.vmulti_getEnchantments(stack, i, container.enchantmentPower[i]);
					if (enchants != null && !enchants.isEmpty()) {
						InfoEnchantment infoEnchantment_1 = enchants.get(accessor.vmulti_getRandom().nextInt(enchants.size()));
						container.enchantmentId[i] = Registry.ENCHANTMENT.getRawId(infoEnchantment_1.enchantment);
						container.enchantmentLevel[i] = infoEnchantment_1.level;
					}
				}
			}

			container.sendContentUpdates();
		};

	}

	public static int getEnchBoost(World world, List<BlockPos> positions) {
		int ret = 0;
		for (BlockPos pos : positions) {
			BlockState state = world.getBlockState(pos);
			Block block = state.getBlock();
			if (VMultiAPI.ENCHANTMENT_BOOSTERS.contains(block)) {
				ret += (block instanceof EnchantmentBooster) ? ((EnchantmentBooster) block).getEnchantmentBoost(world, pos, state) : 1;
			}
		}
		return ret;
	}

}
