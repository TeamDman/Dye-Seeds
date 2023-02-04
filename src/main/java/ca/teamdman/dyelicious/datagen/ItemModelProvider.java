package ca.teamdman.dyelicious.datagen;

import ca.teamdman.dyelicious.Dyelicious;
import ca.teamdman.dyelicious.common.registry.DyeliciousItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
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
		DyeliciousItems.SEEDS_LOOKUP.values().stream().map(RegistryObject::get).forEach(seed -> {
			var itemId = ForgeRegistries.ITEMS.getKey(seed);
			getBuilder(itemId.toString())
					.parent(new ModelFile.UncheckedModelFile("item/generated"))
					.texture("layer0", new ResourceLocation(itemId.getNamespace(), "item/seeds"));
//			this.withExistingParent(ForgeRegistries.ITEMS.getKey(seed).toString(), ForgeRegistries.ITEMS.getKey(Items.WHEAT_SEEDS));
		});
	}
}
