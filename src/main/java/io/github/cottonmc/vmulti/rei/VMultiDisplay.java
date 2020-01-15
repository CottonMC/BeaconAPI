package io.github.cottonmc.vmulti.rei;

import me.shedaniel.rei.api.EntryStack;
import me.shedaniel.rei.api.RecipeDisplay;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class VMultiDisplay implements RecipeDisplay {
	private Identifier id;
	private List<EntryStack> order, allItems;
	private List<EntryStack> output;
	private int page;

	public VMultiDisplay(Identifier id, int page, List<ItemStack> order, ItemStack[] output) {
		this.id = id;
		this.page = page;
		this.order = order.stream().map(EntryStack::create).collect(Collectors.toList());
		this.output = Arrays.asList(output).stream().map(EntryStack::create).collect(Collectors.toList());
		this.allItems = order.stream().map(EntryStack::create).collect(Collectors.toList());
	}

	public int getPage() {
		return page;
	}

	@Override
	public List<List<EntryStack>> getInputEntries() {
		List<List<EntryStack>> lists = new ArrayList<>();
		for (EntryStack allItem : allItems) {
			lists.add(Collections.singletonList(allItem));
		}
		return lists;
	}

	@Override
	public List<EntryStack> getOutputEntries() {
		return output;
	}

	@Override
	public Identifier getRecipeCategory() {
		return id;
	}

	@Override
	public List<List<EntryStack>> getRequiredEntries() {
		return Collections.singletonList(allItems);
	}

	public List<EntryStack> getItemsByOrder() {
		return order;
	}
}
