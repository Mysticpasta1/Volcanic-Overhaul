package com.mystic.volcanic.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class SupercompactCopperBlock extends Block {
    public SupercompactCopperBlock() {
        super(BlockBehaviour.Properties.copy(Blocks.RAW_COPPER_BLOCK));
    }
}
