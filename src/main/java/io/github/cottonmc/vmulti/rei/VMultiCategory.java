package io.github.cottonmc.vmulti.rei;

import com.google.common.collect.Lists;
import me.shedaniel.math.api.Point;
import me.shedaniel.math.api.Rectangle;
import me.shedaniel.rei.api.RecipeCategory;
import me.shedaniel.rei.api.Renderer;
import me.shedaniel.rei.gui.renderers.RecipeRenderer;
import me.shedaniel.rei.gui.widget.RecipeBaseWidget;
import me.shedaniel.rei.gui.widget.SlotWidget;
import me.shedaniel.rei.gui.widget.Widget;
import me.shedaniel.rei.plugin.DefaultPlugin;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GuiLighting;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.function.Supplier;

import static com.mojang.blaze3d.platform.GlStateManager.color4f;

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
	public Renderer getIcon() {
		return Renderer.fromItemStack(new ItemStack(icon));
	}

	@Override
	public String getCategoryName() {
		return I18n.translate("category." + id.getNamespace() + "." + id.getPath());
	}

	@Override
	public RecipeRenderer getSimpleRenderer(VMultiDisplay display) {
		return new RecipeRenderer() {
			@Override
			public int getHeight() {
				return 10 + MinecraftClient.getInstance().textRenderer.fontHeight;
			}

			@Override
			public void render(int x, int y, double mouseX, double mouseY, float delta) {
				MinecraftClient.getInstance().textRenderer.draw(I18n.translate("text.vmulti.display.page", display.getPage() + 1), x + 5, y + 6, -1);
			}
		};
	}

	@Override
	public List<Widget> setupDisplay(Supplier<VMultiDisplay> recipeDisplaySupplier, Rectangle bounds) {
		List<Widget> widgets = Lists.newArrayList();
		Point startingPoint = new Point(bounds.x + bounds.width - 55, bounds.y + 110);
		widgets.add(new RecipeBaseWidget(bounds) {
			@Override
			public void render(int mouseX, int mouseY, float partialTicks) {
				color4f(1.0F, 1.0F, 1.0F, 1.0F);
				GuiLighting.disable();
				MinecraftClient.getInstance().getTextureManager().bindTexture(DefaultPlugin.getDisplayTexture());
				this.blit(startingPoint.x, startingPoint.y, 28, 221, 55, 26);
			}
		});
		List<ItemStack> stacks = recipeDisplaySupplier.get().getInput().get(0);
		int i = 0;
		for (int y = 0; y < 6; y++)
			for (int x = 0; x < 8; x++) {
				widgets.add(new SlotWidget(bounds.getCenterX() - 72 + x * 18, bounds.y + y * 18, stacks.size() > i ? Renderer.fromItemStack(stacks.get(i)) : Renderer.empty(), true, true, true));
				i++;
			}
		widgets.add(new SlotWidget(startingPoint.x + 34, startingPoint.y + 5, Renderer.fromItemStacks(recipeDisplaySupplier.get().getOutput()), false, true, true));
		return widgets;
	}

	@Override
	public int getDisplayHeight() {
		return 140;
	}
}
