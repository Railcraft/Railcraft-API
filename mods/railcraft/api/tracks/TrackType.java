/*------------------------------------------------------------------------------
 Copyright (c) CovertJaguar, 2011-2016

 This work (the API) is licensed under the "MIT" License,
 see LICENSE.md for details.
 -----------------------------------------------------------------------------*/

package mods.railcraft.api.tracks;

import mods.railcraft.api.core.ILocalizedObject;
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
public class TrackType implements IStringSerializable, ILocalizedObject {
    public static final String NBT_TAG = "rail";

    private final ResourceLocation registryName;
    private final ResourceLocation baseBlock;
    private final float resistance;
    private final boolean highSpeed;
    private final boolean electric;
    private final int maxSupportDistance;
    private final EventHandler eventHandler;

    public static final class Builder {
        private final ResourceLocation registryName;
        private final ResourceLocation baseBlock;
        private float resistance = 3.5F;
        private boolean highSpeed;
        private boolean electric;
        private int maxSupportDistance;
        private EventHandler eventHandler;

        public Builder(ResourceLocation registryName, ResourceLocation baseBlock) {
            this.registryName = registryName;
            this.baseBlock = baseBlock;
        }

        public TrackType build() {
            if (eventHandler == null)
                eventHandler = new EventHandler();
            return new TrackType(registryName, baseBlock, resistance, highSpeed, electric, maxSupportDistance, eventHandler);
        }

        public final Builder setResistance(float resistance) {
            this.resistance = resistance;
            return this;
        }

        public final Builder setHighSpeed(boolean highSpeed) {
            this.highSpeed = highSpeed;
            return this;
        }

        public final Builder setElectric(boolean electric) {
            this.electric = electric;
            return this;
        }

        public final Builder setMaxSupportDistance(int maxSupportDistance) {
            this.maxSupportDistance = maxSupportDistance;
            return this;
        }

        public final Builder setEventHandler(EventHandler eventHandler) {
            this.eventHandler = eventHandler;
            return this;
        }
    }

    public static class EventHandler {
        public void onMinecartPass(World worldIn, EntityMinecart cart, BlockPos pos, @Nullable TrackKit trackKit) {
        }

        public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity) {
        }

        public float getMaxSpeed(World world, EntityMinecart cart, BlockPos pos) {
            return 0.4f;
        }
    }

    public TrackType(ResourceLocation registryName, ResourceLocation baseBlock, float resistance, boolean highSpeed, boolean electric, int maxSupportDistance, EventHandler eventHandler) {
        this.registryName = registryName;
        this.baseBlock = baseBlock;
        this.resistance = resistance;
        this.highSpeed = highSpeed;
        this.electric = electric;
        this.maxSupportDistance = maxSupportDistance;
        this.eventHandler = eventHandler;
    }

    public boolean isHighSpeed() {
        return highSpeed;
    }

    public boolean isElectric() {
        return electric;
    }

    public final ResourceLocation getRegistryName() {
        return registryName;
    }

    @Override
    public final String getName() {
        return getRegistryName().toString().replaceAll("[.:]", "_");
    }

    @Override
    public String getLocalizationTag() {
        return "track_type.railcraft." + getName() + ".name";
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

    public int getMaxSupportDistance() {
        return maxSupportDistance;
    }

    public EventHandler getEventHandler() {
        return eventHandler;
    }

    @Override
    public String toString() {
        return "TrackType{" + getName() + "}";
    }
}
