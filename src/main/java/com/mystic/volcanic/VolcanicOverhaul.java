package com.mystic.volcanicoverhaul;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
@Mod(VolcanicOverhaul.MODID)
public class VolcanicOverhaul {
    public static final String MODID = "volcanic";

    public VolcanicOverhaul() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
    }
}
