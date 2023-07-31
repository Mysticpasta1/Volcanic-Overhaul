package com.mystic.volcanic.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class SupercompactEmeraldBlock extends Block {
    public SupercompactEmeraldBlock() {
        super(BlockBehaviour.Properties.copy(Blocks.EMERALD_BLOCK));
    }
}
