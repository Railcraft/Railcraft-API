/*
 * ******************************************************************************
 *  Copyright 2011-2015 CovertJaguar
 *
 *  This work (the API) is licensed under the "MIT" License, see LICENSE.md for details.
 * ***************************************************************************
 */

package mods.railcraft.api.tracks;

import mods.railcraft.api.core.items.IToolCrowbar;
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
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static net.minecraft.block.BlockRailBase.EnumRailDirection.*;

/**
 * All ITrackInstances should extend this class. It contains a number of default
 * functions and standard behavior for Tracks that should greatly simplify
 * implementing new Tracks when using this API.
 *
 * @author CovertJaguar <http://www.railcraft.info>
 * @see ITrackInstance
 * @see TrackRegistry
 * @see TrackSpec
 */
public abstract class TrackInstanceBase implements ITrackInstance {

    public TileEntity tileEntity;

    private BlockRailBase getBlock() {
        return (BlockRailBase) tileEntity.getBlockType();
    }

    @Override
    public void setTile(TileEntity tile) {
        tileEntity = tile;
    }

    @Override
    public TileEntity getTile() {
        return tileEntity;
    }

    @Override
    public List<ItemStack> getDrops(int fortune) {
        List<ItemStack> drops = new ArrayList<ItemStack>();
        drops.add(getTrackSpec().getItem());
        return drops;
    }

    @Override
    public BlockRailBase.EnumRailDirection getRailDirection(IBlockState state, EntityMinecart cart) {
        return state.getValue(((BlockRailBase) state.getBlock()).getShapeProperty());
    }

    @Override
    public void onMinecartPass(EntityMinecart cart) {
    }

