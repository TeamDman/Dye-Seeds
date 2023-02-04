package ca.teamdman.dyelicious.datagen;

import ca.teamdman.dyelicious.Dyelicious;
import ca.teamdman.dyelicious.common.registry.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.CropBlock;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlockStateProvider extends BlockStateProvider {
	public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
		super(output, Dyelicious.MOD_ID, exFileHelper);
	}

	@Override
	protected void registerStatesAndModels() {
//		for (DyeColor dye : DyeColor.values()) {
//			var block = ModBlocks.CROP_LOOKUP.get(dye).get();
//			getVariantBuilder(block).forAllStates(state->{
//				return ConfiguredModel.builder()
//						.modelFile(models().singleTexture("crop", block.getRegistryName().getPath(), block.getRegistryName()))
//						.build();
//			});
//		}

		// register all the crops
		for (DyeColor dye : DyeColor.values()) {
			var block = ModBlocks.CROP_LOOKUP.get(dye).get();
			var name = ForgeRegistries.BLOCKS.getKey(block);
			getVariantBuilder(block).forAllStates(state->{
				var stage = state.getValue(CropBlock.AGE);
				return ConfiguredModel.builder()
						.modelFile(
								models().singleTexture(
										name.getPath() + "_stage" + stage,
										mcLoc("block/crop"),
										"crop",
										modLoc("block/crop_stage" + stage)
								)
										.renderType("cutout")
						)
						.build();
			});
		}
	}
}
