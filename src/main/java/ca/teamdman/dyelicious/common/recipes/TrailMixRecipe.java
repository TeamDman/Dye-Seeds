package ca.teamdman.dyelicious.common.recipes;

import ca.teamdman.dyelicious.common.items.TrailMixItem;
import ca.teamdman.dyelicious.common.registry.ModRecipes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

public class TrailMixRecipe extends CustomRecipe {
	public TrailMixRecipe(ResourceLocation pId, CraftingBookCategory pCategory) {
		super(pId, pCategory);
	}

	/**
	 * Trail mix should be a shapeless recipe that requires exactly:
	 * - 1x vanilla wheat seeds
	 * - 1x anything edible
	 * - 1x anything else, which may also be edible
	 */
	@Override
	public boolean matches(CraftingContainer pContainer, Level pLevel) {
		return !assemble(pContainer).isEmpty();
	}

	@Override
	public ItemStack assemble(CraftingContainer pContainer) {
		var foundFood = ItemStack.EMPTY;
		var foundSeeds  = ItemStack.EMPTY;
		var foundOther  = ItemStack.EMPTY;
		for (int i = 0; i < pContainer.getContainerSize(); i++) {
			var stack = pContainer.getItem(i);
			if (stack.isEmpty()) continue;
			if (stack.getItem().isEdible()) {
				if (foundFood.isEmpty()) {
					foundFood = stack;
				} else {
					if (!foundOther.isEmpty()) return ItemStack.EMPTY;
					foundOther = stack;
				}
			} else if (stack.getItem() == Items.WHEAT_SEEDS) {
				if (!foundSeeds.isEmpty()) return ItemStack.EMPTY;
				foundSeeds = stack;
			} else {
				if (!foundOther.isEmpty()) return ItemStack.EMPTY;
				foundOther = stack;
			}
		}
		if (foundFood.isEmpty() || foundSeeds.isEmpty() || foundOther.isEmpty()) return ItemStack.EMPTY;
		// todo: automatically determine color of item
		return TrailMixItem.withData(DyeColor.BLACK, foundFood, foundOther);
	}

	@Override
	public boolean canCraftInDimensions(int pWidth, int pHeight) {
		return pWidth * pHeight >= 3;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return ModRecipes.TRAIL_MIX.get();
	}
}
