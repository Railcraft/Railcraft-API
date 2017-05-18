/*------------------------------------------------------------------------------
 Copyright (c) CovertJaguar, 2011-2016

 This work (the API) is licensed under the "MIT" License,
 see LICENSE.md for details.
 -----------------------------------------------------------------------------*/

package mods.railcraft.api.tracks;

import mods.railcraft.api.core.ILocalizedObject;
import mods.railcraft.api.core.IRailcraftRecipeIngredient;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.init.Blocks;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;

import javax.annotation.Nullable;

/**
 * Created by CovertJaguar on 8/10/2016 for Railcraft.
 *
 * @author CovertJaguar <http://www.railcraft.info>
 */
public class TrackType extends IForgeRegistryEntry.Impl<TrackType> implements IStringSerializable, ILocalizedObject {
    public static final String NBT_TAG = "rail";

    private final ResourceLocation baseBlock;
    private final IRailcraftRecipeIngredient rail;

    private final IRailcraftRecipeIngredient railbed;
    private final float resistance;
    private final boolean highSpeed;
    private final boolean electric;
    private final int maxSupportDistance;
    private final EventHandler eventHandler;

    public TrackType(ResourceLocation registryName, ResourceLocation baseBlock,
                     IRailcraftRecipeIngredient rail, IRailcraftRecipeIngredient railbed,
                     float resistance, boolean highSpeed, boolean electric, int maxSupportDistance,
                     EventHandler eventHandler) {
        setRegistryName(registryName);
        this.baseBlock = baseBlock;
        this.rail = rail;
        this.railbed = railbed;
        this.resistance = resistance;
        this.highSpeed = highSpeed;
        this.electric = electric;
        this.maxSupportDistance = maxSupportDistance;
        this.eventHandler = eventHandler;
    }

    public IRailcraftRecipeIngredient getRail() {
        return rail;
    }

    public IRailcraftRecipeIngredient getRailbed() {
        return railbed;
    }

    public boolean isHighSpeed() {
        return highSpeed;
    }

    public boolean isElectric() {
        return electric;
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

    public static final class Builder {
        private final ResourceLocation registryName;
        private final ResourceLocation baseBlock;
        private final IRailcraftRecipeIngredient rail;
        private final IRailcraftRecipeIngredient railbed;
        private float resistance = 3.5F;
        private boolean highSpeed;
        private boolean electric;
        private int maxSupportDistance;
        private EventHandler eventHandler;

        public Builder(ResourceLocation registryName, ResourceLocation baseBlock, IRailcraftRecipeIngredient rail, IRailcraftRecipeIngredient railbed) {
            this.registryName = registryName;
            this.baseBlock = baseBlock;
            this.rail = rail;
            this.railbed = railbed;
        }

        public TrackType build() {
            if (eventHandler == null)
                eventHandler = new EventHandler();
            return new TrackType(registryName, baseBlock, rail, railbed,
                    resistance, highSpeed, electric, maxSupportDistance, eventHandler);
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

        @Nullable
        public BlockRailBase.EnumRailDirection getRailDirectionOverride(IBlockAccess world, BlockPos pos, IBlockState state, @Nullable EntityMinecart cart) {
            return null;
        }

        public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity) {
        }

        public float getMaxSpeed(World world, @Nullable EntityMinecart cart, BlockPos pos) {
            return 0.4f;
        }
    }
}
