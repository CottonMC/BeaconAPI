package io.github.cottonmc.vmulti.rei;

import com.google.common.collect.Lists;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.EntryStack;
import me.shedaniel.rei.api.RecipeCategory;
import me.shedaniel.rei.api.widgets.Widgets;
import me.shedaniel.rei.gui.entries.RecipeEntry;
import me.shedaniel.rei.gui.widget.Widget;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import java.util.LinkedList;
import java.util.List;

@Environment(EnvType.CLIENT)
public class VMultiCategory implements RecipeCategory<VMultiDisplay> {
	private Item icon;
	private Identifier id;

	public VMultiCategory(Item icon, Identifier id) {
		this.icon = icon;
		this.id = id;
	}

	@Override
	public Identifier getIdentifier() {
		return id;
	}

	@Override
	public EntryStack getLogo() {
		return EntryStack.create(new ItemStack(icon));
	}

	@Override
	public String getCategoryName() {
		return I18n.translate("category." + id.getNamespace() + "." + id.getPath());
	}

	@Override
	public RecipeEntry getSimpleRenderer(VMultiDisplay display) {
		return new RecipeEntry() {
			@Override
			public int getHeight() {
				return 10 + MinecraftClient.getInstance().textRenderer.fontHeight;
			}

			@Override
			public void render(MatrixStack matrices, Rectangle rectangle, int mouseX, int mouseY, float delta) {
				MinecraftClient.getInstance().textRenderer.draw(matrices, I18n.translate("text.rei.composting.page", display.getPage() + 1), rectangle.x + 5, rectangle.y + 6, -1);			}
		};
	}

	@Override
	public List<Widget> setupDisplay(VMultiDisplay recipeDisplay, Rectangle bounds) {
		List<Widget> widgets = Lists.newArrayList();
		Point startingPoint = new Point(bounds.x + bounds.width - 55, bounds.y + 110);
		widgets.add(Widgets.createRecipeBase(bounds));
		List<EntryStack> stacks = new LinkedList<>(recipeDisplay.getItemsByOrder());
		int i = 0;
		for (int y = 0; y < 6; y++)
			for (int x = 0; x < 8; x++) {
				int finalI = i;
				EntryStack entryStack = stacks.size() > i ? stacks.get(finalI) : EntryStack.empty();
				widgets.add(Widgets.createSlot(new Point(bounds.getCenterX() - 72 + x * 18, bounds.y + y * 18)).entry(entryStack));
				i++;
			}
		widgets.add(Widgets.createSlot(new Point(startingPoint.x + 34, startingPoint.y + 5)).entries(recipeDisplay.getOutputEntries()).disableBackground());
		return widgets;
	}

	@Override
	public int getDisplayHeight() {
		return 140;
	}
}
