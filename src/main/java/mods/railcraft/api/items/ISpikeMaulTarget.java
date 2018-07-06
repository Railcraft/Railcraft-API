/*------------------------------------------------------------------------------
 Copyright (c) CovertJaguar, 2011-2017

 This work (the API) is licensed under the "MIT" License,
 see LICENSE.md for details.
 -----------------------------------------------------------------------------*/

package mods.railcraft.api.items;

import mods.railcraft.api.tracks.IBlockTrackOutfitted;
import mods.railcraft.api.tracks.TrackKit;
import mods.railcraft.api.tracks.TrackToolsAPI;
import mods.railcraft.api.tracks.TrackType;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * Created by CovertJaguar on 3/6/2017 for Railcraft.
 *
 * @author CovertJaguar <http://www.railcraft.info>
 */
public interface ISpikeMaulTarget {
    List<ISpikeMaulTarget> spikeMaulTargets = new ArrayList<>();

    boolean matches(World world, BlockPos pos, IBlockState state);

    boolean setToTarget(World world, BlockPos pos, IBlockState state, EntityPlayer player, BlockRailBase.EnumRailDirection shape, TrackType trackType);

    class TrackKitTarget implements ISpikeMaulTarget {
        private final Supplier<TrackKit> trackKit;

        public TrackKitTarget(Supplier<TrackKit> trackKit) {
            this.trackKit = trackKit;
        }

        @Override
        public boolean matches(World world, BlockPos pos, IBlockState state) {
            return state.getBlock() instanceof IBlockTrackOutfitted
                    && ((IBlockTrackOutfitted) state.getBlock()).getTrackKit(world, pos) == trackKit.get();
        }

        @Override
        public boolean setToTarget(World world,
                                   BlockPos pos,
                                   IBlockState state,
                                   EntityPlayer player,
                                   BlockRailBase.EnumRailDirection shape,
                                   TrackType trackType) {
            return TrackToolsAPI.blockTrackOutfitted.place(world, pos, player, shape, trackType, trackKit.get());
        }
    }
}
