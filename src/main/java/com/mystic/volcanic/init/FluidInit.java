package com.mystic.volcanic.init;

import com.mystic.volcanic.VolcanicOverhaul;
import com.mystic.volcanic.fluids.GoldLavaFluid;
import com.mystic.volcanic.fluids.IronLavaFluid;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FluidInit {
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, VolcanicOverhaul.MODID);

    public static final RegistryObject<FlowingFluid> IRON_LAVA = FLUIDS.register("iron_lava",
            () -> new IronLavaFluid.Source());
    public static final RegistryObject<FlowingFluid> FLOWING_IRON_LAVA = FLUIDS.register("flowing_iron_lava",
            () -> new IronLavaFluid.Flowing());
    public static final RegistryObject<FlowingFluid> GOLD_LAVA = FLUIDS.register("gold_lava",
            () -> new GoldLavaFluid.Source());
    public static final RegistryObject<FlowingFluid> FLOWING_GOLD_LAVA = FLUIDS.register("flowing_gold_lava",
            () -> new GoldLavaFluid.Flowing());

    public static void init(IEventBus eventBus) {
        FLUIDS.register(eventBus);
    }
}
