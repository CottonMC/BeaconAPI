package io.github.cottonmc.beaconapi.rei;

import me.shedaniel.rei.api.RecipeDisplay;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

import java.util.Collections;
import java.util.List;

public class BeaconDisplay implements RecipeDisplay {
	private Identifier id;
	private List<ItemStack> inputs;

	public BeaconDisplay(Identifier id, List<ItemStack> inputs) {
		this.id = id;
		this.inputs = inputs;
	}

	@Override
	public List<List<ItemStack>> getInput() {
		return Collections.singletonList(inputs);
	}

	@Override
	public List<ItemStack> getOutput() {
		return Collections.singletonList(new ItemStack(Items.BEACON));
	}

	@Override
	public Identifier getRecipeCategory() {
		return id;
	}
}
