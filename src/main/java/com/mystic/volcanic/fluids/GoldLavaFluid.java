package com.mystic.volcanic.fluids;

import com.mystic.volcanic.init.BlockInit;
import com.mystic.volcanic.init.FluidInit;
import com.mystic.volcanic.init.FluidTypesInit;
import com.mystic.volcanic.init.ItemInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.FluidInteractionRegistry;
import net.minecraftforge.fluids.FluidType;

import javax.annotation.Nullable;
import java.util.Optional;

public abstract class IronLavaFluid extends FlowingFluid {
    public Fluid getFlowing() {
        return FluidInit.FLOWING_IRON_LAVA.get();
    }

    public Fluid getSource() {
        return FluidInit.IRON_LAVA.get();
    }

    public Item getBucket() {
        return ItemInit.IRON_LAVA_BUCKET.get();
    }

    @Override
    public FluidType getFluidType() {
        return FluidTypesInit.IRON_LAVA_FLUID_TYPE.get();
    }

    public void animateTick(Level p_230567_, BlockPos p_230568_, FluidState p_230569_, RandomSource p_230570_) {
        BlockPos blockpos = p_230568_.above();
        if (p_230567_.getBlockState(blockpos).isAir() && !p_230567_.getBlockState(blockpos).isSolidRender(p_230567_, blockpos)) {
            if (p_230570_.nextInt(100) == 0) {
                double d0 = (double) p_230568_.getX() + p_230570_.nextDouble();
                double d1 = (double) p_230568_.getY() + 1.0D;
                double d2 = (double) p_230568_.getZ() + p_230570_.nextDouble();
                p_230567_.addParticle(ParticleTypes.LAVA, d0, d1, d2, 0.0D, 0.0D, 0.0D);
                p_230567_.playLocalSound(d0, d1, d2, SoundEvents.LAVA_POP, SoundSource.BLOCKS, 0.2F + p_230570_.nextFloat() * 0.2F, 0.9F + p_230570_.nextFloat() * 0.15F, false);
            }

            if (p_230570_.nextInt(200) == 0) {
                p_230567_.playLocalSound((double) p_230568_.getX(), (double) p_230568_.getY(), (double) p_230568_.getZ(), SoundEvents.LAVA_AMBIENT, SoundSource.BLOCKS, 0.2F + p_230570_.nextFloat() * 0.2F, 0.9F + p_230570_.nextFloat() * 0.15F, false);
            }
        }

    }

    public void randomTick(Level p_230572_, BlockPos p_230573_, FluidState p_230574_, RandomSource p_230575_) {
        if (p_230572_.getGameRules().getBoolean(GameRules.RULE_DOFIRETICK)) {
            int i = p_230575_.nextInt(3);
            if (i > 0) {
                BlockPos blockpos = p_230573_;

                for (int j = 0; j < i; ++j) {
                    blockpos = blockpos.offset(p_230575_.nextInt(3) - 1, 1, p_230575_.nextInt(3) - 1);
                    if (!p_230572_.isLoaded(blockpos)) {
                        return;
                    }

                    BlockState blockstate = p_230572_.getBlockState(blockpos);
                    if (blockstate.isAir()) {
                        if (this.hasFlammableNeighbours(p_230572_, blockpos)) {
                            p_230572_.setBlockAndUpdate(blockpos, net.minecraftforge.event.ForgeEventFactory.fireFluidPlaceBlockEvent(p_230572_, blockpos, p_230573_, Blocks.FIRE.defaultBlockState()));
                            return;
                        }
                    } else if (blockstate.getMaterial().blocksMotion()) {
                        return;
                    }
                }
            } else {
                for (int k = 0; k < 3; ++k) {
                    BlockPos blockpos1 = p_230573_.offset(p_230575_.nextInt(3) - 1, 0, p_230575_.nextInt(3) - 1);
                    if (!p_230572_.isLoaded(blockpos1)) {
                        return;
                    }

                    if (p_230572_.isEmptyBlock(blockpos1.above()) && this.isFlammable(p_230572_, blockpos1, Direction.UP)) {
                        p_230572_.setBlockAndUpdate(blockpos1.above(), net.minecraftforge.event.ForgeEventFactory.fireFluidPlaceBlockEvent(p_230572_, blockpos1.above(), p_230573_, Blocks.FIRE.defaultBlockState()));
                    }
                }
            }

        }
    }

    private boolean hasFlammableNeighbours(LevelReader p_76228_, BlockPos p_76229_) {
        for (Direction direction : Direction.values()) {
            if (this.isFlammable(p_76228_, p_76229_.relative(direction), direction.getOpposite())) {
                return true;
            }
        }

        return false;
    }

