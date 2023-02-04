package ca.teamdman.dyelicious.client;

import ca.teamdman.dyelicious.Dyelicious;
import ca.teamdman.dyelicious.common.registry.DyeliciousItems;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid= Dyelicious.MOD_ID, bus= Mod.EventBusSubscriber.Bus.MOD,value= Dist.CLIENT)
public class SeedItemTint {
	@SubscribeEvent
	public static void registerItemColours(RegisterColorHandlersEvent.Item event) {
		DyeliciousItems.SEEDS_LOOKUP.forEach((dye, seed) -> event.getItemColors().register(new SeedItemColour(dye), seed.get()));
	}

	public static class SeedItemColour implements ItemColor {
		public final DyeColor DYE;
		public SeedItemColour(DyeColor dye) {
			this.DYE = dye;
		}
		@Override
		public int getColor(ItemStack stack, int tintIndex) {
			return this.DYE.getMaterialColor().col;
		}
	}


}