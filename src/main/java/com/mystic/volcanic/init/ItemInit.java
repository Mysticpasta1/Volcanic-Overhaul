package com.mystic.volcanic.init;

import com.mystic.volcanic.VolcanicOverhaul;
import com.mystic.volcanic.items.NetheriteBucket;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ItemInit {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, VolcanicOverhaul.MODID);

    public static final RegistryObject<Item> NETHERITE_BUCKET = register("netherite_bucket", () -> new NetheriteBucket(() -> Fluids.EMPTY, new Item.Properties().tab(VolcanicGroupInit.MAIN)));

    public static final RegistryObject<Item> IRON_LAVA_BUCKET = register("iron_lava_bucket",
            () -> new NetheriteBucket(FluidInit.IRON_LAVA,
                    new Item.Properties().tab(VolcanicGroupInit.MAIN).craftRemainder(ItemInit.NETHERITE_BUCKET.get()).stacksTo(1)));

    public static final RegistryObject<Item> GOLD_LAVA_BUCKET = register("gold_lava_bucket",
            () -> new NetheriteBucket(FluidInit.GOLD_LAVA,
                    new Item.Properties().tab(VolcanicGroupInit.MAIN).craftRemainder(ItemInit.NETHERITE_BUCKET.get()).stacksTo(1)));

    public static <T extends Item> RegistryObject<T> register(String name, Supplier<T> item) {
        return ITEMS.register(name, item);
    }

    public static void init(IEventBus bus) {
        ITEMS.register(bus);
    }
}
