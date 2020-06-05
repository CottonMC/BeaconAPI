package io.github.cottonmc.vmulti.rei;

import io.github.cottonmc.vmulti.api.VMultiAPI;
import me.shedaniel.rei.api.RecipeHelper;
import me.shedaniel.rei.api.plugins.REIPluginV0;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
public class VMultiREIPlugin implements REIPluginV0 {
	//TODO: are these gonna go here or in REI?
//	public static final Identifier BEACON_BASE = new Identifier(VMultiAPI.MODID, "beacon_base");
//	public static final Identifier BEACON_ACTIVATOR = new Identifier(VMultiAPI.MODID, "beacon_activator");
	public static final Identifier CONDUIT_ACTIVATOR = new Identifier(VMultiAPI.MODID, "conduit_activator");
	public static final Identifier ENCHANTMENT_BOOSTER = new Identifier(VMultiAPI.MODID, "enchantment_booster");

	@Override
	public Identifier getPluginIdentifier() {
		return new Identifier("vmulti", "rei_plugin");
	}

	@Override
	public void registerPluginCategories(RecipeHelper helper) {
//		helper.registerCategory(new VMultiCategory(Items.BEACON, BEACON_BASE));
//		helper.registerCategory(new VMultiCategory(Items.BEACON, BEACON_ACTIVATOR));
		helper.registerCategory(new VMultiCategory(Items.CONDUIT, CONDUIT_ACTIVATOR));
		helper.registerCategory(new VMultiCategory(Items.ENCHANTING_TABLE, ENCHANTMENT_BOOSTER));
	}

	@Override
	public void registerRecipeDisplays(RecipeHelper helper) {
//		addMultiDisplay(helper, Items.BEACON, BEACON_BASE, VMultiAPI.getBeaconBaseStacks());
//		addMultiDisplay(helper, Items.BEACON, BEACON_ACTIVATOR, VMultiAPI.getBeaconActivatorStacks());
		addMultiDisplay(helper, Items.CONDUIT, CONDUIT_ACTIVATOR, VMultiAPI.getConduitFrameStacks());
		addMultiDisplay(helper, Items.ENCHANTING_TABLE, ENCHANTMENT_BOOSTER, VMultiAPI.getEnchantmentBoosterStacks());
	}

	private static void addMultiDisplay(RecipeHelper helper, Item icon, Identifier id, List<ItemStack> stacks) {
		for (int i = 0; i < stacks.size(); i += MathHelper.clamp(48, 1, stacks.size() - i)) {
			List<ItemStack> thisStacks = new ArrayList<>();
			for (int j = i; j < i + 48; j++)
				if (j < stacks.size())
					thisStacks.add(stacks.get(j));
			helper.registerDisplay(id, new VMultiDisplay(id, MathHelper.floor(i / 48f), thisStacks, new ItemStack[]{new ItemStack(icon)}));
		}
	}

	@Override
	public void registerOthers(RecipeHelper helper) {
//		helper.registerAutoCraftButtonArea(BEACON_BASE, bounds -> null);
//		helper.registerAutoCraftButtonArea(BEACON_ACTIVATOR, bounds -> null);
		helper.registerAutoCraftButtonArea(CONDUIT_ACTIVATOR, bounds -> null);
		helper.registerAutoCraftButtonArea(ENCHANTMENT_BOOSTER, bounds -> null);
	}
}
