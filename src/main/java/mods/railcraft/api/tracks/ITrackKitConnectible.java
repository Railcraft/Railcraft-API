/*------------------------------------------------------------------------------
 Copyright (c) CovertJaguar, 2011-2019

 This work (the API) is licensed under the "MIT" License,
 see LICENSE.md for details.
 -----------------------------------------------------------------------------*/

package mods.railcraft.api.tracks;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

/**
 * Allow fences, etc. to connect to an outfitted track.
 */
public interface ITrackKitConnectible {

    /**
     * Check to allow fences, etc. to connect to the track.
     *
     * @see net.minecraft.block.Block#canBeConnectedTo(IBlockAccess, BlockPos, EnumFacing)
     */
    boolean canBeConnectedTo(EnumFacing facing);
}
