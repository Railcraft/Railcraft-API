/*
 * ******************************************************************************
 *  Copyright 2011-2015 CovertJaguar
 *
 *  This work (the API) is licensed under the "MIT" License, see LICENSE.md for details.
 * ***************************************************************************
 */

package mods.railcraft.api.core.items;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRailBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

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
     * @param stack      The track to place
     * @param world      The World object
     * @param pos        The position
     * @param trackShape The preferred EnumRailDirection. May be null. If the shape is invalid for your track, use your default value.
     * @return true if successful
     */
    boolean placeTrack(ItemStack stack, @Nullable EntityPlayer player, World world, BlockPos pos, @Nullable BlockRailBase.EnumRailDirection trackShape);

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
     */
    boolean isPlacedTileEntity(ItemStack stack, TileEntity tile);
}
