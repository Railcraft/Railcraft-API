/*------------------------------------------------------------------------------
 Copyright (c) CovertJaguar, 2011-2018

 This work (the API) is licensed under the "MIT" License,
 see LICENSE.md for details.
 -----------------------------------------------------------------------------*/

package mods.railcraft.api.charge;

/**
 * Batteries the heart of the Charge Network.
 *
 * Consumers and 'wires' don't need batteries, but generators and battery blocks do.
 *
 * You don't to have a Tile Entity to provide a battery for the network,
 * serialization and ticking is handled by the network itself.
 *
 * Generators should add their power output directly to its battery object.
 *
 * You shouldn't hold onto battery objects for longer than you need them.
 * The API makes no guarantee that the battery object assigned to a specific coordinate
 * will always be the same object.
 *
 * Such that sometimes:
 * {@code
 * IBatteryBlock bat1 = Charge.distribution.network(world).access(pos).getBattery();
 * IBatteryBlock bat2 = Charge.distribution.network(world).access(pos).getBattery();
 * bat1 != bat2
 * }
 *
 * Created by CovertJaguar on 10/27/2018 for Railcraft.
 *
 * @author CovertJaguar <http://www.railcraft.info>
 */
public
interface IBatteryBlock extends IBattery {
    enum State {
        /**
         * Infinite Batteries will supply an infinite amount of power to the network.
         */
        INFINITE,
        /**
         * Rechargeable batteries can be filled and drained indefinitely.
         * The charge network will balance the change level between all
         * the rechargeable batteries in the network.
         *
         * Generators should posses a small rechargeable battery just large enough to hold
         * the generator's max per tick output with a similar draw level and 100% efficiency.
         */
        RECHARGEABLE,
        /**
         * Disposable batteries are excluded from the charge network's level balancing.
         * They will be drained after rechargeable batteries.
         */
        DISPOSABLE,
        DISABLED
    }

    /**
     * Gets the current state of the battery.
     *
     * @return The battery's state.
     */
    default State getState() {
        return State.RECHARGEABLE;
    }

    /**
     * Sets the current state of the battery.
     *
     * The state of a battery is always under the control of the client,
     * the network will never change it for you.
     *
     * @param stateImpl The battery's new state.
     */
    default void setState(State stateImpl) {
    }

    class Spec {
        private final IBatteryBlock.State initialState;
        private final double capacity;
        private final double maxDraw;
        private final double efficiency;

        /**
         * @param initialState The initial state of the battery.
         * @param capacity     The capacity of the battery.
         * @param maxDraw      How much charge can be drawn from this battery per tick.
         * @param efficiency   How efficient it is to draw from this battery. When you extract charge from the network,
         *                     the network averages the efficiency of every "battery" on the grid and multiples the charge
         *                     removed by that average. Large numbers of more efficient "batteries" make the grid more
         *                     efficient.
         *
         *                     Generators should generally have this set to 1.0.
         *                     Converters should have a low efficiency like 0.65.
         *                     Battery blocks should be less than 1.0, but can go as low as you want.
         */
        public Spec(IBatteryBlock.State initialState, double capacity, double maxDraw, double efficiency) {
            this.initialState = initialState;
            this.capacity = capacity;
            this.maxDraw = maxDraw;
            this.efficiency = efficiency;
        }

        public IBatteryBlock.State getInitialState() {
            return initialState;
        }

        public double getCapacity() {
            return capacity;
        }

        public double getMaxDraw() {
            return maxDraw;
        }

        public double getEfficiency() {
            return efficiency;
        }

        @Override
        public String toString() {
            return String.format("Battery{Cap: %.2f, Draw: %.2f, Eff: %.2f}", capacity, maxDraw, efficiency);
        }
    }
}
