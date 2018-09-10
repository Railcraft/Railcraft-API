/*------------------------------------------------------------------------------
 Copyright (c) CovertJaguar, 2011-2017
 http://railcraft.info

 This code is the property of CovertJaguar
 and may only be used with explicit written
 permission unless otherwise specified on the
 license page at http://railcraft.info/wiki/info:license.
 -----------------------------------------------------------------------------*/

package mods.railcraft.api.charge;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

/**
 * Indicates that a block may be charge nodes. Implemented by the block class.
 */
public interface IChargeBlock {

    /**
     * Defines the features of the charge node created for this block.
     *
     * @see ChargeNodeDefinition
     * @param state The block state
     * @param world The world
     * @param pos The position
     * @return The charge node definition
     */
    @Nullable
    ChargeNodeDefinition getChargeDef(IBlockState state, IBlockAccess world, BlockPos pos);

    /**
     * Registers a charge node.
     * It is strongly recommended to call this method in
     * {@link net.minecraft.block.Block#onBlockAdded(World, BlockPos, IBlockState)}.
     *
     * @param state The block state
     * @param world The world
     * @param pos The position
     */
    default void registerNode(IBlockState state, World world, BlockPos pos) {
        ChargeNodeDefinition chargeDef = getChargeDef(state, world, pos);
        if (chargeDef != null)
            ChargeToolsApi.getDimension(world).registerChargeNode(pos, chargeDef);
    }

    /**
     * Removes a charge node.
     * It is strongly recommended to call this method in
     * {@link net.minecraft.block.Block#breakBlock(World, BlockPos, IBlockState)}.
     *
     * @param world The world
     * @param pos The position
     */
    default void deregisterNode(World world, BlockPos pos) {
        ChargeToolsApi.getDimension(world).deregisterChargeNode(pos);
    }

}
