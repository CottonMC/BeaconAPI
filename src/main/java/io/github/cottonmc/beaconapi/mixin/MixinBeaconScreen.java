package io.github.cottonmc.beaconapi.mixin;

import io.github.cottonmc.beaconapi.api.BeaconTags;
import net.minecraft.client.gui.screen.ingame.AbstractContainerScreen;
import net.minecraft.client.gui.screen.ingame.BeaconScreen;
import net.minecraft.container.BeaconContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Mixin(BeaconScreen.class)
public abstract class MixinBeaconScreen extends AbstractContainerScreen<BeaconContainer> {
	private static final Item[] VANILLA_ACTIVATORS = new Item[] { Items.EMERALD, Items.DIAMOND, Items.GOLD_INGOT, Items.IRON_INGOT };
	private float time = 0F;

	public MixinBeaconScreen(BeaconContainer container, PlayerInventory playerInv, Text name) {
		super(container, playerInv, name);
	}

	@Inject(method = "drawBackground", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/ItemRenderer;renderGuiItem(Lnet/minecraft/item/ItemStack;II)V"), cancellable = true)
	private void drawItems(float partialTicks, int x, int y, CallbackInfo ci) {
		time += partialTicks;
		Item[] toDisplay = getActivatorArray();
		int drawX = ((this.width - this.containerWidth) / 2) + 42;
		int drawY = ((this.height - this.containerHeight) / 2) + 109;
		if (toDisplay.length <= 4) {
			int offset = 0;
			for (int i = 0; i < toDisplay.length; i++) {
				ItemStack stack = new ItemStack(toDisplay[i]);
				this.itemRenderer.renderGuiItem(stack, drawX + offset, drawY);
				offset += 22;
			}
		} else {
			int currentRotation = MathHelper.floor(time / 20.0F);
			int offset = 0;
			for (int i = 0; i < 4; i++) {
				ItemStack stack = new ItemStack(toDisplay[(currentRotation + i) % toDisplay.length]);
				this.itemRenderer.renderGuiItem(stack, drawX + offset, drawY);
				offset += 22;
			}
		}
		this.itemRenderer.zOffset = 0.0F;
		ci.cancel();
	}

	private Item[] getActivatorArray() {
		List<Item> ret = new ArrayList<>();
		List<Item> tag = new ArrayList<>(BeaconTags.BEACON_ACTIVATORS.values());
		for (Item activator : VANILLA_ACTIVATORS) {
			if (tag.contains(activator)) {
				ret.add(activator);
				tag.remove(activator);
			}
		}
		tag.sort(Comparator.comparing(item -> Registry.ITEM.getId(item).toString()));
		ret.addAll(tag);
		return ret.toArray(new Item[0]);
	}
}
