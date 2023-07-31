package com.mystic.volcanic.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class SupercompactGoldBlock extends Block {
    public SupercompactGoldBlock() {
        super(BlockBehaviour.Properties.copy(Blocks.RAW_GOLD_BLOCK));
    }
}
