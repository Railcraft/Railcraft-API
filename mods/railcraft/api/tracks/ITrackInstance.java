/*
 * ******************************************************************************
 *  Copyright 2011-2015 CovertJaguar
 *
 *  This work (the API) is licensed under the "MIT" License, see LICENSE.md for details.
 * ***************************************************************************
 */

package mods.railcraft.api.tracks;

import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IIcon;
import mods.railcraft.api.core.INetworkedObject;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

/**
 * This interface defines a track.
 *
 * Basically all block and tile entity functions for Tracks are delegated to an
 * ITrackInstance.
 *
 * Instead of implementing this interface directly, you should probably extend
 * TrackInstanceBase. It will simplify your life.
 *
 * All packet manipulation is handled by Railcraft's code, you just need to
 * implement the functions in INetworkedObject to pass data from the server to
 * the client.
 *
 * @author CovertJaguar
 * @see TrackInstanceBase
 */
public interface ITrackInstance extends INetworkedObject {

    TrackSpec getTrackSpec();
    
    List<ItemStack> getDrops(int fortune);

    /**
     * Return the rail's metadata (without the power bit if the rail uses one).
     * Can be used to make the cart think the rail something other than it is,
     * for example when making diamond junctions or switches.
     *
     * Valid rail metadata is defined as follows: 0x0: flat track going
     * North-South 0x1: flat track going West-East 0x2: track ascending to the
     * East 0x3: track ascending to the West 0x4: track ascending to the North
     * 0x5: track ascending to the South 0x6: WestNorth corner (connecting East
     * and South) 0x7: EastNorth corner (connecting West and South) 0x8:
     * EastSouth corner (connecting West and North) 0x9: WestSouth corner
     * (connecting East and North)
     *
     * @param cart The cart asking for the metadata, null if it is not called by
     * EntityMinecart.
     * @return The metadata.
     */
    int getBasicRailMetadata(EntityMinecart cart);

    /**
     * This function is called by any minecart that passes over this rail. It is
     * called once per update tick that the minecart is on the rail.
     *
     * @param cart The cart on the rail.
     */
    void onMinecartPass(EntityMinecart cart);

    /**
     * Return the block texture to be used.
     *
     * @return
     */
    IIcon getIcon();

    void writeToNBT(NBTTagCompound data);

    void readFromNBT(NBTTagCompound data);

    /**
     * Return true if this track requires update ticks.
     *
     * @return
     */
    boolean canUpdate();

    void update();

    boolean blockActivated(EntityPlayer player);

    void onBlockPlaced();

    void onBlockRemoved();

    void onBlockPlacedBy(EntityLivingBase entity);

    void onNeighborBlockChange(Block blockChanged);

    /**
     * Internal function that sets the Track's TileEntity so it can be
     * referenced for position information, etc...
     *
     * @param tile
     */
    void setTile(TileEntity tile);

    TileEntity getTile();

    BlockPos getPos();

    float getHardness();

    float getExplosionResistance(double srcX, double srcY, double srcZ, Entity exploder);

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
    float getRailMaxSpeed(EntityMinecart cart);

}
