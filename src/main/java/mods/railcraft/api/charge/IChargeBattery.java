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
     * <p>The return value indicates that some of the charge offered cannot be accepted;
     * it is usually caused by the capacity limit.</p>
     *
     * @param charge The charge intended to add
     * @return The rejected charge amount
     */
    double addCharge(double charge);

    /**
     * Removes some charge from the battery.
     *
     * @param charge The maximum amount of charge requested
     * @return The amount of charge removed
     */
    double removeCharge(double charge);
}
