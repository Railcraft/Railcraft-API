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
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
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

    @Nonnull
    private TileEntity tileEntity = new TileEntity() {
    };

    private BlockRailBase getBlock() {
        return (BlockRailBase) getTile().getBlockType();
    }

    @Override
    public void setTile(TileEntity tileEntity) {
        this.tileEntity = tileEntity;
    }

    @Override
    public TileEntity getTile() {
        return tileEntity;
    }

    @Override
    public IBlockState getActualState(IBlockState state) {
        return state;
    }

    @Override
    public List<ItemStack> getDrops(int fortune) {
        List<ItemStack> drops = new ArrayList<ItemStack>();
        drops.add(getTrackSpec().getItem());
        return drops;
    }

    @Override
    public BlockRailBase.EnumRailDirection getRailDirection(IBlockState state, @Nullable EntityMinecart cart) {
        return getRailDirection(state);
    }

    protected final BlockRailBase.EnumRailDirection getRailDirection() {
        World world = theWorldAsserted();
        IBlockState state = world.getBlockState(getPos());
        return getRailDirection(state);
    }

    protected static BlockRailBase.EnumRailDirection getRailDirection(IBlockState state) {
        if (state.getBlock() instanceof BlockRailBase)
            return state.getValue(((BlockRailBase) state.getBlock()).getShapeProperty());
        return NORTH_SOUTH;
    }

    @Override
    public void onMinecartPass(EntityMinecart cart) {
    }

    @Override
    public boolean blockActivated(EntityPlayer player, EnumHand hand, @Nullable ItemStack heldItem) {
        if (this instanceof ITrackReversible) {
            if (heldItem != null && heldItem.getItem() instanceof IToolCrowbar) {
                IToolCrowbar crowbar = (IToolCrowbar) heldItem.getItem();
                if (crowbar.canWhack(player, heldItem, getPos())) {
                    ITrackReversible track = (ITrackReversible) this;
                    track.setReversed(!track.isReversed());
                    markBlockNeedsUpdate();
                    crowbar.onWhack(player, heldItem, getPos());
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void onBlockPlacedBy(IBlockState state, @Nullable EntityLivingBase placer, ItemStack stack) {
        if (placer != null && this instanceof ITrackReversible) {
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
        ((ITrackTile) getTile()).sendUpdateToClient();
    }

    public void markBlockNeedsUpdate() {
        World world = theWorldAsserted();
        IBlockState state = world.getBlockState(getTile().getPos());
        world.notifyBlockUpdate(getTile().getPos(), state, state, 3);
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
    public void onNeighborBlockChange(IBlockState state, @Nullable Block neighborBlock) {
        World world = theWorldAsserted();
        boolean valid = isRailValid(world, getPos(), state.getValue(((BlockRailBase) state.getBlock()).getShapeProperty()));
        if (!valid) {
            Block blockTrack = getBlock();
            blockTrack.dropBlockAsItem(world, getPos(), state, 0);
            world.setBlockToAir(getPos());
            return;
        }

        if (neighborBlock != null && neighborBlock.getDefaultState().canProvidePower()
                && isFlexibleRail() && TrackToolsAPI.countAdjacentTracks(world, getPos()) == 3)
            switchTrack(state, false);
        testPower(state);
    }

    private void switchTrack(IBlockState state, boolean flag) {
        World world = theWorldAsserted();
        BlockPos pos = getTile().getPos();
        BlockRailBase blockTrack = getBlock();
        blockTrack.new Rail(world, pos, state).place(world.isBlockPowered(pos), flag);
    }

    protected final void testPower(IBlockState state) {
        if (!(this instanceof ITrackPowered))
            return;
        World world = theWorldAsserted();
        ITrackPowered r = (ITrackPowered) this;
        boolean powered = world.isBlockIndirectlyGettingPowered(getPos()) > 0 || testPowerPropagation(world, getPos(), getTrackSpec(), state, r.getPowerPropagation());
        if (powered != r.isPowered()) {
            r.setPowered(powered);
            Block blockTrack = getBlock();
            world.notifyNeighborsOfStateChange(getPos(), blockTrack);
            world.notifyNeighborsOfStateChange(getPos().down(), blockTrack);
            BlockRailBase.EnumRailDirection railDirection = state.getValue(((BlockRailBase) state.getBlock()).getShapeProperty());
            if (railDirection.isAscending())
                world.notifyNeighborsOfStateChange(getPos().up(), blockTrack);
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
    public float getExplosionResistance(Explosion explosion, Entity exploder) {
        return 3.5f;
    }

    @Override
    public void writePacketData(@Nonnull DataOutputStream data) throws IOException {
    }

    @Override
    public void readPacketData(@Nonnull DataInputStream data) throws IOException {
    }

    @Nullable
    @Override
    public World theWorld() {
        return getTile().getWorld();
    }

    /**
     * Be careful where you call this function from.
     * Only call it if you have a reasonable assumption that the world can't be null,
     * otherwise the game will crash.
     */
    @Nonnull
    public World theWorldAsserted() throws NullPointerException {
        World world = theWorld();
        if (world == null) throw new NullPointerException("World was null");
        return world;
    }

    @Override
    public BlockPos getPos() {
        return getTile().getPos();
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
