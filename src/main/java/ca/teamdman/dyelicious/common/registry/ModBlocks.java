package ca.teamdman.dyelicious.common.registry;

import ca.teamdman.dyelicious.Dyelicious;
import ca.teamdman.dyelicious.common.blocks.DyeCropBlock;
import ca.teamdman.dyelicious.common.blocks.Dyeguritator;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ModBlocks {
	private static final DeferredRegister<Block> BLOCKS           = DeferredRegister.create(
			ForgeRegistries.BLOCKS,
			Dyelicious.MOD_ID
	);

	public static final HashMap<DyeColor, RegistryObject<DyeCropBlock>> CROP_LOOKUP          = new HashMap<>();
	public static final List<RegistryObject<? extends Block>>           REGISTRY_OBJECT_LIST = new ArrayList<>();
	public static final  RegistryObject<Block>      DYEGURGITATOR_BLOCK  = BLOCKS.register("dyegurgitator", Dyeguritator::new);

	static {
		for (DyeColor colour : DyeColor.values()) {
			var regobj = BLOCKS.register("crop_" + colour.name().toLowerCase(), ()-> new DyeCropBlock(colour));
			REGISTRY_OBJECT_LIST.add(regobj);
			CROP_LOOKUP.put(colour, regobj);
		}
		REGISTRY_OBJECT_LIST.add(DYEGURGITATOR_BLOCK);
	}

	public static void register(IEventBus bus) {
		BLOCKS.register(bus);
	}

}
