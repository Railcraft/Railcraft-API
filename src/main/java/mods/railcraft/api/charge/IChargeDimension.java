package mods.railcraft.api.charge;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Represents a charge manager for a server-side {@link World}.
 */
public interface IChargeDimension {

    /**
     * Gets the world it manages.
     *
     * @return The world
     */
    World getWorld();

    /**
     * Registers a charge node.
     *
     * <p>It is suggested to call {@link IChargeBlock#registerNode(IBlockState, World, BlockPos)}
     * instead.</p>
     *
     * @param pos The block position
     * @param def The charge node definition
     */
    void registerChargeNode(BlockPos pos, ChargeNodeDefinition def);

    /**
     * Removes a charge node.
     *
     * <p>It is suggested to call {@link IChargeBlock#deregisterNode(World, BlockPos)} instead.</p>
     *
     * @param pos The block position
     */
    void deregisterChargeNode(BlockPos pos);

    /**
     * Accesses the charge node at a certain position.
     *
     * <p>If a valid node is not available, a node with {@link IChargeNode#isNull()} equivalent to
     * {@code true} is returned.</p>
     *
     * @param pos The position
     * @return A node retrieved
     */
    IChargeNode getNode(BlockPos pos);
}
