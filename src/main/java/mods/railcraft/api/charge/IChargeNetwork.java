/*------------------------------------------------------------------------------
 Copyright (c) CovertJaguar, 2011-2018

 This work (the API) is licensed under the "MIT" License,
 see LICENSE.md for details.
 -----------------------------------------------------------------------------*/

package mods.railcraft.api.charge;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Created by CovertJaguar on 10/19/2018 for Railcraft.
 *
 * @author CovertJaguar <http://www.railcraft.info>
 */
public interface IChargeNetwork {

    /**
     * Queues the node to be added to the network.
     *
     * If you pass a null chargeDef, nothing will happen.
     *
     * @return return true if the network changed.
     */
    default boolean addNode(IBlockState state, World world, BlockPos pos) {
        return false;
    }

    /**
     * Queues the node to be removed to the network
     */
    default void removeNode(BlockPos pos) {
    }

    /**
     * Get a grid access point for the position.
     *
     * @return A grid access point, may be a dummy object if there is no valid grid at the location.
     */
    default IChargeAccess access(BlockPos pos) {
        return new IChargeAccess() {
        };
    }

    /**
     * Apply Charge damage to the target entity from the current network.
     */
    default void zap(BlockPos pos, Entity entity, DamageOrigin origin, float damage) {
    }

    enum DamageOrigin {
        BLOCK, TRACK
    }
}
