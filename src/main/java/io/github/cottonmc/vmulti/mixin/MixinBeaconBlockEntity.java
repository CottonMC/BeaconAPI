package io.github.cottonmc.vmulti.mixin;

import io.github.cottonmc.vmulti.api.ComponentCollector;
import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BeaconBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(BeaconBlockEntity.class)
public abstract class MixinBeaconBlockEntity extends BlockEntity implements ComponentCollector {
	private Object2IntMap<Block> beaconBlocks = new Object2IntArrayMap<>();

	public MixinBeaconBlockEntity(BlockEntityType<?> type) {
		super(type);
	}

	@Inject(method = "updateLevel", at = @At("HEAD"))
	public void clearBlockMap(int x, int y, int z, CallbackInfo ci) {
		beaconBlocks.clear();
	}

	@Inject(method = "updateLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;getBlockState(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/BlockState;"), locals = LocalCapture.CAPTURE_FAILEXCEPTION)
	public void appendBeaconBlocks(int x, int y, int z, CallbackInfo info, int i, int j, boolean bl, int k, int l) {
		BlockState state = world.getBlockState(new BlockPos(k, j, l));
		if (state.matches(BlockTags.BEACON_BASE_BLOCKS)) {
			beaconBlocks.put(state.getBlock(), beaconBlocks.getInt(state.getBlock()) + 1);
		}
	}

	@Override
	public Object2IntMap<Block> getActivatingBlocks() {
		return beaconBlocks;
	}
}
