package com.mystic.volcanic.init;

import com.mystic.volcanic.VolcanicOverhaul;
import com.mystic.volcanic.fluids.IronLavaFluid;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Consumer;

public class FluidTypesInit {
    public static final ResourceLocation ironStillTexture = new ResourceLocation(VolcanicOverhaul.MODID, "blocks/iron_lava_still");
    public static final ResourceLocation ironFlowingTexture = new ResourceLocation(VolcanicOverhaul.MODID, "blocks/iron_lava_flow");
    public static final ResourceLocation ironOverlayTexture = new ResourceLocation(VolcanicOverhaul.MODID, "blocks/iron_lava");
    public static final ResourceLocation goldStillTexture = new ResourceLocation(VolcanicOverhaul.MODID, "blocks/gold_lava_still");
    public static final ResourceLocation goldFlowingTexture = new ResourceLocation(VolcanicOverhaul.MODID, "blocks/gold_lava_flow");
    public static final ResourceLocation goldOverlayTexture = new ResourceLocation(VolcanicOverhaul.MODID, "blocks/gold_lava");
    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, VolcanicOverhaul.MODID);

    public static final RegistryObject<FluidType> IRON_LAVA_FLUID_TYPE = registerIronLavaType("iron_lava", new FluidType(FluidType.Properties.create()
            .canSwim(false)
            .canDrown(false)
            .pathType(BlockPathTypes.LAVA)
            .adjacentPathType(null)
            .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL_LAVA)
            .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY_LAVA)
            .lightLevel(15)
            .density(4000)
            .viscosity(5000)
            .temperature(1500)) {
        @Override
        public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
            consumer.accept(new IClientFluidTypeExtensions() {
                @Override
                public ResourceLocation getStillTexture() {
                    return ironStillTexture;
                }

                @Override
                public ResourceLocation getFlowingTexture() {
                    return ironFlowingTexture;
                }

                @Override
                public @org.jetbrains.annotations.Nullable ResourceLocation getOverlayTexture() {
                    return ironOverlayTexture;
                }
            });
        }
    });

    public static final RegistryObject<FluidType> GOLD_LAVA_FLUID_TYPE = registerIronLavaType("gold_lava", new FluidType(FluidType.Properties.create()
            .canSwim(false)
            .canDrown(false)
            .pathType(BlockPathTypes.LAVA)
            .adjacentPathType(null)
            .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL_LAVA)
            .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY_LAVA)
            .lightLevel(15)
            .density(5000)
            .viscosity(6000)
            .temperature(1700)) {
        @Override
        public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
            consumer.accept(new IClientFluidTypeExtensions() {
                @Override
                public ResourceLocation getStillTexture() {
                    return goldStillTexture;
                }

                @Override
                public ResourceLocation getFlowingTexture() {
                    return goldFlowingTexture;
                }

                @Override
                public @org.jetbrains.annotations.Nullable ResourceLocation getOverlayTexture() {
                    return goldOverlayTexture;
                }
            });
        }
    });

    private static RegistryObject<FluidType> registerIronLavaType(String name, FluidType fluidType) {
        return FLUID_TYPES.register(name, () -> fluidType);
    }

    public static void init(IEventBus eventBus) {
        FLUID_TYPES.register(eventBus);
    }
}