    private boolean isFlammable(LevelReader level, BlockPos pos, Direction face) {
        return pos.getY() >= level.getMinBuildHeight() && pos.getY() < level.getMaxBuildHeight() && !level.hasChunkAt(pos) ? false : level.getBlockState(pos).isFlammable(level, pos, face);
    }

    @Nullable
    public ParticleOptions getDripParticle() {
        return ParticleTypes.DRIPPING_LAVA;
    }

    protected void beforeDestroyingBlock(LevelAccessor p_76216_, BlockPos p_76217_, BlockState p_76218_) {
        this.fizz(p_76216_, p_76217_);
    }

    public int getSlopeFindDistance(LevelReader p_76244_) {
        return p_76244_.dimensionType().ultraWarm() ? 4 : 2;
    }

    public BlockState createLegacyBlock(FluidState p_76249_) {
        return BlockInit.IRON_LAVA_BLOCK.get().defaultBlockState().setValue(LiquidBlock.LEVEL, Integer.valueOf(getLegacyLevel(p_76249_)));
    }

    public boolean isSame(Fluid p_76231_) {
        return p_76231_ == FluidInit.IRON_LAVA.get() || p_76231_ == FluidInit.FLOWING_IRON_LAVA.get();
    }

    public int getDropOff(LevelReader p_76252_) {
        return p_76252_.dimensionType().ultraWarm() ? 1 : 2;
    }

    public boolean canBeReplacedWith(FluidState p_76233_, BlockGetter p_76234_, BlockPos p_76235_, Fluid p_76236_, Direction p_76237_) {
        return p_76233_.getHeight(p_76234_, p_76235_) >= 0.44444445F && p_76236_.is(FluidTags.WATER);
    }

    public int getTickDelay(LevelReader p_76226_) {
        return p_76226_.dimensionType().ultraWarm() ? 10 : 30;
    }

    public int getSpreadDelay(Level p_76203_, BlockPos p_76204_, FluidState p_76205_, FluidState p_76206_) {
        int i = this.getTickDelay(p_76203_);
        if (!p_76205_.isEmpty() && !p_76206_.isEmpty() && !p_76205_.getValue(FALLING) && !p_76206_.getValue(FALLING) && p_76206_.getHeight(p_76203_, p_76204_) > p_76205_.getHeight(p_76203_, p_76204_) && p_76203_.getRandom().nextInt(4) != 0) {
            i *= 4;
        }

        return i;
    }

    private void fizz(LevelAccessor p_76213_, BlockPos p_76214_) {
        p_76213_.levelEvent(1501, p_76214_, 0);
    }

    protected boolean canConvertToSource() {
        return false;
    }

    protected void spreadTo(LevelAccessor level, BlockPos blockpos, BlockState blockState, Direction direction, FluidState fluidState) {
        if (direction == Direction.DOWN) {
            FluidState fluidstate = level.getFluidState(blockpos);
            if (fluidstate.is(FluidTags.WATER)) {
                if (blockState.getBlock() instanceof LiquidBlock) {
                    level.setBlock(blockpos, net.minecraftforge.event.ForgeEventFactory.fireFluidPlaceBlockEvent(level, blockpos, blockpos, Blocks.BASALT.defaultBlockState()), 3);
                }

                this.fizz(level, blockpos);
                return;
            }
        }

        super.spreadTo(level, blockpos, blockState, direction, fluidState);
    }

    public static void interactions() {
        FluidInteractionRegistry.addInteraction(FluidTypesInit.IRON_LAVA_FLUID_TYPE.get(), new FluidInteractionRegistry.InteractionInformation(ForgeMod.WATER_TYPE.get(),
                fluidState -> fluidState.isSource() ? Blocks.RAW_IRON_BLOCK.defaultBlockState() : Blocks.SMOOTH_BASALT.defaultBlockState()
        ));
    }

    protected boolean isRandomlyTicking() {
        return true;
    }

    protected float getExplosionResistance() {
        return 100.0F;
    }

    public Optional<SoundEvent> getPickupSound() {
        return Optional.of(SoundEvents.BUCKET_FILL_LAVA);
    }

    public static class Flowing extends IronLavaFluid {
        protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> p_76260_) {
            super.createFluidStateDefinition(p_76260_);
            p_76260_.add(LEVEL);
        }

        public int getAmount(FluidState p_76264_) {
            return p_76264_.getValue(LEVEL);
        }

        public boolean isSource(FluidState p_76262_) {
            return false;
        }
    }

    public static class Source extends IronLavaFluid {
        public int getAmount(FluidState p_76269_) {
            return 8;
        }

        public boolean isSource(FluidState p_76267_) {
            return true;
        }
    }
}
