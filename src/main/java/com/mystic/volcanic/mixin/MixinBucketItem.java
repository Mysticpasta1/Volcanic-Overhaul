package com.mystic.volcanic.mixin;

import com.mystic.volcanic.fluids.GoldLavaFluid;
import com.mystic.volcanic.init.BlockInit;
import com.mystic.volcanic.init.FluidInit;
import com.mystic.volcanic.items.NetheriteBucket;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.event.ForgeEventFactory;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BucketItem.class)
public abstract class MixinBucketItem extends Item {
    public MixinBucketItem(Properties properties) {
        super(properties);
    }

    @Shadow
    protected abstract boolean canBlockContainFluid(Level worldIn, BlockPos posIn, BlockState blockstate);

    @Shadow
    @Final
    private Fluid content;

    @Inject(method = "use", at = @At(value = "HEAD"), cancellable = true)
    public void usedOnHotterLavas(Level level, Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir) {
        cir.cancel();
        ItemStack itemstack = player.getItemInHand(hand);
        BlockHitResult blockhitresult = this.getPlayerPOVHitResult(level, player, this.content == Fluids.EMPTY ? ClipContext.Fluid.SOURCE_ONLY : ClipContext.Fluid.NONE);
        InteractionResultHolder<ItemStack> ret = ForgeEventFactory.onBucketUse(player, level, itemstack, blockhitresult);
        if (ret != null) cir.setReturnValue(ret);
        if (blockhitresult.getType() == HitResult.Type.MISS) {
            cir.setReturnValue(InteractionResultHolder.pass(itemstack));
        } else if (blockhitresult.getType() != HitResult.Type.BLOCK) {
            cir.setReturnValue(InteractionResultHolder.pass(itemstack));
        } else {
            BlockPos blockpos = blockhitresult.getBlockPos();
            Direction direction = blockhitresult.getDirection();
            BlockPos blockpos1 = blockpos.relative(direction);
            if (level.mayInteract(player, blockpos) && player.mayUseItemAt(blockpos1, direction, itemstack)) {
                if (this.content == Fluids.EMPTY) {
                    BlockState blockstate1 = level.getBlockState(blockpos);
                    if (blockstate1.getBlock() instanceof BucketPickup) {
                        BucketPickup bucketpickup = (BucketPickup) blockstate1.getBlock();
                        if (NetheriteBucket.isBucketPickupFluid(bucketpickup)) {
                            if (itemstack.getCount() == 1) {
                                player.getInventory().removeItem(itemstack);
                            } else {
                                itemstack.setCount(itemstack.getCount() - 1);
                            }
                            player.playSound(SoundEvents.ITEM_BREAK, 1.0F, 1.0F);
                            player.hurt(DamageSource.ON_FIRE, 1.5f);
                            cir.setReturnValue(InteractionResultHolder.pass(itemstack));
                        } else {
                            ItemStack itemstack1 = bucketpickup.pickupBlock(level, blockpos, blockstate1);
                            if (!itemstack1.isEmpty()) {
                                player.awardStat(Stats.ITEM_USED.get(this));
                                bucketpickup.getPickupSound(blockstate1).ifPresent((p_150709_) -> player.playSound(p_150709_, 1.0F, 1.0F));
                                level.gameEvent(player, GameEvent.FLUID_PICKUP, blockpos);
                                ItemStack itemstack2 = ItemUtils.createFilledResult(itemstack, player, itemstack1);
                                if (!level.isClientSide) {
                                    CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayer) player, itemstack1);
                                }
                                cir.setReturnValue(InteractionResultHolder.sidedSuccess(itemstack2, level.isClientSide()));
                            }
                        }
                    }
                    cir.setReturnValue(InteractionResultHolder.fail(itemstack));
                } else {
                    BlockState blockstate = level.getBlockState(blockpos);
                    BlockPos blockpos2 = this.canBlockContainFluid(level, blockpos, blockstate) ? blockpos : blockpos1;
                    if (((BucketItem) (Object) this).emptyContents(player, level, blockpos2, blockhitresult, itemstack)) {
                        ((BucketItem) (Object) this).checkExtraContent(player, level, itemstack, blockpos2);
                        if (player instanceof ServerPlayer) {
                            CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer) player, blockpos2, itemstack);
                        }

                        player.awardStat(Stats.ITEM_USED.get((BucketItem) (Object) this));
                        cir.setReturnValue(InteractionResultHolder.sidedSuccess(((BucketItem) (Object) this).getEmptySuccessItem(itemstack, player), level.isClientSide()));
                    } else {
                        cir.setReturnValue(InteractionResultHolder.fail(itemstack));
                    }
                }
            } else {
                cir.setReturnValue(InteractionResultHolder.fail(itemstack));
            }
        }
    }
}
