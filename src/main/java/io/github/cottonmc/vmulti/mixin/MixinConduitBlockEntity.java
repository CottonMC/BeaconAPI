package io.github.cottonmc.vmulti.mixin;

import io.github.cottonmc.vmulti.api.ComponentCollector;
import io.github.cottonmc.vmulti.api.VMultiAPI;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.ConduitBlockEntity;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ConduitBlockEntity.class)
public class MixinConduitBlockEntity implements ComponentCollector {
	private Object2IntMap<Block> activators = new Object2IntOpenHashMap<>();

	@Shadow @Final @Mutable
	private static Block[] ACTIVATING_BLOCKS = VMultiAPI.CONDUIT_ACTIVATORS.values().toArray(new Block[]{});

	@Override
	public Object2IntMap<Block> getActivatingBlocks() {
		return activators;
	}

	@Inject(method = "updateActivatingBlocks", at = @At("HEAD"))
	private void clearActivatorList(CallbackInfoReturnable<Boolean> cir) {
		activators.clear();
	}

	@Inject(method = "updateActivatingBlocks", at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z"), locals = LocalCapture.CAPTURE_FAILEXCEPTION, remap = false)
	private void addActivators(CallbackInfoReturnable<Boolean> cir, int x, int y, int z, BlockPos newPos, BlockState state) {
		if (VMultiAPI.ENCHANTMENT_BOOSTERS.contains(state.getBlock())) {
			int currentCount = activators.getOrDefault(state.getBlock(), 0);
			activators.put(state.getBlock(), currentCount + 1);
		}
	}
}
