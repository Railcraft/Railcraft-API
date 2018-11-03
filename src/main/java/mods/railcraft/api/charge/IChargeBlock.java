/*------------------------------------------------------------------------------
 Copyright (c) CovertJaguar, 2011-2018

 This work (the API) is licensed under the "MIT" License,
 see LICENSE.md for details.
 -----------------------------------------------------------------------------*/

package mods.railcraft.api.charge;

import com.google.common.annotations.Beta;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Random;

/**
 * This interface must be implement by any {@link net.minecraft.block.Block}
 * that wants to interface with any of the charge networks.
 *
 * Created by CovertJaguar on 7/25/2016 for Railcraft.
 *
 * @author CovertJaguar <http://www.railcraft.info>
 */
public interface IChargeBlock {

    /**
     * Asks the Block to provide a charge definition for the requesting network.
     *
     * This method can be called by any network, respond accordingly.
     *
     * It is generally to be considered an error to return the same charge definition to multiple networks.
     * Most blocks will probably be members of the {@link Charge#distribution} network only.
     *
     * Only "transformer" blocks that pass charge from one network to another should respond to multiple networks.
     *
     * If there is any way to better enforce/indicate this requirement, I haven't discovered it.
     * I expect this will be a frequent source of bugs caused by improper implementation.
     *
     * SO PAY ATTENTION!
     *
     * @param network The network type which is requesting a charge definition. Most blocks should only respond to one
     *                type of network.
     */
    @Nullable IChargeBlock.ChargeSpec getChargeDef(Charge network, IBlockState state, IBlockAccess world, BlockPos pos);

    /**
     * The Charge Meter calls this to get a node for meter readings.
     *
     * Most blocks don't need to touch this, but Multi-blocks may want to redirect to the master block.
     */
    @Beta
    default Charge.IAccess getMeterAccess(IBlockState state, World world, BlockPos pos) {
        return Charge.distribution.network(world).access(pos);
    }

    /**
     * Helper method for registering a block to the networks.
     *
     * This function must be called from the following functions:
     * {@link net.minecraft.block.Block#onBlockAdded(World, BlockPos, IBlockState)}
     * {@link net.minecraft.block.Block#updateTick(World, BlockPos, IBlockState, Random)}
     *
     * The block must set {@link net.minecraft.block.Block#setTickRandomly(boolean)} to true in the constructor.
     */
    default void registerNode(IBlockState state, World world, BlockPos pos) {
        Arrays.stream(Charge.values()).forEach(n -> n.network(world).addNode(pos, state));
    }

    /**
     * Helper method for removing a block from the networks.
     *
     * This function must be called from the following function:
     * {@link net.minecraft.block.Block#breakBlock(World, BlockPos, IBlockState)}
     */
    default void deregisterNode(World world, BlockPos pos) {
        Arrays.stream(Charge.values()).forEach(n -> n.network(world).removeNode(pos));
    }

    enum ConnectType {
        BLOCK, SLAB, TRACK, WIRE
    }

    final class ChargeSpec {
        private final ConnectType connectType;
        private final double losses;
        private final @Nullable IBatteryBlock.Spec batterySpec;

        public ChargeSpec(ConnectType connectType, double losses) {
            this(connectType, losses, null);
        }

        /**
         * @param connectType This controls how our block will connect to other blocks.
         *                    Many blocks can only connect in specific ways due to block shape.
         * @param losses      The cost of connecting this block to charge network due to resistance losses, etc.
         * @param batterySpec The battery specification for our block. Batteries are optional.
         */
        public ChargeSpec(ConnectType connectType, double losses, @Nullable IBatteryBlock.Spec batterySpec) {
            this.connectType = connectType;
            this.losses = losses;
            this.batterySpec = batterySpec;
        }

        public double getLosses() {
            return losses;
        }

        public @Nullable IBatteryBlock.Spec getBatterySpec() {
            return batterySpec;
        }

        public ConnectType getConnectType() {
            return connectType;
        }

        @Override
        public String toString() {
            String string = String.format("ChargeSpec{%s, losses=%.2f}", connectType, losses);
            if (batterySpec != null)
                string += "|" + batterySpec;
            return string;
        }

    }

}
