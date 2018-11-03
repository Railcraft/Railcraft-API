/*------------------------------------------------------------------------------
 Copyright (c) CovertJaguar, 2011-2018

 This work (the API) is licensed under the "MIT" License,
 see LICENSE.md for details.
 -----------------------------------------------------------------------------*/

package mods.railcraft.api.charge;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

/**
 * Created by CovertJaguar on 11/2/2018 for Railcraft.
 *
 * @author CovertJaguar <http://www.railcraft.info>
 */
public interface IChargeAccess {
    /**
     * Returns whether the network contains the requested charge amount and enough excess charge to extract it.
     *
     * This operation takes into account the grid's efficiency value.
     *
     * @return true if there is enough charge in the network to withdraw the requested amount.
     */
    default boolean hasCapacity(double amount) {
        return false;
    }

    /**
     * Remove the requested amount of charge if possible and
     * return whether sufficient charge was available to perform the operation.
     *
     * @return true if charge could be removed in full
     */
    default boolean useCharge(double amount) {
        return false;
    }

    /**
     * Removes as much of the desiredAmount of charge as possible from the gird.
     *
     * @return amount removed, may be less than desiredAmount
     */
    default double removeCharge(double desiredAmount) {
        return 0.0;
    }

    /**
     * Get the node's battery object.
     *
     * Don't hold onto this reference, just grab it from the network as needed.
     *
     * @return The battery object.
     */
    default @Nullable IBatteryBlock getBattery() {
        return null;
    }

    /**
     * Can be returned from {@link net.minecraft.block.Block#getComparatorInputOverride(IBlockState, World, BlockPos)}.
     *
     * @return The current storage percentage of the entire grid.
     */
    default int getComparatorOutput() {
        return 0;
    }
}