    @Override
    public boolean blockActivated(EntityPlayer player) {
        if (this instanceof ITrackReversible) {
            ItemStack current = player.getCurrentEquippedItem();
            if (current != null && current.getItem() instanceof IToolCrowbar) {
                IToolCrowbar crowbar = (IToolCrowbar) current.getItem();
                if (crowbar.canWhack(player, current, getPos())) {
                    ITrackReversible track = (ITrackReversible) this;
                    track.setReversed(!track.isReversed());
                    markBlockNeedsUpdate();
                    crowbar.onWhack(player, current, getPos());
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void onBlockPlacedBy(IBlockState state, EntityLivingBase placer, ItemStack stack) {
        if (placer == null)
            return;
        if (this instanceof ITrackReversible) {
            int dir = MathHelper.floor_double((double) ((placer.rotationYaw * 4F) / 360F) + 0.5D) & 3;
            ((ITrackReversible) this).setReversed(dir == 0 || dir == 1);
        }
        switchTrack(state, true);
        testPower(state);
        markBlockNeedsUpdate();
    }

    @Override
    public void onBlockRemoved() {
    }

    public void sendUpdateToClient() {
        ((ITrackTile) tileEntity).sendUpdateToClient();
    }

    public void markBlockNeedsUpdate() {
        getWorld().markBlockForUpdate(tileEntity.getPos());
    }

    @SuppressWarnings("WeakerAccess")
    protected boolean isRailValid(World world, BlockPos pos, BlockRailBase.EnumRailDirection dir) {
        boolean valid = true;
        if (!world.isSideSolid(pos.down(), EnumFacing.UP))
            valid = false;
        if (dir == ASCENDING_EAST && !world.isSideSolid(pos.east(), EnumFacing.UP))
            valid = false;
        else if (dir == ASCENDING_WEST && !world.isSideSolid(pos.west(), EnumFacing.UP))
            valid = false;
        else if (dir == ASCENDING_NORTH && !world.isSideSolid(pos.north(), EnumFacing.UP))
            valid = false;
        else if (dir == ASCENDING_SOUTH && !world.isSideSolid(pos.south(), EnumFacing.UP))
            valid = false;
        return valid;
    }

    @Override
    public void onNeighborBlockChange(IBlockState state, Block neighborBlock) {
        boolean valid = isRailValid(getWorld(), getPos(), state.getValue(((BlockRailBase) state.getBlock()).getShapeProperty()));
        if (!valid) {
            Block blockTrack = getBlock();
            blockTrack.dropBlockAsItem(getWorld(), getPos(), state, 0);
            getWorld().setBlockToAir(getPos());
            return;
        }

        if (neighborBlock != null && neighborBlock.canProvidePower()
                && isFlexibleRail() && RailTools.countAdjacentTracks(getWorld(), getPos()) == 3)
            switchTrack(state, false);
        testPower(state);
    }

    private void switchTrack(IBlockState state, boolean flag) {
        BlockPos pos = tileEntity.getPos();
        BlockRailBase blockTrack = getBlock();
        blockTrack.new Rail(getWorld(), pos, state).func_180364_a(getWorld().isBlockPowered(pos), flag);
    }

    protected final void testPower(IBlockState state) {
        if (!(this instanceof ITrackPowered))
            return;
        ITrackPowered r = (ITrackPowered) this;
        boolean powered = getWorld().isBlockIndirectlyGettingPowered(getPos()) > 0 || testPowerPropagation(getWorld(), getPos(), getTrackSpec(), state, r.getPowerPropagation());
        if (powered != r.isPowered()) {
            r.setPowered(powered);
            Block blockTrack = getBlock();
            getWorld().notifyNeighborsOfStateChange(getPos(), blockTrack);
            getWorld().notifyNeighborsOfStateChange(getPos().down(), blockTrack);
            BlockRailBase.EnumRailDirection railDirection = state.getValue(((BlockRailBase) state.getBlock()).getShapeProperty());
            if (railDirection.isAscending())
                getWorld().notifyNeighborsOfStateChange(getPos().up(), blockTrack);
            sendUpdateToClient();
            // System.out.println("Setting power [" + i + ", " + j + ", " + k + "]");
        }
    }

    private boolean testPowerPropagation(World world, BlockPos pos, TrackSpec baseSpec, IBlockState state, int maxDist) {
        return isConnectedRailPowered(world, pos, baseSpec, state, true, 0, maxDist) || isConnectedRailPowered(world, pos, baseSpec, state, false, 0, maxDist);
    }

    private boolean isConnectedRailPowered(World world, BlockPos pos, TrackSpec baseSpec, IBlockState state, boolean dir, int dist, int maxDist) {
        if (dist >= maxDist)
            return false;
        boolean powered = true;
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        BlockRailBase.EnumRailDirection railDirection = state.getValue(getBlock().getShapeProperty());
        switch (railDirection) {
            case NORTH_SOUTH: // '\0'
                if (dir)
                    z++;
                else
                    z--;
                break;

            case EAST_WEST: // '\001'
                if (dir)
                    x--;
                else
                    x++;
                break;

            case ASCENDING_EAST: // '\002'
                if (dir)
                    x--;
                else {
                    x++;
                    y++;
                    powered = false;
                }
                railDirection = EAST_WEST;
                break;

            case ASCENDING_WEST: // '\003'
                if (dir) {
                    x--;
                    y++;
                    powered = false;
                } else
                    x++;
                railDirection = EAST_WEST;
                break;

            case ASCENDING_NORTH: // '\004'
                if (dir)
                    z++;
                else {
                    z--;
                    y++;
                    powered = false;
                }
                railDirection = NORTH_SOUTH;
                break;

            case ASCENDING_SOUTH: // '\005'
                if (dir) {
                    z++;
                    y++;
                    powered = false;
                } else
                    z--;
                railDirection = NORTH_SOUTH;
                break;
        }
        pos = new BlockPos(x, y, z);
        return testPowered(world, pos, baseSpec, dir, dist, maxDist, railDirection) || (powered && testPowered(world, pos.down(), baseSpec, dir, dist, maxDist, railDirection));
    }

    private boolean testPowered(World world, BlockPos nextPos, TrackSpec baseSpec, boolean dir, int dist, int maxDist, BlockRailBase.EnumRailDirection prevOrientation) {
        // System.out.println("Testing Power at <" + nextPos + ">");
        IBlockState nextBlockState = world.getBlockState(nextPos);
        if (nextBlockState.getBlock() == getBlock()) {
            BlockRailBase.EnumRailDirection nextOrientation = nextBlockState.getValue(((BlockRailBase) nextBlockState.getBlock()).getShapeProperty());
            TileEntity nextTile = world.getTileEntity(nextPos);
            if (nextTile instanceof ITrackTile) {
                ITrackInstance nextTrack = ((ITrackTile) nextTile).getTrackInstance();
                if (!(nextTrack instanceof ITrackPowered) || nextTrack.getTrackSpec() != baseSpec || !((ITrackPowered) this).canPropagatePowerTo(nextTrack))
                    return false;
                if (prevOrientation == EAST_WEST && (nextOrientation == NORTH_SOUTH || nextOrientation == ASCENDING_NORTH || nextOrientation == ASCENDING_SOUTH))
                    return false;
                if (prevOrientation == NORTH_SOUTH && (nextOrientation == EAST_WEST || nextOrientation == ASCENDING_EAST || nextOrientation == ASCENDING_WEST))
                    return false;
                if (((ITrackPowered) nextTrack).isPowered())
                    return world.isBlockPowered(nextPos) || world.isBlockPowered(nextPos.up()) || isConnectedRailPowered(world, nextPos, baseSpec, nextBlockState, dir, dist + 1, maxDist);
            }
        }
        return false;
    }

    @Override
    public void writeToNBT(NBTTagCompound data) {
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
    }

    @Override
    public boolean canUpdate() {
        return false;
    }

    @Override
    public void update() {
    }

    @Override
    public float getHardness() {
        return 1.05F;
    }

    @Override
    public float getExplosionResistance(double srcX, double srcY, double srcZ, Entity exploder) {
        return 3.5f;
    }

    @Override
    public void writePacketData(DataOutputStream data) throws IOException {
    }

    @Override
    public void readPacketData(DataInputStream data) throws IOException {
    }

    @Override
    public World getWorld() {
        return tileEntity.getWorld();
    }

    @Override
    public BlockPos getPos() {
        return tileEntity.getPos();
    }

    /**
     * Return true if the rail can make corners. Used by placement logic.
     *
     * @return true if the rail can make corners.
     */
    @Override
    public boolean isFlexibleRail() {
        return false;
    }

    /**
     * Returns true if the rail can make up and down slopes. Used by placement
     * logic.
     *
     * @return true if the rail can make slopes.
     */
    @Override
    public boolean canMakeSlopes() {
        return true;
    }

    /**
     * Returns the max speed of the rail.
     *
     * @param cart The cart on the rail, may be null.
     * @return The max speed of the current rail.
     */
    @Override
    public float getRailMaxSpeed(EntityMinecart cart) {
        return 0.4f;
    }

}
