package io.github.cottonmc.vmulti.mixin;

import io.github.cottonmc.vmulti.api.VMultiAPI;
import net.minecraft.block.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EnchantingTableBlock.class)
public class MixinEnchantingTableBlock {

	@Redirect(method = "randomDisplayTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;getBlockState(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/BlockState;"))
	private BlockState getMatchingBlock(World world, BlockPos pos) {
		if (world.getBlockState(pos).isIn(VMultiAPI.ENCHANTMENT_BOOSTERS)) {
			return Blocks.BOOKSHELF.getDefaultState();
		}
		return world.getBlockState(pos);
	}
}
