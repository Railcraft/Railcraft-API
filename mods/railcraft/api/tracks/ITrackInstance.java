/*------------------------------------------------------------------------------
 Copyright (c) CovertJaguar, 2011-2016

 This work (the API) is licensed under the "MIT" License,
 see LICENSE.md for details.
 -----------------------------------------------------------------------------*/

package mods.railcraft.api.tracks;

import mcp.MethodsReturnNonnullByDefault;
import mods.railcraft.api.core.INetworkedObject;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.List;

/**
 * This interface defines a track.
 *
 * Basically all block and tile entity functions for Tracks are delegated to an
 * ITrackInstance.
 *
 * Instead of implementing this interface directly, you should probably extend
 * TrackInstanceBase. It will simplify your life.
 *
 * You must have a constructor that accepts a single TileEntity object.
 *
 * All packet manipulation is handled by Railcraft's code, you just need to
 * implement the functions in INetworkedObject to pass data from the server to
 * the client.
 *
 * @author CovertJaguar
 * @see TrackInstanceBase
 */
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public interface ITrackInstance extends INetworkedObject<DataInputStream, DataOutputStream> {

    TileEntity getTile();

    void setTile(TileEntity tileEntity);

    TrackSpec getTrackSpec();

    /**
     * Use this to add properties to the state for rendering information.
     */
    IBlockState getActualState(IBlockState state);

    List<ItemStack> getDrops(int fortune);

    /**
     * Return the rail's shape.
     * Can be used to make the cart think the rail something other than it is,
     * for example when making diamond junctions or switches.
     *
     * @param cart The cart asking for the metadata, null if it is not called by
     *             EntityMinecart.
     * @return The metadata.
     */
    BlockRailBase.EnumRailDirection getRailDirection(IBlockState state, @Nullable EntityMinecart cart);

    /**
     * This function is called by any minecart that passes over this rail. It is
     * called once per update tick that the minecart is on the rail.
     *
     * @param cart The cart on the rail.
     */
    void onMinecartPass(EntityMinecart cart);

    void writeToNBT(NBTTagCompound data);

    void readFromNBT(NBTTagCompound data);

    /**
     * Return true if this track requires update ticks.
     */
    boolean canUpdate();

    void update();

    boolean blockActivated(EntityPlayer player, EnumHand hand, @Nullable ItemStack heldItem);

    void onBlockRemoved();

    void onBlockPlacedBy(IBlockState state, @Nullable EntityLivingBase placer, ItemStack stack);

    void onNeighborBlockChange(IBlockState state, @Nullable Block neighborBlock);

    BlockPos getPos();

    float getHardness();

    float getExplosionResistance(Explosion explosion, Entity exploder);

    /**
     * Return true if the rail can make corners. Used by placement logic.
     *
     * @return true if the rail can make corners.
     */
    boolean isFlexibleRail();

    /**
     * Returns true if the rail can make up and down slopes. Used by placement
     * logic.
     *
     * @return true if the rail can make slopes.
     */
    boolean canMakeSlopes();

    /**
     * Returns the max speed of the rail.
     *
     * @param cart The cart on the rail, may be null.
     * @return The max speed of the current rail.
     */
    float getRailMaxSpeed(World world, EntityMinecart cart, BlockPos pos);

}
