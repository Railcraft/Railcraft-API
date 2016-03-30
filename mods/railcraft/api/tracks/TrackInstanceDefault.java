/*******************************************************************************
 * Copyright (c) CovertJaguar, 2011-2016
 *
 * This work (the API) is licensed under the "MIT" License,
 * see LICENSE.md for details.
 ******************************************************************************/
package mods.railcraft.api.tracks;

import mods.railcraft.common.util.misc.Game;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;

/**
 * @author CovertJaguar <http://www.railcraft.info>
 */
public class TrackInstanceDefault extends TrackInstanceBase {

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
        return true;
    }

    @Override
    public void update() {
        if (Game.isHost(getWorld())) {
            IBlockState oldState = getWorld().getBlockState(getPos());
            BlockRailBase oldBlock = (BlockRailBase) oldState.getBlock();
            BlockRailBase newBlock = (BlockRailBase) Blocks.rail;
            IBlockState newState = newBlock.getDefaultState().withProperty(newBlock.getShapeProperty(), oldState.getValue(oldBlock.getShapeProperty()));
            getWorld().setBlockState(getPos(), newState);
        }
    }
}
