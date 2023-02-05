package ca.teamdman.dyelicious.common.registry;

import ca.teamdman.dyelicious.Dyelicious;
import ca.teamdman.dyelicious.common.recipes.TrailMixRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes {
	private static final DeferredRegister<RecipeSerializer<?>> RECIPES = DeferredRegister.create(
			ForgeRegistries.RECIPE_SERIALIZERS,
			Dyelicious.MOD_ID
	);

	public static final RegistryObject<SimpleCraftingRecipeSerializer<TrailMixRecipe>> TRAIL_MIX = RECIPES.register(
			"trail_mix",
			()-> new SimpleCraftingRecipeSerializer<>(TrailMixRecipe::new)
	);

	public static void register(IEventBus bus) {
		RECIPES.register(bus);
	}
}
