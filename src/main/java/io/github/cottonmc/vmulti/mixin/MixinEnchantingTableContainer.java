package io.github.cottonmc.vmulti.mixin;

import io.github.cottonmc.vmulti.impl.Consumers;
import io.github.cottonmc.vmulti.impl.EnchantingTableAccessors;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.screen.Property;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.EnchantmentScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;

@Mixin(EnchantmentScreenHandler.class)
public abstract class MixinEnchantingTableContainer implements EnchantingTableAccessors {
	@Shadow @Final private Random random;

	@Shadow @Final private Property seed;

	@Shadow protected abstract List<EnchantmentLevelEntry> generateEnchantments(ItemStack itemStack_1, int int_1, int int_2);

	@Shadow @Final private Inventory inventory;

	@Override
	public Random vmulti_getRandom() {
		return random;
	}

	@Override
	public Property vmulti_getSeed() {
		return seed;
	}

	@Override
	public List<EnchantmentLevelEntry> vmulti_getEnchantments(ItemStack stack, int slot, int enchantingPower) {
		return generateEnchantments(stack, slot, enchantingPower);
	}

//	@Redirect(method = "onContentChanged", at = @At(value = "INVOKE", target = "Lnet/minecraft/container/BlockContext;run(Ljava/util/function/BiConsumer;)V"))
//	private void redirectChangeConsumer(ScreenHandlerContext context, BiConsumer<World, BlockPos> original) {
//		context.run(Consumers.getEnchantingScanner((EnchantmentScreenHandler) (Object) this, inventory.getStack(0)));
//	}

	@ModifyArg(method = "onContentChanged", at = @At(value = "INVOKE", target = "Lnet/minecraft/screen/ScreenHandlerContext;run(Ljava/util/function/BiConsumer;)V"))
	BiConsumer<World, BlockPos> getChangeConsumer(BiConsumer<World, BlockPos> original) {
		return Consumers.getEnchantingScanner((EnchantmentScreenHandler) (Object)this, inventory.getStack(0));
	}
}
