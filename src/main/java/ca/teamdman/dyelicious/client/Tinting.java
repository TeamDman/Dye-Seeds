package ca.teamdman.dyelicious.client;

import ca.teamdman.dyelicious.Dyelicious;
import ca.teamdman.dyelicious.common.registry.ModBlocks;
import ca.teamdman.dyelicious.common.registry.ModItems;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.Nullable;

@Mod.EventBusSubscriber(modid= Dyelicious.MOD_ID, bus= Mod.EventBusSubscriber.Bus.MOD,value= Dist.CLIENT)
public class Tinting {
	@SubscribeEvent
	public static void registerItemColours(RegisterColorHandlersEvent.Item event) {
		for (DyeColor dye : DyeColor.values()) {
			var seed = ModItems.SEEDS_LOOKUP.get(dye).get();
			var trailMix = ModItems.TRAIL_MIX_LOOKUP.get(dye).get();
			var block = ModBlocks.CROP_LOOKUP.get(dye).get();
			event.getItemColors().register(new DyeColourItemColour(dye), seed);
//			event.getItemColors().register(new DyeColourItemColour(dye), trailMix);
			event.getBlockColors().register(new DyeColourBlockColour(dye), block);
		}
	}

	public static class DyeColourItemColour implements ItemColor {
		public final DyeColor DYE;
		public DyeColourItemColour(DyeColor dye) {
			this.DYE = dye;
		}
		@Override
		public int getColor(ItemStack stack, int tintIndex) {
			return this.DYE.getMaterialColor().col;
		}
	}

	public static class DyeColourBlockColour implements BlockColor {
		public final DyeColor DYE;
		public DyeColourBlockColour(DyeColor dye) {
			this.DYE = dye;
		}
		@Override
		public int getColor(BlockState state, @Nullable BlockAndTintGetter getter, @Nullable BlockPos pos, int tintIndex) {
			return this.DYE.getMaterialColor().col;
		}
	}

}