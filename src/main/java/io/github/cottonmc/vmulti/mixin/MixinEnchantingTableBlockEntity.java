package io.github.cottonmc.vmulti.mixin;

import io.github.cottonmc.vmulti.api.ComponentCollector;
import io.github.cottonmc.vmulti.api.VMultiAPI;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.EnchantingTableBlockEntity;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(EnchantingTableBlockEntity.class)
public abstract class MixinEnchantingTableBlockEntity extends BlockEntity implements ComponentCollector {
	public MixinEnchantingTableBlockEntity(BlockEntityType<?> type) {
		super(type);
	}

	@Override
	public Object2IntMap<Block> getActivatingBlocks() {
		Object2IntMap<Block> ret = new Object2IntOpenHashMap<>();
		for(int i = -2; i <= 2; ++i) {
			for(int j = -2; j <= 2; ++j) {
				if (i > -2 && i < 2 && j == -1) {
					j = 2;
				}
				for(int k = 0; k <= 1; ++k) {
					BlockPos newPos = pos.add(i, k, j);
					Block block = world.getBlockState(newPos).getBlock();
					if (VMultiAPI.ENCHANTMENT_BOOSTERS.contains(block)) {
						int currentCount = ret.getOrDefault(block, 0);
						ret.put(block, currentCount + 1);
					}
				}
			}
		}
		return ret;
	}
}
