/*------------------------------------------------------------------------------
 Copyright (c) CovertJaguar, 2011-2016

 This work (the API) is licensed under the "MIT" License,
 see LICENSE.md for details.
 -----------------------------------------------------------------------------*/

package mods.railcraft.api.tracks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * Created by CovertJaguar on 8/10/2016 for Railcraft.
 *
 * @author CovertJaguar <http://www.railcraft.info>
 */
public interface ITrackType {
    String NBT_TAG = "kit";

    String getRegistryName();

    default float getResistance() {
        return 3.5F;
    }

    default void onMinecartPass(World world, EntityMinecart cart, BlockPos pos, @Nullable TrackKit trackKit) {
    }

    default void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity) {
    }

    default float getMaxSpeed(World world, EntityMinecart cart, BlockPos pos) {
        return 0.4f;
    }
}
