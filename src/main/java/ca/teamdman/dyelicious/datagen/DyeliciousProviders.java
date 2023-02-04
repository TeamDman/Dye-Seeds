package ca.teamdman.dyelicious.datagen;

import ca.teamdman.dyelicious.Dyelicious;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Collections;
import java.util.List;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = Dyelicious.MOD_ID)
public class DyeliciousProviders {
	@SubscribeEvent
	public static void onRegister(GatherDataEvent event) {
		event.getGenerator()
				.addProvider(event.includeClient(),
							 new DyeliciousLanguageProvider(event.getGenerator()
																	.getPackOutput()));
		event.getGenerator()
				.addProvider(event.includeClient(),
							 new ItemModelProvider(event.getGenerator()
														   .getPackOutput(), event.getExistingFileHelper()));

		event.getGenerator().addProvider(event.includeServer(),new DyeliciousBlockStateProvider(event.getGenerator()
																			  .getPackOutput(), event.getExistingFileHelper()));

		event.getGenerator().addProvider(event.includeServer(), new DyeliciousLootProvider(
						event.getGenerator()
								.getPackOutput(),
						// Specify registry names of tables that are required to generate, or can leave empty
						Collections.emptySet(),
						// Sub providers which generate the loot
						List.of(subProvider1, subProvider2, /*...*/)
		))


	}
}
