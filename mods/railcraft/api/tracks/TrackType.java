/*------------------------------------------------------------------------------
 Copyright (c) CovertJaguar, 2011-2016

 This work (the API) is licensed under the "MIT" License,
 see LICENSE.md for details.
 -----------------------------------------------------------------------------*/

package mods.railcraft.api.tracks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecart;
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
    public static final String NBT_TAG = "kit";

    private final String registryName;
    private float resistance = 3.5F;
    private final ResourceLocation texture;

    public TrackType(String registryName, ResourceLocation texture) {
        this.registryName = registryName;
        this.texture = texture;
    }

    public final String getRegistryName() {
        return registryName;
    }

    @Override
    public final String getName() {
        return getRegistryName().replaceAll("[.:]", "_");
    }

    public ResourceLocation getTexture() {
        return texture;
    }

    public final float getResistance() {
        return resistance;
    }

    public final void setResistance(float resistance) {
        this.resistance = resistance;
    }

    public void onMinecartPass(World world, EntityMinecart cart, BlockPos pos, @Nullable TrackKit trackKit) {
    }

    public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity) {
    }

    public float getMaxSpeed(World world, EntityMinecart cart, BlockPos pos) {
        return 0.4f;
    }
}
