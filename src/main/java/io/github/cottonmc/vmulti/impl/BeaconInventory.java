package io.github.cottonmc.vmulti.impl;

import io.github.cottonmc.vmulti.api.VMultiAPI;
import net.minecraft.inventory.BasicInventory;
import net.minecraft.item.ItemStack;

public class BeaconInventory extends BasicInventory {

	public BeaconInventory(int size) {
		super(size);
	}

	public boolean isValidInvStack(int slot, ItemStack stack) {
		return VMultiAPI.BEACON_ACTIVATORS.contains(stack.getItem());
	}

	public int getInvMaxStackAmount() {
		return 1;
	}
}
