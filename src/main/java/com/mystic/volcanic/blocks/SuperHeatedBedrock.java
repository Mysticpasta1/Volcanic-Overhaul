package com.mystic.volcanic.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class SuperHeatedBedrock extends Block {

    public SuperHeatedBedrock(Properties properties) {
        super(properties.strength(-1.0F, 3600000.0F).lightLevel((light) -> 15).noLootTable().isValidSpawn((p_61031_, p_61032_, p_61033_, p_61034_) -> false));
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
        } else if (player.getItemInHand(hand).is(Items.DIAMOND_PICKAXE)) {
            player.getItemInHand(hand).hurtAndBreak(1, player, (player1 -> {
                player.hurt(DamageSource.ON_FIRE, 1.5f);
            }));
            return InteractionResult.SUCCESS;
        } else {
            return InteractionResult.PASS;
        }
    }

    public void stepOn(Level level, BlockPos blockpos, BlockState blockState, Entity entity) {
        entity.hurt(DamageSource.HOT_FLOOR, 100000.0F);
    }
}
