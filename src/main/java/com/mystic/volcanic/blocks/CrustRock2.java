package com.mystic.volcanic.blocks;

import net.minecraft.world.level.block.Block;

public class CrustRock2 extends Block {
    public CrustRock2(Properties properties) {
        super(properties.requiresCorrectToolForDrops().strength(5.0F, 9.0F));
    }
}
