package com.mystic.volcanic.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class SupercompactLapisBlock extends Block {
    public SupercompactLapisBlock() {
        super(BlockBehaviour.Properties.copy(Blocks.LAPIS_BLOCK));
    }
}
