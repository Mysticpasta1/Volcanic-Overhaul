package com.mystic.volcanic.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.IceBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(IceBlock.class)
public class MixinIceBlock {
    @Inject(method = "melt", at = @At(value = "HEAD"), remap = false, cancellable = true)
    public void isVaporizedOnPlaced(BlockState state, Level level, BlockPos pos, CallbackInfo ci) {
        ci.cancel();
        if (level.dimensionType().ultraWarm() || level.dimension() == Level.OVERWORLD && pos.getY() <= -100) {
            level.removeBlock(pos, false);
        } else {
           level.setBlockAndUpdate(pos, Blocks.WATER.defaultBlockState());
           level.neighborChanged(pos, Blocks.WATER, pos);
        }
    }
}
