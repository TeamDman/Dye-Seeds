package ca.teamdman.dyelicious.datagen;

import ca.teamdman.dyelicious.Dyelicious;
import ca.teamdman.dyelicious.common.registry.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.data.LanguageProvider;

public class ModLanguageProvider extends LanguageProvider {
	public ModLanguageProvider(PackOutput output) {
		super(output, Dyelicious.MOD_ID, "en_us");
	}

	private String titleCase(String str) {
		return str.substring(0, 1)
				.toUpperCase() + str.substring(1)
				.toLowerCase();
	}

	@Override
	protected void addTranslations() {
		ModItems.SEEDS_LOOKUP.forEach((colour, item) -> {
			String name = titleCase(colour.name()) + " dye seeds";
			this.add(item.get(), name);
		});
		ModItems.TRAIL_MIX_LOOKUP.forEach((colour, item) -> {
			String name = titleCase(colour.name()) + " questionable trail mix";
			this.add(item.get(), name);
		});

		this.add("item_group.dyelicious.main", "Dyelicious");
	}
}
