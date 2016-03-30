/*
 * ******************************************************************************
 *  Copyright 2011-2015 CovertJaguar
 *
 *  This work (the API) is licensed under the "MIT" License, see LICENSE.md for details.
 * ***************************************************************************
 */

package mods.railcraft.api.tracks;

import net.minecraft.block.properties.PropertyBool;

/**
 * Implementing this interface will allow your track to be
 * powered via Redstone.
 * <p/>
 * And so long as you inherit from TrackInstanceBase, all the code for updating
 * the power state is already in place (including propagation).
 *
 * @author CovertJaguar <http://www.railcraft.info>
 */
public interface ITrackPowered extends ITrackInstance {

    PropertyBool POWERED = PropertyBool.create("powered");

    /**
     * Getter for a simple boolean variable for caching the power state.
     */
    boolean isPowered();

    /**
     * Setter for a simple boolean variable for caching the power state.
     */
    void setPowered(boolean powered);

    /**
     * The distance that a redstone signal will be passed along from track to track.
     */
    int getPowerPropagation();

    /**
     * Allows finer control of whether tracks can pass power.
     * <p/>
     * Note: At the current time, the system only supports tracks with the same TrackSpec passing along power.
     * This function is to allow finer control within the spec.
     * Example, for tracks with multiple modes like the Coupler Track.
     */
    boolean canPropagatePowerTo(ITrackInstance track);
}
