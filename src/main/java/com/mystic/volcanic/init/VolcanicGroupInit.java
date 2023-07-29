package com.mystic.volcanic.init;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class VolcanicGroupInit {
    public static final CreativeModeTab MAIN = new CreativeModeTab(CreativeModeTab.getGroupCountSafe(), "volcanic.general") {
        @Override
        public ItemStack makeIcon() {
            return BlockInit.MANTLEROCK1.get().asItem().getDefaultInstance();
        }
    };

    public static void init(){
    }
}