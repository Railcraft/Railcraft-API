/*------------------------------------------------------------------------------
 Copyright (c) CovertJaguar, 2011-2016

 This work (the API) is licensed under the "MIT" License,
 see LICENSE.md for details.
 -----------------------------------------------------------------------------*/

package mods.railcraft.api.tracks;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Have your ITrackInstance implement this to override normal track placement.
 *
 * Used by tracks such as the Suspended Track.
 *
 * @author CovertJaguar <http://www.railcraft.info>
 */
@Deprecated
public interface ITrackKitCustomPlaced extends ITrackKitInstance {

    /**
     * Used to override normal track placement.
     *
     * Used by tracks such as the Suspended Track.
     *
     * Warning: This is called before the TileEntity is set.
     *
     * @param world The World
     * @return true if the rail can placed at the specified location, false to prevent placement
     */
    boolean canPlaceRailAt(World world, BlockPos pos);
}
