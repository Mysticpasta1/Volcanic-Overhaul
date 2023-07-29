package com.mystic.volcanic.init;

import com.mystic.volcanic.VolcanicOverhaul;
import com.mystic.volcanic.fluids.IronLavaFluid;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FluidTypesInit {
    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, VolcanicOverhaul.MODID);

    public static final RegistryObject<FluidType> IRON_LAVA_FLUID_TYPE = registerIronLavaType("iron_lava", 
    
    private static RegistryObject<FluidType> registerIronLavaType(String name, FluidType.Properties properties) {
        return FLUID_TYPES.register(name, () -> new FluidType(properties));
    }

    public static void init(IEventBus eventBus) {
        FLUID_TYPES.register(eventBus);
    }
}