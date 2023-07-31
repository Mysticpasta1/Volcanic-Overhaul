package com.mystic.volcanic.init;

import com.mystic.volcanic.VolcanicOverhaul;
import com.mystic.volcanic.fluids.CopperLavaFluid;
import com.mystic.volcanic.fluids.DiamondLavaFluid;
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
    public static final RegistryObject<FlowingFluid> COPPER_LAVA = FLUIDS.register("copper_lava",
            () -> new CopperLavaFluid.Source());
    public static final RegistryObject<FlowingFluid> FLOWING_COPPER_LAVA = FLUIDS.register("flowing_copper_lava",
            () -> new CopperLavaFluid.Flowing());
    public static final RegistryObject<FlowingFluid> DIAMOND_LAVA = FLUIDS.register("diamond_lava",
            () -> new DiamondLavaFluid.Source());
    public static final RegistryObject<FlowingFluid> FLOWING_DIAMOND_LAVA = FLUIDS.register("flowing_diamond_lava",
            () -> new DiamondLavaFluid.Flowing());
    public static void init(IEventBus eventBus) {
        FLUIDS.register(eventBus);
    }
}
