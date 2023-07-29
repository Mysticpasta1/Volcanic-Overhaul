package com.mystic.volcanic.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.MagmaBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class CenterCoreStone extends MagmaBlock {
    public CenterCoreStone(Properties properties) {
        super(properties.requiresCorrectToolForDrops().strength(2.0F, 39.0F).lightLevel((light) -> 14));
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        if(player.getItemInHand(hand).is(Items.WOODEN_PICKAXE) || player.getItemInHand(hand).is(Items.STONE_PICKAXE)) {
            player.getItemInHand(hand).hurtAndBreak(1, player, (player1 -> {
                player.hurt(DamageSource.ON_FIRE, 1.5f);
            }));
            return InteractionResult.SUCCESS;
        } else if (player.getItemInHand(hand).is(Items.IRON_PICKAXE) || player.getItemInHand(hand).is(Items.GOLDEN_PICKAXE)) {
            player.getItemInHand(hand).hurtAndBreak(1, player, (player1 -> {
                player.hurt(DamageSource.ON_FIRE, 1.5f);
            }));
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    public void stepOn(Level p_153777_, BlockPos p_153778_, BlockState p_153779_, Entity p_153780_) {
        if (p_153780_ instanceof LivingEntity && !EnchantmentHelper.hasFrostWalker((LivingEntity)p_153780_)) {
            p_153780_.hurt(DamageSource.HOT_FLOOR, 5.0F);
        }

        super.stepOn(p_153777_, p_153778_, p_153779_, p_153780_);
    }
}
