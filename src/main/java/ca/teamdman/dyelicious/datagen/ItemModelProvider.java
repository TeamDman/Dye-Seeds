package ca.teamdman.dyelicious.datagen;

import ca.teamdman.dyelicious.Dyelicious;
import ca.teamdman.dyelicious.common.registry.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemModelProvider extends net.minecraftforge.client.model.generators.ItemModelProvider {
	public ItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
		super(output, Dyelicious.MOD_ID, existingFileHelper);
	}

	@Override
	protected void registerModels() {
		for (DyeColor colour : DyeColor.values()) {
			var seedId     = ModItems.SEEDS_LOOKUP.get(colour).getId();
			var trailMixId = ModItems.TRAIL_MIX_LOOKUP.get(colour).getId();

			getBuilder(seedId.toString())
					.parent(new ModelFile.UncheckedModelFile("item/generated"))
					.texture("layer0", modLoc("item/seeds"));

			getBuilder(trailMixId.toString())
					.parent(new ModelFile.UncheckedModelFile("item/generated"))
					.texture("layer0", modLoc("item/trail_mix"));
		}
	}
}
