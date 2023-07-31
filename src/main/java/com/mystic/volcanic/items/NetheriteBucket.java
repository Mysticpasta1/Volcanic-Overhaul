package com.mystic.volcanic.items;

import com.mystic.volcanic.init.BlockInit;
import com.mystic.volcanic.init.ItemInit;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class NetheriteBucket extends BucketItem {
    private final Fluid content;
    private final java.util.function.Supplier<? extends Fluid> fluidSupplier;

    // Forge: Use the other constructor that takes a Supplier
    @Deprecated
    public NetheriteBucket(Fluid fluid, Item.Properties properties) {
        super(fluid, properties.stacksTo(16));
        this.content = fluid;
        this.fluidSupplier = net.minecraftforge.registries.ForgeRegistries.FLUIDS.getDelegateOrThrow(fluid);
    }

    /**
     * @param supplier A fluid supplier such as {@link net.minecraftforge.registries.RegistryObject<Fluid>}
     */
    public NetheriteBucket(java.util.function.Supplier<? extends Fluid> supplier, Item.Properties builder) {
        super(supplier, builder.stacksTo(16));
        this.content = supplier.get();
        this.fluidSupplier = supplier;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level p_40703_, Player p_40704_, InteractionHand p_40705_) {
        ItemStack itemstack = p_40704_.getItemInHand(p_40705_);
        BlockHitResult blockhitresult = getPlayerPOVHitResult(p_40703_, p_40704_, this.content == Fluids.EMPTY ? ClipContext.Fluid.SOURCE_ONLY : ClipContext.Fluid.NONE);
        InteractionResultHolder<ItemStack> ret = net.minecraftforge.event.ForgeEventFactory.onBucketUse(p_40704_, p_40703_, itemstack, blockhitresult);
        if (ret != null) return ret;
        if (blockhitresult.getType() == HitResult.Type.MISS) {
            return InteractionResultHolder.pass(itemstack);
        } else if (blockhitresult.getType() != HitResult.Type.BLOCK) {
            return InteractionResultHolder.pass(itemstack);
        } else {
            BlockPos blockpos = blockhitresult.getBlockPos();
            Direction direction = blockhitresult.getDirection();
            BlockPos blockpos1 = blockpos.relative(direction);
            if (p_40703_.mayInteract(p_40704_, blockpos) && p_40704_.mayUseItemAt(blockpos1, direction, itemstack)) {
                if (this.content == Fluids.EMPTY) {
                    BlockState blockstate1 = p_40703_.getBlockState(blockpos);
                    if (blockstate1.getBlock() instanceof BucketPickup) {
                        BucketPickup bucketpickup = (BucketPickup)blockstate1.getBlock();
                        if(isBucketPickupFluid(bucketpickup)) {
                            ItemStack itemstack1 = bucketpickup.pickupBlock(p_40703_, blockpos, blockstate1);
                            if (!itemstack1.isEmpty()) {
                                p_40704_.awardStat(Stats.ITEM_USED.get(this));
                                bucketpickup.getPickupSound(blockstate1).ifPresent((p_150709_) -> {
                                    p_40704_.playSound(p_150709_, 1.0F, 1.0F);
                                });
                                p_40703_.gameEvent(p_40704_, GameEvent.FLUID_PICKUP, blockpos);
                                ItemStack itemstack2 = ItemUtils.createFilledResult(itemstack, p_40704_, itemstack1);
                                if (!p_40703_.isClientSide) {
                                    CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayer) p_40704_, itemstack1);
                                }

                                return InteractionResultHolder.sidedSuccess(itemstack2, p_40703_.isClientSide());
                            }
                        }
                    }

                    return InteractionResultHolder.fail(itemstack);
                } else {
                    BlockState blockstate = p_40703_.getBlockState(blockpos);
                    BlockPos blockpos2 = canBlockContainFluid(p_40703_, blockpos, blockstate) ? blockpos : blockpos1;
                    if (this.emptyContents(p_40704_, p_40703_, blockpos2, blockhitresult, itemstack)) {
                        this.checkExtraContent(p_40704_, p_40703_, itemstack, blockpos2);
                        if (p_40704_ instanceof ServerPlayer) {
                            CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer)p_40704_, blockpos2, itemstack);
                        }

                        p_40704_.awardStat(Stats.ITEM_USED.get(this));
                        return InteractionResultHolder.sidedSuccess(this.getEmptySuccessItem(itemstack, p_40704_), p_40703_.isClientSide());
                    } else {
                        return InteractionResultHolder.fail(itemstack);
                    }
                }
            } else {
                return InteractionResultHolder.fail(itemstack);
            }
        }
    }

    public static ItemStack getEmptySuccessItem(ItemStack p_40700_, Player p_40701_) {
        return !p_40701_.getAbilities().instabuild ? new ItemStack(ItemInit.NETHERITE_BUCKET.get()) : p_40700_;
    }

    public static boolean isBucketPickupFluid(BucketPickup bucketpickup) {
        return bucketpickup == BlockInit.GOLD_LAVA_BLOCK.get() || bucketpickup == BlockInit.IRON_LAVA_BLOCK.get()
                || bucketpickup == BlockInit.COPPER_LAVA_BLOCK.get() || bucketpickup == BlockInit.DIAMOND_LAVA_BLOCK.get();
    }

    public Fluid getFluid() { return fluidSupplier.get(); }
}
