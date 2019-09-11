package io.github.cottonmc.beaconapi.impl;

import io.github.cottonmc.beaconapi.api.BeaconTags;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.inventory.BasicInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

public class BeaconInventory extends BasicInventory {

	public BeaconInventory(int size) {
		super(size);
	}

	public boolean isValidInvStack(int slot, ItemStack stack) {
		return BeaconTags.BEACON_ACTIVATORS.contains(stack.getItem());
	}

	public int getInvMaxStackAmount() {
		return 1;
	}
}
