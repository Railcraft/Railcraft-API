/*------------------------------------------------------------------------------
 Copyright (c) CovertJaguar, 2011-2017

 This work (the API) is licensed under the "MIT" License,
 see LICENSE.md for details.
 -----------------------------------------------------------------------------*/

package mods.railcraft.api.charge;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

/**
 * Keeps the cart battery capability instance.
 */
public final class CapabilitiesCharge {
    @CapabilityInject(ICartBattery.class)
    public static Capability<ICartBattery> CART_BATTERY;

    private CapabilitiesCharge() {
    }
}
