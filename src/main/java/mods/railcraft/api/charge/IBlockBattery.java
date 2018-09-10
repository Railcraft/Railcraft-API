/*------------------------------------------------------------------------------
 Copyright (c) CovertJaguar, 2011-2017

 This work (the API) is licensed under the "MIT" License,
 see LICENSE.md for details.
 -----------------------------------------------------------------------------*/

package mods.railcraft.api.charge;

/**
 * Common interface for block-based batteries.
 */
public interface IBlockBattery extends IChargeBattery {
    /**
     * Gets the maximum per-tick draw of charge.
     *
     * @return The draw
     */
    double getMaxDraw();

    /**
     * Gets the efficiency of charge drawing.
     *
     * <p>This ratio should be between {@code 0.0D} and {@code 1.0D}.</p>
     *
     * @return The efficiency
     */
    double getEfficiency();

    /**
     * Initializes a charge battery with the amount of charge read from the storage
     * (of a block entity or the world storage).
     *
     * @param charge The set charge amount
     */
    void initCharge(double charge);

    /**
     * Used by railcraft to calculate the charge available from a battery in a tick.
     *
     * @return The available charge
     */
    default double getAvailableCharge() {
        return Math.min(getCharge(), getMaxDraw());
    }
}
