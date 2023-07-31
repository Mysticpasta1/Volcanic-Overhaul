package com.mystic.volcanic.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class SupercompactCoalBlock extends Block {
    public SupercompactCoalBlock() {
        super(BlockBehaviour.Properties.copy(Blocks.COAL_BLOCK));
    }
}
