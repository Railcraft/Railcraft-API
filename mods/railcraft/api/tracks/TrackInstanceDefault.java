/*******************************************************************************
 * Copyright (c) CovertJaguar, 2011-2016
 *
 * This work (the API) is licensed under the "MIT" License,
 * see LICENSE.md for details.
 ******************************************************************************/
package mods.railcraft.api.tracks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author CovertJaguar <http://www.railcraft.info>
 */
public class TrackInstanceDefault extends TrackInstanceBase {
    private final boolean swapOut;

    public TrackInstanceDefault() {
        this(true);
    }

    public TrackInstanceDefault(boolean swapOut) {
        this.swapOut = swapOut;
    }

    @Nonnull
    @Override
    public TrackSpec getTrackSpec() {
        return TrackRegistry.getTrackSpec("Railcraft:default");
    }

    @Override
    public boolean isFlexibleRail() {
        return true;
    }

    @Override
    public boolean canUpdate() {
        return swapOut;
    }

    @Override
    public void update() {
        swapTrack();
    }

    @Override
    public void onNeighborBlockChange(IBlockState state, @Nullable Block neighborBlock) {
        super.onNeighborBlockChange(state, neighborBlock);
        swapTrack();
    }

    private void swapTrack() {
        World world = theWorld();
        if (swapOut && world != null && !world.isRemote) {
            IBlockState oldState = world.getBlockState(getPos());
            BlockRailBase oldBlock = (BlockRailBase) oldState.getBlock();
            BlockRailBase newBlock = (BlockRailBase) Blocks.RAIL;
            IBlockState newState = newBlock.getDefaultState().withProperty(newBlock.getShapeProperty(), oldState.getValue(oldBlock.getShapeProperty()));
            world.setBlockState(getPos(), newState);
        }
    }
}
