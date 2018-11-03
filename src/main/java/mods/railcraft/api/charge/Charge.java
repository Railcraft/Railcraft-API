/*------------------------------------------------------------------------------
 Copyright (c) CovertJaguar, 2011-2018

 This work (the API) is licensed under the "MIT" License,
 see LICENSE.md for details.
 -----------------------------------------------------------------------------*/

package mods.railcraft.api.charge;

import com.google.common.annotations.Beta;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

/**
 * The heart of the Charge system is here.
 *
 * And block that wants to interact with the Charge network should implement IChargeBlock and ensure that they
 * call the proper add/remove functions.
 *
 * Everything else is done through {@link IAccess}.
 *
 * Example code:
 * {@code Charge.distribution.network(world).access(pos).useCharge(500)}
 *
 * Created by CovertJaguar on 10/19/2018 for Railcraft.
 *
 * @author CovertJaguar <http://www.railcraft.info>
 */
public enum Charge {
    /**
     * The distribution network is the charge network used by standard consumers, wires, tracks, and batteries.
     *
     * This is the only network currently implemented and currently covers all use cases.
     */
    distribution,
    /**
     * The transmission network is the charge network used by low maintenance transmission lines and transformers,
     * consumers should not access this network directly.
     *
     * Not currently implemented.
     */
    @Beta
    transmission,
    /**
     * The rail network is the charge network used by tracks and the carts on them.
     *
     * Not currently implemented.
     */
    @Beta
    rail,
    /**
     * The catenary network is the charge network used by catenaries and the carts below them.
     *
     * Not currently implemented.
     */
    @Beta
    catenary;

    /**
     * This is how you get access to the meat of the charge network.
     */
    public INetwork network(World world) {
        return manager.network(world);
    }

    /**
     * Entry point for rendering charge related effects.
     */
    public static IZapEffectRenderer effects() {
        return effects;
    }

    public interface IManager {

        /**
         * The network is the primary means of interfacing with charge.
         */
        default INetwork network(World world) {
            return new INetwork() {
            };
        }
    }

    /**
     * Created by CovertJaguar on 10/19/2018 for Railcraft.
     *
     * @author CovertJaguar <http://www.railcraft.info>
     */
    public interface INetwork {

        /**
         * Queues the node to be added to the network.
         *
         * If you pass a null chargeDef, nothing will happen.
         *
         * @return return true if the network changed.
         */
        default boolean addNode(BlockPos pos, IBlockState state) {
            return false;
        }

        /**
         * Queues the node to be removed to the network
         */
        default void removeNode(BlockPos pos) {
        }

        /**
         * Get a grid access point for the position.
         *
         * @return A grid access point, may be a dummy object if there is no valid grid at the location.
         */
        default IAccess access(BlockPos pos) {
            return new IAccess() {
            };
        }

    }

    /**
     * Created by CovertJaguar on 11/2/2018 for Railcraft.
     *
     * @author CovertJaguar <http://www.railcraft.info>
     */
    public interface IAccess {
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

        /**
         * Apply Charge damage to the target entity from the current network.
         */
        default void zap(Entity entity, DamageOrigin origin, float damage) {
        }

    }

    public enum DamageOrigin {
        BLOCK, TRACK
    }

    public interface IZapEffectRenderer {
        /**
         * Helper method that most blocks can use for spark effects. It has a chance of calling
         * {@link #zapEffectSurface(IBlockState, World, BlockPos)}.
         *
         * The chance is increased if its raining.
         *
         * @param chance Integer value such that chance of sparking is defined by {@code rand.nextInt(chance) == 0}
         *               Most blocks use 50, tracks use 75. Lower numbers means more frequent sparks.
         */
        default void throwSparks(IBlockState state, World world, BlockPos pos, Random rand, int chance) {
        }

        /**
         * Spawns a single spark from a point source.
         *
         * @param source Can be a TileEntity, Entity, BlockPos, or Vec3d
         * @throws IllegalArgumentException If source is of an unexpected type.
         */
        default void zapEffectPoint(World world, Object source) {
        }

        /**
         * Spawns a lot of sparks from a point source.
         *
         * @param source Can be a TileEntity, Entity, BlockPos, or Vec3d
         * @throws IllegalArgumentException If source is of an unexpected type.
         */
        default void zapEffectDeath(World world, Object source) {
        }

        /**
         * Spawns a spark from the surface of each rendered side of a block.
         */
        default void zapEffectSurface(IBlockState stateIn, World worldIn, BlockPos pos) {
        }
    }

    /**
     * User's shouldn't touch this. It's set using reflection by Railcraft.
     */
    @SuppressWarnings("CanBeFinal")
    private IManager manager = new IManager() {
    };

    /**
     * User's shouldn't touch this. It's set using reflection by Railcraft.
     */
    @SuppressWarnings("CanBeFinal")
    private static IZapEffectRenderer effects = new IZapEffectRenderer() {
    };

}
