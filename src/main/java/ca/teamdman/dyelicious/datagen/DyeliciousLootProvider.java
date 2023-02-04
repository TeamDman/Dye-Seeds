package ca.teamdman.dyelicious.datagen;

import ca.teamdman.dyelicious.common.registry.DyeliciousBlocks;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class DyeliciousLootProvider extends LootTableProvider {
	public DyeliciousLootProvider(PackOutput output, Set<ResourceLocation> idk, List<SubProviderEntry> p_253798_) {
		super(output, idk, p_253798_);
	}

	@Override
	public List<SubProviderEntry> getTables() {
		return ImmutableList.of(new SubProviderEntry(MyLootSubProvider::new, LootContextParamSets.BLOCK));
	}

	// In some LootTableProvider subclass
	@Override
	protected void validate(Map<ResourceLocation, LootTable> tables, ValidationContext ctx) {
		tables.forEach((name, table) -> LootTables.validate(ctx, name, table));
	}

	public static class MyLootSubProvider implements LootTableSubProvider {
		@Override
		public void generate(BiConsumer<ResourceLocation, LootTable.Builder> writer) {
			// set up drops for the dye crops
			// should drop the correct dye seed when broken at age<6
			// should drop the correct dye when broken at age=6
			// should drop 3 of the correct dye and have a chance to drop additional seeds at age=7
			ResourceLocation dyeCrop = new ResourceLocation("mymodid:dye_crop");

			LootTable.Builder dyeCropAgeLessThan6 = LootTable.lootTable()
					.withPool(LootPool.lootPool()
									  .setRolls(ConstantValue.exactly(1))
									  .add(LootItem.lootTableItem(ModItems.SEED_ITEM)
												   .apply(SetLootCondition.condition(new BlockStatePropertyLootCondition(new BlockStatePredicate(Blocks.MY_DYE_CROP.getDefaultState().with(AgeableBlock.AGE, IntegerRange.create(0, 5))))))));

			LootTable.Builder dyeCropAge6 = LootTable.lootTable()
					.withPool(LootPool.lootPool()
									  .setRolls(ConstantValue.exactly(1))
									  .add(LootItem.lootTableItem(ModItems.DYE_ITEM)
												   .apply(SetLootCondition.condition(new BlockStatePropertyLootCondition(new BlockStatePredicate(Blocks.MY_DYE_CROP.getDefaultState().with(AgeableBlock.AGE, 6))))));

			LootTable.Builder dyeCropAge7 = LootTable.lootTable()
					.withPool(LootPool.lootPool()
									  .setRolls(ConstantValue.exactly(1))
									  .add(LootItem.lootTableItem(ModItems.DYE_ITEM)
												   .apply(SetCountFunction.setCount(ConstantValue.of(3)))
												   .apply(SetLootCondition.condition(new BlockStatePropertyLootCondition(new BlockStatePredicate(Blocks.MY_DYE_CROP.getDefaultState().with(AgeableBlock.AGE, 7))))))
									  .add(LootItem.lootTableItem(ModItems.SEED_ITEM)
												   .apply(SetCountFunction.setCount(UniformGenerator.of(0, 2)))
												   .apply(SetLootCondition.condition(new RandomChanceLootCondition(0.5f))));

			writer.accept(dyeCrop.createPath("age_less_than_6"), dyeCropAgeLessThan6);
			writer.accept(dyeCrop.createPath("age_6"), dyeCropAge6);
			writer.accept(dyeCrop.createPath("age_7"), dyeCropAge7);

			//			DyeliciousBlocks.CROP_LOOKUP.forEach((dye, crop) -> {
//				writer.accept(crop.get().getLootTable(), LootTable.lootTable()
//						.withPool(LootPool.lootPool()
//								.when(StatePropertiesPredicate.Builder.properties()
//										.hasProperty(CropBlock.AGE, 7))
//								.setRolls(ConstantRange.exactly(3))
//								.add(LootItem.lootTableItem(crop.get())
//										.apply(SetCount.setCount(ConstantRange.exactly(3)))
//										.apply(ApplyBonus.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE, 0.5714286F, 3)))
//								.add(LootItem.lootTableItem(crop.get())
//										.when(RandomChanceWithLooting.randomChanceAndLootingBoost(0.125F, 0.05F))
//										.apply(ApplyBonus.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE, 0.5714286F, 1)))
//								.otherwise(LootTable.pool()
//										.when(StatePropertiesPredicate.Builder.properties()
//												.hasProperty(CropBlock.AGE, 6))
//										.add(LootItem.lootTableItem(crop.get())
//												.apply(ApplyBonus.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE, 0.5714286F, 3)))
//										.add(LootItem.lootTableItem(crop.get())
//												.when(RandomChanceWithLooting.randomChanceAndLootingBoost(0.125F, 0.05F))
//												.apply(ApplyBonus.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE, 0.5714286F, 1)))
//										.otherwise(LootTable.pool()
//												.when(StatePropertiesPredicate.Builder.properties()
//														.hasProperty(CropBlock.AGE, 5))
//												.add(LootItem.lootTableItem(crop.get())
//														.apply(ApplyBonus.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE, 0.5714286F, 2)))
//												.add(LootItem.lootTableItem(crop.get())
//														.when(RandomChanceWithLooting.randomChanceAndLootingBoost(0.125F, 0.05F))
//														.apply(ApplyBonus.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE,
		}
	}
}
