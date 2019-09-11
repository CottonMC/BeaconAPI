package io.github.cottonmc.beaconapi.rei;

import io.github.cottonmc.beaconapi.api.BeaconTags;
import me.shedaniel.rei.api.RecipeHelper;
import me.shedaniel.rei.api.plugins.REIPluginV0;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.SemanticVersion;
import net.fabricmc.loader.util.version.VersionParsingException;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
public class BeaconREIPlugin implements REIPluginV0 {
	public static final Identifier BASE = new Identifier("beaconapi", "beacon_base");
	public static final Identifier ACTIVATOR = new Identifier("beaconapi", "beacon_activator");

	@Override
	public SemanticVersion getMinimumVersion() throws VersionParsingException {
		return SemanticVersion.parse("3.0-pre");
	}

	@Override
	public Identifier getPluginIdentifier() {
		return new Identifier("beaconapi", "rei_plugin");
	}

	@Override
	public void registerPluginCategories(RecipeHelper helper) {
		helper.registerCategory(new BeaconCategory(BASE));
		helper.registerCategory(new BeaconCategory(ACTIVATOR));
	}

	@Override
	public void registerRecipeDisplays(RecipeHelper helper) {
		addBeaconDisplay(helper, BASE, BeaconTags.getBaseStacks());
		addBeaconDisplay(helper, ACTIVATOR, BeaconTags.getActivatorStacks());
	}

	private static void addBeaconDisplay(RecipeHelper helper, Identifier id, List<ItemStack> stacks) {
		for (int i = 0; i < stacks.size(); i += MathHelper.clamp(48, 1, stacks.size() - i)) {
			List<ItemStack> thisStacks = new ArrayList<>();
			for (int j = i; j < i + 48; j++)
				if (j < stacks.size())
					thisStacks.add(stacks.get(j));
			helper.registerDisplay(id, new BeaconDisplay(id, MathHelper.floor(i / 48f), thisStacks, stacks));
		}
	}

	@Override
	public void registerOthers(RecipeHelper helper) {
		helper.registerAutoCraftButtonArea(BASE, bounds -> null);
		helper.registerAutoCraftButtonArea(ACTIVATOR, bounds -> null);
	}
}
