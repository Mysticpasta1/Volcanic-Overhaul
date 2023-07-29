package com.mystic.volcanic.blocks;

import net.minecraft.world.level.block.Block;

public class CrustRock1 extends Block {
    public CrustRock1(Properties properties) {
        super(properties.requiresCorrectToolForDrops().strength(4.5F, 8.5F));
    }
}
