package io.github.cottonmc.vmulti.api;

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

public class VMultiAPI implements ModInitializer {
	public static final String MODID = "vmulti";

	private static final Item[] VANILLA_BEACON_BASES = new Item[] { Items.EMERALD_BLOCK, Items.DIAMOND_BLOCK, Items.GOLD_BLOCK, Items.IRON_BLOCK };
	private static final Item[] VANILLA_BEACON_ACTIVATORS = new Item[] { Items.EMERALD, Items.DIAMOND, Items.GOLD_INGOT, Items.IRON_INGOT };

	private static final Item[] VANILLA_CONDUIT_ACTIVATORS = new Item[] { Items.PRISMARINE, Items.PRISMARINE_BRICKS, Items.SEA_LANTERN, Items.DARK_PRISMARINE };

	public static final Tag<Block> BEACON_BASES = TagRegistry.block(new Identifier(MODID, "beacon_bases"));
	public static final Tag<Item> BEACON_ACTIVATORS = TagRegistry.item(new Identifier(MODID, "beacon_activators"));

	public static final Tag<Block> CONDUIT_ACTIVATORS = TagRegistry.block(new Identifier(MODID, "conduit_activators"));

	public static final Tag<Block> ENCHANTMENT_BOOSTERS = TagRegistry.block(new Identifier(MODID, "enchantment_boosters"));


	@Override
	public void onInitialize() {
		//TODO: add a data pack thing for choosing beacon effects?
	}

	public static List<ItemStack> getBeaconBaseStacks() {
		List<ItemStack> ret = new ArrayList<>();
		List<Block> blockTag = new ArrayList<>(VMultiAPI.BEACON_BASES.values());
		List<Item> tag = new ArrayList<>();
		for (Block block : blockTag) {
			Item item = block.asItem();
			if (item != Items.AIR) tag.add(item);
		}
		for (Item base : VANILLA_BEACON_BASES) {
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

	public static List<ItemStack> getBeaconActivatorStacks() {
		List<ItemStack> ret = new ArrayList<>();
		List<Item> tag = new ArrayList<>(VMultiAPI.BEACON_ACTIVATORS.values());
		for (Item activator : VANILLA_BEACON_ACTIVATORS) {
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

	public static List<ItemStack> getConduitFrameStacks() {
		List<ItemStack> ret = new ArrayList<>();
		List<Block> blockTag = new ArrayList<>(VMultiAPI.CONDUIT_ACTIVATORS.values());
		List<Item> tag = new ArrayList<>();
		for (Block block : blockTag) {
			Item item = block.asItem();
			if (item != Items.AIR) tag.add(item);
		}
		for (Item base : VANILLA_CONDUIT_ACTIVATORS) {
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

	public static List<ItemStack> getEnchantmentBoosterStacks() {
		List<ItemStack> ret = new ArrayList<>();
		List<Block> blockTag = new ArrayList<>(VMultiAPI.ENCHANTMENT_BOOSTERS.values());
		List<Item> tag = new ArrayList<>();
		for (Block block : blockTag) {
			Item item = block.asItem();
			if (item != Items.AIR) tag.add(item);
		}
		if (tag.contains(Items.BOOKSHELF)) {
			ret.add(new ItemStack(Items.BOOKSHELF));
			tag.remove(Items.BOOKSHELF);
		}
		tag.sort(Comparator.comparing(item -> Registry.ITEM.getId(item).toString()));
		for (Item item : tag) {
			ret.add(new ItemStack(item));
		}
		return ret;
	}
}
