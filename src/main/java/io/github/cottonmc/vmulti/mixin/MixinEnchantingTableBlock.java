package io.github.cottonmc.vmulti.mixin;

import io.github.cottonmc.vmulti.api.VMultiAPI;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.EnchantingTableBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EnchantingTableBlock.class)
public class MixinEnchantingTableBlock {

	@Redirect(method = "randomDisplayTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;getBlock()Lnet/minecraft/block/Block;"))
	private Block getReplacedBlock(BlockState state) {
		if (VMultiAPI.ENCHANTMENT_BOOSTERS.contains(state.getBlock())) return Blocks.BOOKSHELF;
		else return Blocks.AIR;
	}
}
