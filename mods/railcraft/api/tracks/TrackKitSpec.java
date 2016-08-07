/*------------------------------------------------------------------------------
 Copyright (c) CovertJaguar, 2011-2016

 This work (the API) is licensed under the "MIT" License,
 see LICENSE.md for details.
 -----------------------------------------------------------------------------*/

package mods.railcraft.api.tracks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;

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
 * @see ITrackKit
 */
public final class TrackKitSpec {

    public static Block blockTrack;
    @Nonnull
    private final String tag;
    private final Function<ItemStack, List<String>> tooltipProvider;
    private final ModelResourceLocation iconProvider;
    @Nonnull
    private final Class<? extends ITrackKit> instanceClass;

    /**
     * Defines a new track kit spec.
     *
     * @param tag             A unique internal string identifier (ex.
     *                        "railcraft:track.speed.transition")
     * @param iconProvider    The provider for Track item icons
     * @param instanceClass   The ITrackInstance class that corresponds to this
     *                        TrackSpec
     * @param tooltipProvider The tool tip for the Track Item
     */
    public TrackKitSpec(@Nonnull String tag, @Nullable ModelResourceLocation iconProvider, @Nonnull Class<? extends ITrackKit> instanceClass, @Nullable Function<ItemStack, List<String>> tooltipProvider) {
        this.tag = tag.toLowerCase(Locale.ENGLISH);
        this.iconProvider = iconProvider;
        this.instanceClass = instanceClass;
        this.tooltipProvider = tooltipProvider;
    }

    @Nonnull
    public String getTrackTag() {
        return tag;
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
    public ITrackKit createInstanceFromSpec() {
        try {
            ITrackKit trackInstance = instanceClass.newInstance();
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
    public Function<ItemStack, List<String>> getToolTipProvider() {
        return tooltipProvider;
    }

    public boolean canMakeSlopes() {
        return false;
    }

    @Override
    public String toString() {
        return "Track -> " + getTrackTag();
    }

}
