package io.github.cottonmc.beaconapi.rei;

import me.shedaniel.rei.api.RecipeCategory;
import me.shedaniel.rei.api.Renderer;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

public class BeaconCategory implements RecipeCategory<BeaconDisplay> {
	private Identifier id;

	public BeaconCategory(Identifier id) {
		this.id = id;
	}

	@Override
	public Identifier getIdentifier() {
		return id;
	}

	@Override
	public Renderer getIcon() {
		return Renderer.fromItemStack(new ItemStack(Items.BEACON));
	}

	@Override
	public String getCategoryName() {
		return I18n.translate("category." + id.getNamespace() + "." + id.getPath());
	}

	//TODO: display
}
