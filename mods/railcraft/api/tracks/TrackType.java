/*------------------------------------------------------------------------------
 Copyright (c) CovertJaguar, 2011-2016

 This work (the API) is licensed under the "MIT" License,
 see LICENSE.md for details.
 -----------------------------------------------------------------------------*/

package mods.railcraft.api.tracks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.init.Blocks;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * Created by CovertJaguar on 8/10/2016 for Railcraft.
 *
 * @author CovertJaguar <http://www.railcraft.info>
 */
public class TrackType implements IStringSerializable {
    public static final String NBT_TAG = "rail";

    private final ResourceLocation registryName;
    private final ResourceLocation texture;
    private final ResourceLocation baseBlock;
    private float resistance = 3.5F;

    private int maxSupportDistance;

    public TrackType(ResourceLocation registryName, ResourceLocation baseBlock, ResourceLocation texture) {
        this.registryName = registryName;
        this.baseBlock = baseBlock;
        this.texture = texture;
    }

    public final ResourceLocation getRegistryName() {
        return registryName;
    }

    @Override
    public final String getName() {
        return getRegistryName().toString().replaceAll("[.:]", "_");
    }

    public ResourceLocation getTexture() {
        return texture;
    }

    public BlockRailBase getBaseBlock() {
        BlockRailBase block = (BlockRailBase) Block.getBlockFromName(baseBlock.toString());
        if (block == null)
            return (BlockRailBase) Blocks.RAIL;
        return block;
    }

    public final float getResistance() {
        return resistance;
    }

    public final void setResistance(float resistance) {
        this.resistance = resistance;
    }

    public int getMaxSupportDistance() {
        return maxSupportDistance;
    }

    public void setMaxSupportDistance(int maxSupportDistance) {
        this.maxSupportDistance = maxSupportDistance;
    }

    public void onMinecartPass(World world, EntityMinecart cart, BlockPos pos, @Nullable TrackKit trackKit) {
    }

    public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity) {
    }

    public float getMaxSpeed(World world, EntityMinecart cart, BlockPos pos) {
        return 0.4f;
    }

    @Override
    public String toString() {
        return "TrackType{" + getName() + "}";
    }
}
