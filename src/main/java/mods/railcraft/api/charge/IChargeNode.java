package mods.railcraft.api.charge;

import net.minecraft.util.math.BlockPos;

import java.util.function.BiConsumer;

/**
 * Represents a node in the charge system in a world at a position.
 */
public interface IChargeNode {

    /**
     * Checks if a request for an amount of charge can be fulfilled.
     *
     * @param amount The amount to fulfill
     * @return True if a use charge request can be fully met
     */
    boolean canUseCharge(double amount);

    /**
     * Requests to remove an amount of charge, which must be fulfilled.
     *
     * <p>When the method returns {@code false}, no change is made.</p>
     *
     * @param amount The amount
     * @return True the amount of charge is totally removed
     */
    boolean useCharge(double amount);

    /**
     * Requests to remove at most an amount of charge.
     *
     * @param desiredAmount The maximum amount to remove
     * @return The amount of charge actually removed
     */
    double removeCharge(double desiredAmount);

    /**
     * Adds a listener that is triggered when charge is used.
     *
     * @param listener The listener
     */
    void addListener(BiConsumer<? super IChargeNode, ? super Double> listener);

    /**
     * Removes a listener that is triggered when charge is used.
     *
     * <p>This removal is identity-based.</p>
     *
     * @param listener The listener
     */
    void removeListener(BiConsumer<? super IChargeNode, ? super Double> listener);

    /**
     * Loads a block-entity-based battery.
     *
     * <p>It is suggested to call {@link IBatteryTile#loadBattery()} instead.</p>
     */
    void loadBattery();

    /**
     * Unloads a block-entity-based battery.
     *
     * <p>It is suggested to call {@link IBatteryTile#unloadBattery()} instead.</p>
     */
    void unloadBattery();

    /**
     * Returns if this node is a null one or a dummy one.
     *
     * @return True if the node is not valid
     */
    boolean isNull();

    /**
     * Returns the charge dimension this node belongs to.
     */
    IChargeDimension getDimension();

    /**
     * Returns the block position of the node in the world.
     */
    BlockPos getPos();

}
