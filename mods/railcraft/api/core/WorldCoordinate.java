/*
 * ******************************************************************************
 *  Copyright 2011-2015 CovertJaguar
 *
 *  This work (the API) is licensed under the "MIT" License, see LICENSE.md for details.
 * ***************************************************************************
 */
package mods.railcraft.api.core;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3i;

import javax.annotation.Nonnull;

/**
 * This immutable class represents a point in the Minecraft world, while taking
 * into account the possibility of coordinates in different dimensions.
 *
 * @author CovertJaguar <http://www.railcraft.info>
 */
public class WorldCoordinate extends BlockPos {
    /**
     * The dimension
     */
    private final int dimension;

    /**
     * Creates a new WorldCoordinate
     *
     * @param dimension Dimension ID
     * @param x         World Coordinate
     * @param y         World Coordinate
     * @param z         World Coordinate
     */
    public WorldCoordinate(int dimension, int x, int y, int z) {
        super(x, y, z);
        this.dimension = dimension;
    }

    /**
     * Creates a new WorldCoordinate
     *
     * @param dimension Dimension ID
     * @param pos       World Coordinates
     */
    public WorldCoordinate(int dimension, Vec3i pos) {
        super(pos);
        this.dimension = dimension;
    }

    public WorldCoordinate(TileEntity tile) {
        super(tile.getPos());
        this.dimension = tile.getWorld().provider.getDimensionId();
    }

    public static WorldCoordinate readFromNBT(NBTTagCompound data, String key) {
        if (data.hasKey(key, 10)) {
            NBTTagCompound nbt = data.getCompoundTag(key);
            int dim = nbt.getInteger("dim");
            int x = nbt.getInteger("x");
            int y = nbt.getInteger("y");
            int z = nbt.getInteger("z");
            return new WorldCoordinate(dim, x, y, z);
        } else if (data.hasKey(key, 11)) {
            int[] c = data.getIntArray(key);
            return new WorldCoordinate(c[0], c[1], c[2], c[3]);
        }
        return null;
    }

    public void writeToNBT(NBTTagCompound data, String tag) {
        data.setIntArray(tag, new int[]{dimension, getX(), getY(), getZ()});
    }

    public boolean isEqual(int dim, int x, int y, int z) {
        return getX() == x && getY() == y && getZ() == z && this.dimension == dim;
    }

    public boolean isEqual(int dim, BlockPos p) {
        return getX() == p.getX() && getY() == p.getY() && getZ() == p.getZ() && this.dimension == dim;
    }

//    public int compareTo(@Nonnull WorldCoordinate o) {
//        if (dimension != o.dimension)
//            return dimension - o.dimension;
//        if (getX() != o.getX())
//            return getX() - o.getX();
//        if (getY() != o.getY())
//            return getY() - o.getY();
//        if (getZ() != o.getZ())
//            return getZ() - o.getZ();
//        return 0;
//    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final WorldCoordinate other = (WorldCoordinate) obj;
        return isEqual(other.dimension, other);
    }

    @Override
    public int hashCode() {
        int result = dimension;
        result = 31 * result + super.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "WorldCoordinate{" + "dimension=" + dimension + ", x=" + getX() + ", y=" + getY() + ", z=" + getZ() + '}';
    }

    public int getDim() {
        return dimension;
    }

    public boolean isBelowWorld() {
        return getY() < 0;
    }
}
