package io.github.cottonmc.beaconapi.api;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

public class BeaconTags implements ModInitializer {
	public static final Tag<Block> BEACON_BASES = TagRegistry.block(new Identifier("beaconapi", "beacon_bases"));
	public static final Tag<Item> BEACON_ACTIVATORS = TagRegistry.item(new Identifier("beaconapi", "beacon_activators"));

	@Override
	public void onInitialize() {
		//TODO: add a data pack thing for choosing beacon effects?
	}
}
