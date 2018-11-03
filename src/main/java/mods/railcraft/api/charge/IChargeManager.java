/*------------------------------------------------------------------------------
 Copyright (c) CovertJaguar, 2011-2018

 This work (the API) is licensed under the "MIT" License,
 see LICENSE.md for details.
 -----------------------------------------------------------------------------*/

package mods.railcraft.api.charge;

import net.minecraft.world.World;

/**
 * This is how you get access to the meat of the charge network.
 */
public interface IChargeManager {

    /**
     * The network is the primary means of interfacing with charge.
     */
    default IChargeNetwork network(World world) {
        return new IChargeNetwork() {
        };
    }
}
