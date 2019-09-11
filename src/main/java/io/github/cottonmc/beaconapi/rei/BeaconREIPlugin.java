package io.github.cottonmc.beaconapi.rei;

import io.github.cottonmc.beaconapi.api.BeaconTags;
import me.shedaniel.rei.api.RecipeHelper;
import me.shedaniel.rei.api.plugins.REIPluginV0;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.SemanticVersion;
import net.fabricmc.loader.util.version.VersionParsingException;
import net.minecraft.util.Identifier;

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
		helper.registerDisplay(BASE, new BeaconDisplay(BASE, BeaconTags.getBaseStacks()));
		helper.registerDisplay(ACTIVATOR, new BeaconDisplay(ACTIVATOR, BeaconTags.getActivatorStacks()));
	}
}
