package io.github.cottonmc.vmulti.mixin;

import io.github.cottonmc.vmulti.api.VMultiAPI;
import net.minecraft.client.gui.screen.ingame.BeaconScreen;
import net.minecraft.client.gui.screen.ingame.ContainerScreen;
import net.minecraft.container.BeaconContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BeaconScreen.class)
public abstract class MixinBeaconScreen extends ContainerScreen<BeaconContainer> {
	private float time = 0F;

	public MixinBeaconScreen(BeaconContainer container, PlayerInventory playerInv, Text name) {
		super(container, playerInv, name);
	}

	@Inject(method = "drawBackground", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/ItemRenderer;renderGuiItem(Lnet/minecraft/item/ItemStack;II)V"), cancellable = true)
	private void drawItems(float partialTicks, int x, int y, CallbackInfo ci) {
		time += partialTicks;
		ItemStack[] toDisplay = VMultiAPI.getBeaconActivatorStacks().toArray(new ItemStack[0]);
		int drawX = ((this.width - this.containerWidth) / 2) + 20;
		int drawY = ((this.height - this.containerHeight) / 2) + 109;
		if (toDisplay.length <= 5) {
			int offset = 0;
			for (int i = 0; i < toDisplay.length; i++) {
				ItemStack stack = toDisplay[i];
				this.itemRenderer.renderGuiItem(stack, drawX + offset, drawY);
				offset += 22;
				//fix uneven distances with new arrangement
				if (i == 0) offset -= 1;
				if (i == 2) offset += 1;
			}
		} else {
			int currentRotation = MathHelper.floor(time / 20.0F);
			int offset = 0;
			for (int i = 0; i < 5; i++) {
				ItemStack stack = toDisplay[(currentRotation + i) % toDisplay.length];
				this.itemRenderer.renderGuiItem(stack, drawX + offset, drawY);
				offset += 22;
				//fix uneven distances with new arrangement
				if (i == 0) offset -= 1;
				if (i == 2) offset += 1;
			}
		}
		this.itemRenderer.zOffset = 0.0F;
		ci.cancel();
	}

}
