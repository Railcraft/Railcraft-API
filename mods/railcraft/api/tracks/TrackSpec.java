/*
 * ******************************************************************************
 *  Copyright 2011-2015 CovertJaguar
 *
 *  This work (the API) is licensed under the "MIT" License, see LICENSE.md for details.
 * ***************************************************************************
 */

package mods.railcraft.api.tracks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Locale;

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
 * @see ITrackInstance
 */
public final class TrackSpec {

    public static Block blockTrack;
    @Nonnull
    private final String tag;
    private final short trackId;
    private final List<String> tooltip;
    private final ModelResourceLocation iconProvider;
    @Nonnull
    private final Class<? extends ITrackInstance> instanceClass;

    /**
     * Defines a new track spec.
     *
     * @param trackId       A unique identifier for the track type. 0-512 are reserved
     *                      for Railcraft. Capped at Short.MAX_VALUE
     * @param tag           A unique internal string identifier (ex.
     *                      "railcraft:track.speed.transition")
     * @param iconProvider  The provider for Track item icons
     * @param instanceClass The ITrackInstance class that corresponds to this
     *                      TrackSpec
     */
    public TrackSpec(short trackId, @Nonnull String tag, @Nullable ModelResourceLocation iconProvider, @Nonnull Class<? extends ITrackInstance> instanceClass) {
        this(trackId, tag, iconProvider, instanceClass, null);
    }

    /**
     * Defines a new track spec.
     *
     * @param trackId       A unique identifier for the track type. 0-512 are reserved
     *                      for Railcraft. Capped at Short.MAX_VALUE
     * @param tag           A unique internal string identifier (ex.
     *                      "railcraft:track.speed.transition")
     * @param iconProvider  The provider for Track item icons
     * @param instanceClass The ITrackInstance class that corresponds to this
     *                      TrackSpec
     * @param tooltip       The tool tip for the Track Item
     */
    public TrackSpec(short trackId, @Nonnull String tag, @Nullable ModelResourceLocation iconProvider, @Nonnull Class<? extends ITrackInstance> instanceClass, @Nullable List<String> tooltip) {
        this.trackId = trackId;
        this.tag = tag.toLowerCase(Locale.ENGLISH);
        this.iconProvider = iconProvider;
        this.instanceClass = instanceClass;
        this.tooltip = tooltip;
    }

    @Nonnull
    public String getTrackTag() {
        return tag;
    }

    public short getTrackId() {
        return trackId;
    }

    /**
     * This function will only work after the Init Phase.
     *
     * @return an ItemStack that can be used to place the track.
     */
    @Nullable
    public ItemStack getItem() {
        return getItem(1);
    }

    /**
     * This function will only work after the Init Phase.
     *
     * @return an ItemStack that can be used to place the track.
     */
    @Nullable
    public ItemStack getItem(int qty) {
        if (blockTrack != null) {
            ItemStack stack = new ItemStack(blockTrack, qty, getTrackTag().hashCode() % (Short.MAX_VALUE - 1));
            NBTTagCompound nbt = new NBTTagCompound();
            nbt.setString("track", tag);
            stack.setTagCompound(nbt);
            return stack;
        }
        return null;
    }

    @Nonnull
    public ITrackInstance createInstanceFromSpec() {
        try {
            ITrackInstance trackInstance = instanceClass.newInstance();
            if (trackInstance == null) throw new NullPointerException("No track constructor found");
            return trackInstance;
        } catch (Exception ex) {
            throw new RuntimeException("Improper Track Instance Constructor", ex);
        }
    }

    public ModelResourceLocation getItemModel() {
        return iconProvider;
    }

    @Nullable
    public List<String> getItemToolTip() {
        return tooltip;
    }

    @Override
    public String toString() {
        return "Track -> " + getTrackTag();
    }

}
