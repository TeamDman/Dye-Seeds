package ca.teamdman.dyelicious.datagen;

import ca.teamdman.dyelicious.common.registry.ModRecipes;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider {
	public ModRecipeProvider(PackOutput pOutput) {
		super(pOutput);
	}

	@Override
	protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
		SpecialRecipeBuilder.special(ModRecipes.TRAIL_MIX.get()).save(pWriter, ModRecipes.TRAIL_MIX.getId().getPath());
	}
}
