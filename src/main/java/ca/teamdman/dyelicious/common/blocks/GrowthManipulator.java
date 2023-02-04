package ca.teamdman.dyelicious.common.blocks;

import ca.teamdman.dyelicious.Dyelicious;
import net.minecraft.world.level.block.CropBlock;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = Dyelicious.MOD_ID)
public class GrowthManipulator {
	@SubscribeEvent
	public static void onGrow(BlockEvent.CropGrowEvent.Pre event) {
		// make the last stage take much longer
		if (event.getState()
				.getValue(CropBlock.AGE) == 6) {
			if (event.getLevel()
					.getRandom()
					.nextFloat() > 0.1f) {
				event.setCanceled(true);
			}
		}
	}
}
