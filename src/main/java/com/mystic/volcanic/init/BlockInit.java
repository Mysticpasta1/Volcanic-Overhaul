package com.mystic.volcanic.init;

import com.mystic.volcanic.VolcanicOverhaul;
import com.mystic.volcanic.blocks.*;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Function;
import java.util.function.Supplier;

public class BlockInit {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, VolcanicOverhaul.MODID);

    public static final RegistryObject<LiquidBlock> IRON_LAVA_BLOCK = BLOCKS.register("iron_lava",
            () -> new LiquidBlock(FluidInit.IRON_LAVA, BlockBehaviour.Properties.of(Material.LAVA).noCollission().randomTicks().strength(100.0F).lightLevel((light) -> 15).noLootTable()));

    public static final RegistryObject<LiquidBlock> GOLD_LAVA_BLOCK = BLOCKS.register("gold_lava",
            () -> new LiquidBlock(FluidInit.GOLD_LAVA, BlockBehaviour.Properties.of(Material.LAVA).noCollission().randomTicks().strength(100.0F).lightLevel((light) -> 15).noLootTable()));

    public static final RegistryObject<Block> CENTERCORESTONE = registerBlock("centercorestone", () -> new CenterCoreStone(BlockBehaviour.Properties.of(Material.STONE)));
    public static final RegistryObject<Block> OUTERCORESTONE = registerBlock("outercorestone", () -> new OuterCoreStone(BlockBehaviour.Properties.of(Material.STONE)));
    public static final RegistryObject<Block> OUTERCOREBOULDER = registerBlock("outercore_boulder", () -> new OuterCoreBoulder(BlockBehaviour.Properties.of(Material.STONE)));
    public static final RegistryObject<Block> INNERCOREBOULDER = registerBlock("innercore_boulder", () -> new InnerCoreBoulder(BlockBehaviour.Properties.of(Material.STONE)));
    public static final RegistryObject<Block> CENTERCOREBOULDER = registerBlock("centercore_boulder", () -> new CenterCoreBoulder(BlockBehaviour.Properties.of(Material.STONE)));
    public static final RegistryObject<Block> INNERCORESTONE = registerBlock("innercorestone", () -> new InnerCoreStone(BlockBehaviour.Properties.of(Material.STONE)));
    public static final RegistryObject<Block> LOWERMANTLEROCK = registerBlock("lower_mantlerock", () -> new LowerMantleRock(BlockBehaviour.Properties.of(Material.STONE)));
    public static final RegistryObject<Block> LOWERMANTLEROCK1 = registerBlock("lower_mantlerock1", () -> new LowerMantleRock1(BlockBehaviour.Properties.of(Material.STONE)));
    public static final RegistryObject<Block> LOWERMANTLEROCK2 = registerBlock("lower_mantlerock2", () -> new LowerMantleRock2(BlockBehaviour.Properties.of(Material.STONE)));
    public static final RegistryObject<Block> MANTLEROCK = registerBlock("mantlerock", () -> new MantleRock(BlockBehaviour.Properties.of(Material.STONE)));
    public static final RegistryObject<Block> MANTLEROCK1 = registerBlock("mantlerock1", () -> new MantleRock1(BlockBehaviour.Properties.of(Material.STONE)));
    public static final RegistryObject<Block> MANTLEROCK2 = registerBlock("mantlerock2", () -> new MantleRock2(BlockBehaviour.Properties.of(Material.STONE)));
    public static final RegistryObject<Block> CRUSTROCK = registerBlock("crustrock", () -> new CrustRock(BlockBehaviour.Properties.of(Material.STONE)));
    public static final RegistryObject<Block> CRUSTROCK1 = registerBlock("crustrock1", () -> new CrustRock1(BlockBehaviour.Properties.of(Material.STONE)));
    public static final RegistryObject<Block> CRUSTROCK2 = registerBlock("crustrock2", () -> new CrustRock2(BlockBehaviour.Properties.of(Material.STONE)));
    public static final RegistryObject<Block> MANTLEROCK2BOULDER = registerBlock("mantlerock2_boulder", () -> new MantleRockBoulder2(BlockBehaviour.Properties.of(Material.STONE)));
    public static final RegistryObject<Block> MANTLEROCK1BOULDER = registerBlock("mantlerock1_boulder", () -> new MantleRockBoulder1(BlockBehaviour.Properties.of(Material.STONE)));
    public static final RegistryObject<Block> LOWERMANTLEROCK2BOULDER = registerBlock("lower_mantlerock2_boulder", () -> new LowerMantleRock2Boulder(BlockBehaviour.Properties.of(Material.STONE)));
    public static final RegistryObject<Block> LOWERMANTLEROCK1BOULDER = registerBlock("lower_mantlerock1_boulder", () -> new LowerMantleRock1Boulder(BlockBehaviour.Properties.of(Material.STONE)));
    public static final RegistryObject<Block> LOWERMANTLEROCKBOULDER = registerBlock("lower_mantlerock_boulder", () -> new LowerMantleRockBoulder(BlockBehaviour.Properties.of(Material.STONE)));
    public static final RegistryObject<Block> SUPERHEATED_BEDROCK = registerBlock("superheated_bedrock", () -> new SuperHeatedBedrock(BlockBehaviour.Properties.of(Material.STONE)));
    private static <B extends Block> RegistryObject<B> registerBlock(String name, Supplier<B> block) {
        return registerBlock(name, block, b -> () -> new BlockItem(b.get(),new Item.Properties().tab(VolcanicGroupInit.MAIN)));
    }

    private static <B extends Block, I extends BlockItem> RegistryObject<B> registerBlock(String name, Supplier<B> block, Function<RegistryObject<B>, Supplier<I>> item) {
        var reg = BLOCKS.register(name, block);
        ItemInit.ITEMS.register(name, () -> item.apply(reg).get());
        return reg;
    }

    public static void init(IEventBus bus) {
        BLOCKS.register(bus);
    }
}
