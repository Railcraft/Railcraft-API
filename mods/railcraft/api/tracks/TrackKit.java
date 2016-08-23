/*------------------------------------------------------------------------------
 Copyright (c) CovertJaguar, 2011-2016

 This work (the API) is licensed under the "MIT" License,
 see LICENSE.md for details.
 -----------------------------------------------------------------------------*/

package mods.railcraft.api.tracks;

import mods.railcraft.api.core.IVariantEnum;
import mods.railcraft.api.core.RailcraftConstantsAPI;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Locale;
import java.util.function.Predicate;

/**
 * Each type of Track has a single instance of TrackSpec that corresponds with
 * it. Each Track block in the world has a ITrackInstance that corresponds with
 * it.
 *
 * Take note of the difference (similar to block classes and tile entities
 * classes).
 *
 * TrackSpecs must be registered with the TrackRegistry in either the Pre-Init
 * or Init Phases.
 *
 * Track ItemStacks can be acquired from the TrackSpec, but you are required to
 * register a proper display name yourself (during Post-Init).
 *
 * @author CovertJaguar <http://www.railcraft.info>
 * @see TrackRegistry
 * @see ITrackKitInstance
 */
public final class TrackKit implements IVariantEnum {
    public static final String NBT_TAG = "kit";
    public static Block blockTrackOutfitted;
    public static Item itemKit;
    @Nonnull
    private final ResourceLocation registryName;
    @Nonnull
    private final Class<? extends ITrackKitInstance> instanceClass;
    private Predicate<TrackType> trackTypeFilter = (t) -> true;
    private boolean allowedOnSlopes = true;
    private boolean requiresTicks = false;
    private boolean visible = true;
    private int maxSupportDistance;

    /**
     * Defines a new track kit spec.
     *
     * @param registryName  A unique internal string identifier (ex.
     *                      "railcraft:speed.transition")
     * @param instanceClass The ITrackInstance class that corresponds to this
     *                      TrackSpec
     */
    public TrackKit(@Nonnull String registryName, @Nonnull Class<? extends ITrackKitInstance> instanceClass) {
        this.registryName = new ResourceLocation(registryName.toLowerCase(Locale.ROOT));
        this.instanceClass = instanceClass;
    }

    @Override
    @Nonnull
    public String getName() {
        return getRegistryName().toString().replaceAll("[.:]", "_");
    }

    public ResourceLocation getRegistryName() {
        return registryName;
    }

    @Override
    public String getResourcePathSuffix() {
        return IVariantEnum.super.getResourcePathSuffix().replace(":", ".");
    }

    /**
     * This function will only work after the Init Phase.
     *
     * @return an ItemStack that can be used to place the track.
     */
    @Nullable
    public ItemStack getTrackKitItem() {
        return getTrackKitItem(1);
    }

    /**
     * This function will only work after the Init Phase.
     *
     * @return an ItemStack that can be used to place the track.
     */
    @Nullable
    public ItemStack getTrackKitItem(int qty) {
        if (itemKit != null) {
            ItemStack stack = new ItemStack(itemKit, qty, ordinal());
            NBTTagCompound nbt = stack.getSubCompound(RailcraftConstantsAPI.MOD_ID, true);
            nbt.setString(NBT_TAG, getName());
            return stack;
        }
        return null;
    }

    /**
     * This function will only work after the Init Phase.
     *
     * @return an ItemStack that can be used to place the track.
     */
    @Nullable
    public ItemStack getOutfittedTrack(TrackType trackType) {
        return getOutfittedTrack(trackType, 1);
    }

    /**
     * This function will only work after the Init Phase.
     *
     * @return an ItemStack that can be used to place the track.
     */
    @Nullable
    public ItemStack getOutfittedTrack(TrackType trackType, int qty) {
        if (blockTrackOutfitted != null) {
            ItemStack stack = new ItemStack(blockTrackOutfitted, qty);
            NBTTagCompound nbt = stack.getSubCompound(RailcraftConstantsAPI.MOD_ID, true);
            nbt.setString(TrackType.NBT_TAG, trackType.getName());
            nbt.setString(NBT_TAG, getName());
            return stack;
        }
        return null;
    }

    @Nonnull
    public ITrackKitInstance createInstance() {
        try {
            ITrackKitInstance trackInstance = instanceClass.newInstance();
            if (trackInstance == null) throw new NullPointerException("No track constructor found");
            return trackInstance;
        } catch (Exception ex) {
            throw new RuntimeException("Improper Track Instance Constructor", ex);
        }
    }

    public boolean isAllowedOnSlopes() {
        return allowedOnSlopes;
    }

    public void setAllowedOnSlopes(boolean allowedOnSlopes) {
        this.allowedOnSlopes = allowedOnSlopes;
    }

    public int getMaxSupportDistance() {
        return maxSupportDistance;
    }

    public void setMaxSupportDistance(int maxSupportDistance) {
        this.maxSupportDistance = maxSupportDistance;
    }

    public boolean requiresTicks() {
        return requiresTicks;
    }

    public void setRequiresTicks(boolean requiresTicks) {
        this.requiresTicks = requiresTicks;
    }

    public void setTrackTypeFilter(Predicate<TrackType> filter) {
        this.trackTypeFilter = filter;
    }

    public boolean isAllowedTrackType(TrackType trackType) {
        return trackTypeFilter.test(trackType);
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    @Override
    public int ordinal() {
        return TrackRegistry.TRACK_KIT.getId(this);
    }

    @Override
    public String toString() {
        return "TrackKit{" + getName() + "}";
    }
}