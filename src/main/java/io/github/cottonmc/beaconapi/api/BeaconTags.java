package io.github.cottonmc.beaconapi.api;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BeaconTags implements ModInitializer {
	private static final Item[] VANILLA_BASES = new Item[] { Items.EMERALD_BLOCK, Items.DIAMOND_BLOCK, Items.GOLD_BLOCK, Items.IRON_BLOCK };
	private static final Item[] VANILLA_ACTIVATORS = new Item[] { Items.EMERALD, Items.DIAMOND, Items.GOLD_INGOT, Items.IRON_INGOT };

	public static final Tag<Block> BEACON_BASES = TagRegistry.block(new Identifier("beaconapi", "beacon_bases"));
	public static final Tag<Item> BEACON_ACTIVATORS = TagRegistry.item(new Identifier("beaconapi", "beacon_activators"));

	@Override
	public void onInitialize() {
		//TODO: add a data pack thing for choosing beacon effects?
	}

	public static List<ItemStack> getBaseStacks() {
		List<ItemStack> ret = new ArrayList<>();
		List<Block> blockTag = new ArrayList<>(BeaconTags.BEACON_BASES.values());
		List<Item> tag = new ArrayList<>();
		for (Block block : blockTag) {
			Item item = block.asItem();
			if (item != Items.AIR) tag.add(item);
		}
		for (Item base : VANILLA_BASES) {
			if (tag.contains(base)) {
				ret.add(new ItemStack(base));
				tag.remove(base);
			}
		}
		tag.sort(Comparator.comparing(item -> Registry.ITEM.getId(item).toString()));
		for (Item item : tag) {
			ret.add(new ItemStack(item));
		}
		return ret;
	}

	public static List<ItemStack> getActivatorStacks() {
		List<ItemStack> ret = new ArrayList<>();
		List<Item> tag = new ArrayList<>(BeaconTags.BEACON_ACTIVATORS.values());
		for (Item activator : VANILLA_ACTIVATORS) {
			if (tag.contains(activator)) {
				ret.add(new ItemStack(activator));
				tag.remove(activator);
			}
		}
		tag.sort(Comparator.comparing(item -> Registry.ITEM.getId(item).toString()));
		for (Item item : tag) {
			ret.add(new ItemStack(item));
		}
		return ret;
	}
}
