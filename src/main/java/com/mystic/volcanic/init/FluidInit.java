package com.mystic.volcanicoverhaul.init;

import com.mystic.volcanicoverhaul.VolcanicOverhaul;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class FluidInit {
    public static final DeferredRegister<Fluid> FLUID = DeferredRegister.create(ForgeRegistries.FLUIDS, VolcanicOverhaul.MODID);
}
