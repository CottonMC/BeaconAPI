package io.github.cottonmc.vmulti.mixin;

import io.github.cottonmc.vmulti.impl.Consumers;
import io.github.cottonmc.vmulti.impl.EnchantingTableAccessors;
import net.minecraft.container.EnchantingTableContainer;
import net.minecraft.container.Property;
import net.minecraft.enchantment.InfoEnchantment;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;

@Mixin(EnchantingTableContainer.class)
public abstract class MixinEnchantingTableContainer implements EnchantingTableAccessors {
	@Shadow @Final private Random random;

	@Shadow @Final private Property seed;

	@Shadow protected abstract List<InfoEnchantment> getRandomEnchantments(ItemStack itemStack_1, int int_1, int int_2);

	@Shadow @Final private Inventory inventory;

	@Shadow @Final public int[] enchantmentPower;

	@Shadow @Final public int[] enchantmentId;

	@Shadow @Final public int[] enchantmentLevel;

	@Override
	public Random vmulti_getRandom() {
		return random;
	}

	@Override
	public Property vmulti_getSeed() {
		return seed;
	}

	@Override
	public List<InfoEnchantment> vmulti_getEnchantments(ItemStack stack, int slot, int enchantingPower) {
		return getRandomEnchantments(stack, slot, enchantingPower);
	}

	@ModifyArg(method = "onContentChanged", at = @At(value = "INVOKE", target = "Lnet/minecraft/container/BlockContext;run(Ljava/util/function/BiConsumer;)V"))
	BiConsumer<World, BlockPos> getChangeConsumer(BiConsumer<World, BlockPos> original) {
		return Consumers.getEnchantingScanner((EnchantingTableContainer)(Object)this, inventory.getInvStack(0));
	}
}
