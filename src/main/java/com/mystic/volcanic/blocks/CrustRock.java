package com.mystic.volcanic.blocks;

import net.minecraft.world.level.block.Block;

public class CrustRock extends Block {
    public CrustRock(Properties properties) {
        super(properties.requiresCorrectToolForDrops().strength(4.0F, 8.0F));
    }
}
