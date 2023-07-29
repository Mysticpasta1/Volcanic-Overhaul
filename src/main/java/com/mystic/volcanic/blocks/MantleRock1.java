package com.mystic.volcanic.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.MagmaBlock;
import net.minecraft.world.level.block.state.BlockState;

public class MantleRock1 extends MagmaBlock {
    public MantleRock1(Properties properties) {
        super(properties.requiresCorrectToolForDrops().strength(5.0F, 15.0F).lightLevel((light) -> 3));
    }

    @Override
    public void stepOn(Level p_153777_, BlockPos p_153778_, BlockState p_153779_, Entity p_153780_) {
        if (p_153780_ instanceof LivingEntity && !EnchantmentHelper.hasFrostWalker((LivingEntity)p_153780_)) {
            p_153780_.hurt(DamageSource.HOT_FLOOR, 3.5F);
        }

        super.stepOn(p_153777_, p_153778_, p_153779_, p_153780_);
    }
}
