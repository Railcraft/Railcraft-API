/*------------------------------------------------------------------------------
 Copyright (c) CovertJaguar, 2011-2018

 This work (the API) is licensed under the "MIT" License,
 see LICENSE.md for details.
 -----------------------------------------------------------------------------*/

package mods.railcraft.api.charge;

/**
 * Base interface for charge batteries.
 */
public interface IChargeBattery {

    /**
     * Gets the charge in the battery.
     *
     * <p>It does not exceed {@link #getCapacity()}.</p>
     *
     * @return The charge
     */
    double getCharge();

    /**
     * Gets the maximum charge the battery can have.
     *
     * @return The maximum charge
     */
    double getCapacity();

    /**
     * Sets the charge in the battery.
     *
     * @param charge The target amount
     */
    void setCharge(double charge);

    /**
     * Adds some charge to the battery.
     *
     * You are responsible for ensuring that you don't add charge to a full battery.
     *
     * Batteries can have slightly more charge than the max capacity for performance reasons.
     *
     * @param charge The charge intended to add
     */
    void addCharge(double charge);

    /**
     * Removes some charge from the battery.
     *
     * @param charge The maximum amount of charge requested
     * @return The amount of charge removed
     */
    double removeCharge(double charge);
}
