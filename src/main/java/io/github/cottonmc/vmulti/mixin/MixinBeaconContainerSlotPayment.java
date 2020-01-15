package io.github.cottonmc.vmulti.mixin;

import io.github.cottonmc.vmulti.api.VMultiAPI;
import net.minecraft.container.Slot;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net/minecraft/container/BeaconContainer$SlotPayment")
public abstract class MixinBeaconContainerSlotPayment {

	@Inject(method = "Lnet/minecraft/container/BeaconContainer$SlotPayment;canInsert(Lnet/minecraft/item/ItemStack;)Z", at = @At("HEAD"), cancellable = true)
	private void taggableInsertSlot(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
		if (VMultiAPI.BEACON_ACTIVATORS.contains(stack.getItem())) cir.setReturnValue(true);
	}
}
