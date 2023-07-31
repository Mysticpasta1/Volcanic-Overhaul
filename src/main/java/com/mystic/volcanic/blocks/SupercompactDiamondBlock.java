package com.mystic.volcanic.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class SupercompactDiamondBlock extends Block {
    public SupercompactDiamondBlock() {
        super(BlockBehaviour.Properties.copy(Blocks.DIAMOND_BLOCK));
    }
}
