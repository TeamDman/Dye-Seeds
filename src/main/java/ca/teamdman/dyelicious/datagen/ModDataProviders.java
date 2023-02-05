package ca.teamdman.dyelicious.datagen;

import ca.teamdman.dyelicious.Dyelicious;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = Dyelicious.MOD_ID)
public class ModDataProviders {
	@SubscribeEvent
	public static void onRegister(GatherDataEvent event) {
		var packOutput = event.getGenerator().getPackOutput();
		var existingFileHelper = event.getExistingFileHelper();
		var lookupProvider = event.getLookupProvider();
		event.getGenerator().addProvider(event.includeClient(), new ModLanguageProvider(packOutput));
		event.getGenerator().addProvider(event.includeClient(), new ItemModelProvider(packOutput, existingFileHelper));
		event.getGenerator().addProvider(event.includeServer(), new ModBlockStateAndModelProvider(packOutput, existingFileHelper));
		event.getGenerator().addProvider(event.includeServer(), new ModLootProvider(packOutput));
		event.getGenerator().addProvider(event.includeServer(), new ModBlockTagsProvider(packOutput, lookupProvider, existingFileHelper));
	}
}
