package io.github.cottonmc.vmulti.rei;

import com.google.common.collect.Lists;
import me.shedaniel.math.api.Point;
import me.shedaniel.math.api.Rectangle;
import me.shedaniel.rei.api.EntryStack;
import me.shedaniel.rei.api.RecipeCategory;
import me.shedaniel.rei.gui.entries.RecipeEntry;
import me.shedaniel.rei.gui.widget.EntryWidget;
import me.shedaniel.rei.gui.widget.QueuedTooltip;
import me.shedaniel.rei.gui.widget.RecipeBaseWidget;
import me.shedaniel.rei.gui.widget.Widget;
import me.shedaniel.rei.plugin.DefaultPlugin;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import javax.annotation.Nullable;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

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

			@Nullable
			@Override
			public QueuedTooltip getTooltip(int mouseX, int mouseY) {
				return null;
			}

			@Override
			public void render(Rectangle rectangle, int mouseX, int mouseY, float delta) {
				MinecraftClient.getInstance().textRenderer.draw(I18n.translate("text.rei.composting.page", display.getPage() + 1), rectangle.x + 5, rectangle.y + 6, -1);			}
		};
	}

	@Override
	public List<Widget> setupDisplay(Supplier<VMultiDisplay> recipeDisplaySupplier, Rectangle bounds) {
		List<Widget> widgets = Lists.newArrayList();
		Point startingPoint = new Point(bounds.x + bounds.width - 55, bounds.y + 110);
		widgets.add(new RecipeBaseWidget(bounds) {
			@Override
			public void render(int mouseX, int mouseY, float partialTicks) {
				MinecraftClient.getInstance().getTextureManager().bindTexture(DefaultPlugin.getDisplayTexture());
				this.blit(startingPoint.x, startingPoint.y, 28, 221, 55, 26);
			}
		});
		List<EntryStack> stacks = new LinkedList<>(recipeDisplaySupplier.get().getItemsByOrder());
		int i = 0;
		for (int y = 0; y < 6; y++)
			for (int x = 0; x < 8; x++) {
				int finalI = i;
				EntryStack entryStack = stacks.size() > i ? stacks.get(finalI) : EntryStack.empty();
				widgets.add(EntryWidget.create(bounds.getCenterX() - 72 + x * 18, bounds.y + y * 18).entry(entryStack));
				i++;
			}
		widgets.add(EntryWidget.create(startingPoint.x + 34, startingPoint.y + 5).entries(recipeDisplaySupplier.get().getOutputEntries()).noBackground());
		return widgets;
	}

	@Override
	public int getDisplayHeight() {
		return 140;
	}
}
