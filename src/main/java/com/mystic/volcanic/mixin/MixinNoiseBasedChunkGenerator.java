package com.mystic.volcanic.mixin;

import net.minecraft.core.Holder;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Aquifer;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({NoiseBasedChunkGenerator.class})
public abstract class MixinNoiseBasedChunkGenerator {
    @Shadow
    protected Holder<NoiseGeneratorSettings> settings;
    @Shadow
    @Final
    @Mutable
    private Aquifer.FluidPicker globalFluidPicker;

    public MixinNoiseBasedChunkGenerator() {
    }

    @Inject(
        method = {"<init>"},
        at = {@At("TAIL")}
    )
    private void onInit(CallbackInfo ci) {
        Aquifer.FluidStatus aquiferLavaStatus = new Aquifer.FluidStatus(Integer.MIN_VALUE, Blocks.LAVA.defaultBlockState());
        Aquifer.FluidPicker modifiedFluidPicker = (x, y, z) -> {
            return y < Math.min(Integer.MIN_VALUE, ((NoiseGeneratorSettings)this.settings.value()).seaLevel()) ? aquiferLavaStatus : new Aquifer.FluidStatus(((NoiseGeneratorSettings)this.settings.value()).seaLevel(), ((NoiseGeneratorSettings)this.settings.value()).defaultFluid());
        };
        this.globalFluidPicker = modifiedFluidPicker;
    }
}
