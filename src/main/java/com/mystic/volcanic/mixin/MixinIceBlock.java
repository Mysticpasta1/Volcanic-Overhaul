package com.mystic.volcanic.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FluidType.class)
public class MixinFluidType {

    @Shadow
    public FluidState getStateForPlacement(BlockAndTintGetter getter, BlockPos pos, FluidStack stack)
    {
        return stack.getFluid().defaultFluidState();
    }

    @Shadow
    public BlockState getBlockForFluidState(BlockAndTintGetter getter, BlockPos pos, FluidState state)
    {
        return state.createLegacyBlock();
    }

    @Inject(method = "isVaporizedOnPlacement", at = @At(value = "HEAD"), remap = false, cancellable = true)
    public void isVaporizedOnPlaced(Level level, BlockPos pos, FluidStack stack, CallbackInfoReturnable<Boolean> cir) {
        BlockState state = this.getBlockForFluidState(level, pos, this.getStateForPlacement(level, pos, stack));
        if (level.dimensionType().ultraWarm())
        {
            cir.setReturnValue(state != null && state.getMaterial() == Material.WATER);
        }

        cir.setReturnValue(level.dimension() == Level.OVERWORLD && pos.getY() <= -100 && state.getMaterial() == Material.WATER);
    }
}
