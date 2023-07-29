package com.mystic.volcanic.mixin;

import com.mystic.volcanic.blocks.*;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DiggerItem.class)
public class MixinBurnBreakDiggingTool {

    @Inject(method = "mineBlock", at = @At("HEAD"), cancellable = true)
    public void burnbreak(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity entity, CallbackInfoReturnable<Boolean> cir) {
        cir.cancel();
        if(burningBlockWoodStone(state, ((DiggerItem) (Object) this).getTier())) {
            stack.hurtAndBreak(100000000, entity, entity1 -> {
                entity1.broadcastBreakEvent(EquipmentSlot.MAINHAND);
                entity.hurt(DamageSource.ON_FIRE, 1.5f);
            });
        } else if (burningBlockIronGold(state, ((DiggerItem) (Object) this).getTier())) {
            stack.hurtAndBreak(100000000, entity, entity1 -> {
                entity1.broadcastBreakEvent(EquipmentSlot.MAINHAND);
                entity.hurt(DamageSource.ON_FIRE, 1.5f);
            });
        } else if (burningBlockDiamond(state, ((DiggerItem) (Object) this).getTier())) {
            stack.hurtAndBreak(100000000, entity, entity1 -> {
                entity1.broadcastBreakEvent(EquipmentSlot.MAINHAND);
                entity.hurt(DamageSource.ON_FIRE, 1.5f);
            });
        } else if (!level.isClientSide && state.getDestroySpeed(level, pos) != 0.0F) {
            stack.hurtAndBreak(1, entity, (entity1) -> {
                entity1.broadcastBreakEvent(EquipmentSlot.MAINHAND);
            });
        }

        cir.setReturnValue(true);
    }

    private boolean burningBlockWoodStone(BlockState blockState, Tier tier) {
        if(tier == Tiers.WOOD || tier == Tiers.STONE) {
            Block block = blockState.getBlock();
            return block instanceof LowerMantleRock || block instanceof LowerMantleRock1 || block instanceof LowerMantleRock2 ||
                    block instanceof LowerMantleRockBoulder || block instanceof LowerMantleRock2Boulder ||
                    block instanceof LowerMantleRock1Boulder || block instanceof CenterCoreBoulder ||
                    block instanceof CenterCoreStone || block instanceof InnerCoreStone ||
                    block instanceof SuperHeatedBedrock || block instanceof InnerCoreBoulder ||
                    block instanceof OuterCoreStone || block instanceof OuterCoreBoulder;
        } else {
            return false;
        }
    }

    private boolean burningBlockIronGold(BlockState blockState, Tier tier) {
        if(tier == Tiers.IRON || tier == Tiers.GOLD) {
            Block block = blockState.getBlock();
            return block instanceof CenterCoreBoulder ||
                    block instanceof CenterCoreStone || block instanceof InnerCoreStone ||
                    block instanceof SuperHeatedBedrock || block instanceof InnerCoreBoulder ||
                    block instanceof OuterCoreStone || block instanceof OuterCoreBoulder;
        } else {
            return false;
        }
    }

    private boolean burningBlockDiamond(BlockState blockState, Tier tier) {
        if(tier == Tiers.DIAMOND) {
            Block block = blockState.getBlock();
            return block instanceof SuperHeatedBedrock;
        } else {
            return false;
        }
    }
}
