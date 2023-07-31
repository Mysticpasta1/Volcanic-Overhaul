package com.mystic.volcanic;

import com.mystic.volcanic.fluids.CopperLavaFluid;
import com.mystic.volcanic.fluids.DiamondLavaFluid;
import com.mystic.volcanic.fluids.GoldLavaFluid;
import com.mystic.volcanic.fluids.IronLavaFluid;
import com.mystic.volcanic.init.*;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
@Mod(VolcanicOverhaul.MODID)
@Mod.EventBusSubscriber()
public class VolcanicOverhaul {
    public static final String MODID = "volcanic";

    public VolcanicOverhaul() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        BlockInit.init(bus);
        ItemInit.init(bus);
        FluidTypesInit.init(bus);
        FluidInit.init(bus);
        VolcanicGroupInit.init();
    }

    @SubscribeEvent
    public static void onServerStarting(ServerStartingEvent event) {
        IronLavaFluid.interactions();
        GoldLavaFluid.interactions();
        DiamondLavaFluid.interactions();
        CopperLavaFluid.interactions();
    }
}
