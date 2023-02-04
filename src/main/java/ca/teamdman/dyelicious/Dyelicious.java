package ca.teamdman.dyelicious;

import ca.teamdman.dyelicious.common.registry.DyeliciousBlocks;
import ca.teamdman.dyelicious.common.registry.DyeliciousItems;
import com.mojang.logging.LogUtils;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(Dyelicious.MOD_ID)
public class Dyelicious
{
    public static final String                  MOD_ID = "dyelicious";
    private static final Logger                 LOGGER = LogUtils.getLogger();

    public Dyelicious()
    {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        DyeliciousItems.register(bus);
        DyeliciousBlocks.register(bus);
    }

}
