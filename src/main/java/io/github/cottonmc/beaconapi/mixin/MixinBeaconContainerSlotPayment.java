package io.github.cottonmc.beaconapi.mixin;

import io.github.cottonmc.beaconapi.api.BeaconTags;
import io.github.cottonmc.beaconapi.impl.BeaconInventory;
import net.minecraft.container.BeaconContainer;
import net.minecraft.container.Slot;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net/minecraft/container/BeaconContainer$SlotPayment")
public abstract class MixinBeaconContainerSlotPayment extends Slot {

	public MixinBeaconContainerSlotPayment(Inventory inv, int slotNum, int x, int y) {
		super(inv, slotNum, x, y);
	}

	@Inject(method = "canInsert", at = @At("HEAD"), cancellable = true)
	private void taggableInsertSlot(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
		if (BeaconTags.BEACON_ACTIVATORS.contains(stack.getItem())) cir.setReturnValue(true);
	}
}
