/*
 * ******************************************************************************
 *  Copyright 2011-2015 CovertJaguar
 *
 *  This work (the API) is licensed under the "MIT" License, see LICENSE.md for details.
 * ***************************************************************************
 */

package mods.railcraft.api.tracks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

/**
 * Used by rails that modify the bounding boxes.
 *
 * For example, the Gated Rails.
 *
 * Not very useful since there is no system in place to insert custom render code.
 *
 * @author CovertJaguar <http://www.railcraft.info>
 */
public interface ITrackCustomShape extends ITrackInstance {

    AxisAlignedBB getCollisionBoundingBox(IBlockState state);

    AxisAlignedBB getSelectedBoundingBox();

    MovingObjectPosition collisionRayTrace(Vec3 vec3d, Vec3 vec3d1);
}
