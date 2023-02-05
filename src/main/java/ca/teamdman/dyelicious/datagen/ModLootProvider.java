package ca.teamdman.dyelicious.datagen;

import ca.teamdman.dyelicious.common.registry.ModBlocks;
import ca.teamdman.dyelicious.common.registry.ModItems;
import com.google.common.collect.ImmutableList;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PotatoBlock;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.ApplyExplosionDecay;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.registries.RegistryObject;

import java.util.Collections;
import java.util.function.BiConsumer;

public class ModLootProvider extends LootTableProvider {
	public ModLootProvider(PackOutput output) {
		super(
				output,
				// Specify registry names of tables that are required to generate, or can leave empty
				Collections.emptySet(),
				// Sub providers which generate the loot
				ImmutableList.of(new SubProviderEntry(DyeCropLootProvider::new, LootContextParamSets.BLOCK))
		);
	}

	public static class DyeCropLootProvider implements LootTableSubProvider {
		public LootItemBlockStatePropertyCondition.Builder ageEq(RegistryObject<? extends Block> block, int age) {
			var condition = LootItemBlockStatePropertyCondition.hasBlockStateProperties(block.get());
			condition.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(PotatoBlock.AGE, age));
			return condition;
		}

		/**
		 * Generate loot table for a single dye crop
		 * - should drop the correct dye seed when broken at age<6
		 * - should drop the correct dye when broken at age=6
		 * - should drop 3 of the correct dye and have a chance to drop additional seeds at age=7
		 *
		 * @param writer
		 * @param dye
		 */
		public void generate(BiConsumer<ResourceLocation, LootTable.Builder> writer, DyeColor dye) {
			var block   = ModBlocks.CROP_LOOKUP.get(dye);
			var seed    = ModItems.SEEDS_LOOKUP.get(dye);
			var dyeItem = DyeItem.byColor(dye);
			var fortune = ApplyBonusCount.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE, 0.5714286F, 3);

			var builder = LootTable.lootTable()
					.withPool(LootPool.lootPool()
							.when(ageEq(block, 6))
							.add(LootItem.lootTableItem(dyeItem))
					)
					.withPool(LootPool.lootPool()
							.when(ageEq(block, 7))
							.add(LootItem.lootTableItem(dyeItem).apply(fortune))
							.add(LootItem.lootTableItem(dyeItem).apply(fortune))
							.add(LootItem.lootTableItem(dyeItem).apply(fortune).when(LootItemRandomChanceCondition.randomChance(0.125F)))
							.add(LootItem.lootTableItem(seed.get()).when(LootItemRandomChanceCondition.randomChance(0.125F)))
					);
			for (int i = 0; i < 6; i++) {
				builder.withPool(LootPool.lootPool()
						.when(ageEq(block, i))
						.add(LootItem.lootTableItem(seed.get()))
				);
			}

			builder.apply(ApplyExplosionDecay.explosionDecay());
			writer.accept(block.get().getLootTable(), builder);
		}

		@Override
		public void generate(BiConsumer<ResourceLocation, LootTable.Builder> writer) {
			for (DyeColor dye : DyeColor.values()) {
				generate(writer, dye);
			}
		}
	}
}
