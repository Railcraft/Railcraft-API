/*
 * ******************************************************************************
 *  Copyright 2011-2015 CovertJaguar
 *
 *  This work (the API) is licensed under the "MIT" License, see LICENSE.md for details.
 * ***************************************************************************
 */

package mods.railcraft.api.core.items;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

/**
 * Should be implemented by any rail item class that wishes to have
 * it's rails placed by for example the Tunnel Bore or Track Relayer.
 * <p/>
 * If you defined your rails with a TrackSpec, you don't need to worry about this.
 *
 * @author CovertJaguar <http://www.railcraft.info>
 */
public interface ITrackItem {

    /**
     * Attempts to place a track.
     *
     * @param stack
     * @param world The World object
     * @param pos   the position
     * @return true if successful
     */
    boolean placeTrack(ItemStack stack, World world, BlockPos pos);

    /**
     * Return the block of a placed track.
     *
     * @return the blockId
     */
    Block getPlacedBlock();

    /**
     * Return true if the given tile entity corresponds to this Track item.
     * <p/>
     * If the track has no tile entity, return true on null.
     *
     * @param stack
     * @param tile
     * @return
     */
    boolean isPlacedTileEntity(ItemStack stack, TileEntity tile);
}
