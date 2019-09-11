package io.github.cottonmc.beaconapi.api;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.block.Block;

public interface BeaconBlocks {
	Object2IntMap<Block> getBeaconBlocks();
}
