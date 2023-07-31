package com.mystic.volcanic.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class SupercompactRedstoneBlock extends Block {
    public SupercompactRedstoneBlock() {
        super(BlockBehaviour.Properties.copy(Blocks.REDSTONE_BLOCK));
    }
}
