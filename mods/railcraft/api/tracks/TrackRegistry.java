/*------------------------------------------------------------------------------
 Copyright (c) CovertJaguar, 2011-2016

 This work (the API) is licensed under the "MIT" License,
 see LICENSE.md for details.
 -----------------------------------------------------------------------------*/

package mods.railcraft.api.tracks;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableSet;
import mods.railcraft.api.core.RailcraftConstantsAPI;
import mods.railcraft.api.core.RailcraftCore;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.LoaderState;
import org.apache.logging.log4j.Level;

import java.util.*;

/**
 * The TrackRegistry is part of a system that allows 3rd party addon to simply,
 * quickly, and easily define new {@link TrackType}s and {@link TrackKit}s with unique behaviors.
 *
 * To define a new TrackType, you need to create a new TrackType object.
 *
 * To define a new TrackKit, you need to create a new TrackKit object and provide a
 * {@link ITrackKitInstance} implementation.
 *
 * The TrackKit contains basic constant information about the TrackKit, while the
 * {@link ITrackKitInstance} controls how the TrackKit interacts with the world.
 *
 * Due to some stupidity in the way that Minecraft handles model registration,
 * TrackTypes and TrackKits must be registered during the PRE-INIT phase of a {@link mods.railcraft.api.core.RailcraftModule}.
 * So if you want to add new ones, you'll need to define your own RailcraftModule. Thankfully this isn't too hard.
 *
 * @author CovertJaguar <http://www.railcraft.info>
 * @see TrackKit
 * @see ITrackKitInstance
 * @see TrackKitInstance
 */
public class TrackRegistry<T extends IStringSerializable> {

    public static final TrackRegistry<TrackType> TRACK_TYPE = new TrackRegistry<>(TrackType.NBT_TAG, "railcraft_iron");
    public static final TrackRegistry<TrackKit> TRACK_KIT = new TrackRegistry<>(TrackKit.NBT_TAG, "railcraft_missing");
    private static final TrackKit missingKit;
    private static ImmutableSet<Tuple<TrackType, TrackKit>> combinations = ImmutableSet.of();

    static {
        missingKit = new TrackKit.TrackKitBuilder(new ResourceLocation(RailcraftConstantsAPI.MOD_ID, "missing"), TrackKitMissing.class).setVisible(false).setRequiresTicks(true).build();
        TRACK_KIT.registry.put(missingKit.getName(), missingKit);
    }

    private final Map<String, T> registry = new HashMap<>();
    private final BiMap<Integer, T> idMap = HashBiMap.create();
    private final Set<String> invalidTags = new HashSet<>();
    private final String nbtTag;
    private final String fallback;

    private TrackRegistry(String nbtTag, String fallback) {
        this.nbtTag = nbtTag;
        this.fallback = fallback;
    }

    private static <T> void populateIndexedLookupTable(BiMap<Integer, T> table, List<T> elements) {
        for (int i = 0; i < elements.size(); i++) {
            table.put(i, elements.get(i));
        }
    }

    public static TrackKit getMissingTrackKit() {
        return missingKit;
    }

    public static ImmutableSet<Tuple<TrackType, TrackKit>> getCombinations() {
        return combinations;
    }

    /**
     * Do not call this!
     */
    public void finalizeRegistry() {
        if (!Loader.instance().isInState(LoaderState.PREINITIALIZATION))
            throw new TrackRegistryException("Finalize called outside PRE-INIT");
        List<T> list = new ArrayList<>();
        list.addAll(registry.values());
        list.sort(Comparator.comparing(T::getName));
        populateIndexedLookupTable(idMap, list);
        if (combinations.isEmpty()) {
            ImmutableSet.Builder<Tuple<TrackType, TrackKit>> builder = ImmutableSet.builder();
            for (TrackKit trackKit : TrackRegistry.TRACK_KIT.getVariants().values()) {
                if (!trackKit.isVisible())
                    continue;
                for (TrackType trackType : TrackRegistry.TRACK_TYPE.getVariants().values()) {
                    if (trackKit.isAllowedTrackType(trackType))
                        builder.add(new Tuple<>(trackType, trackKit));
                }
            }
            combinations = builder.build();
        }
    }

    /**
     * Registers a new variant. This should be called before the Post-Init
     * Phase, during the Init or Pre-Init Phase.
     */
    public void register(T variant) {
        if (!RailcraftConstantsAPI.MOD_ID.equals(Loader.instance().activeModContainer().getModId()) || RailcraftCore.getInitStage() != RailcraftCore.InitStage.PRE_INIT)
            throw new TrackRegistryException("Track objects must be registered during PRE-INIT from a Railcraft Module class");
        if (registry.put(variant.getName(), variant) != null)
            throw new TrackRegistryException("Conflict detected, please contact the author of the " + variant.getName());
    }

    /**
     * Returns a TrackKit object.
     */
    public T get(String tag) {
        tag = tag.toLowerCase(Locale.ROOT).replaceAll("[.:]", "_");
        T variant = registry.get(tag);
        if (variant == null) {
            if (!invalidTags.contains(tag)) {
                FMLLog.log(RailcraftConstantsAPI.MOD_ID, Level.WARN, "Track Registry: Unknown variant tag(%s)", tag);
                StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
                for (int i = 1; i < stackTrace.length && i < 9; i++) {
                    FMLLog.log(Level.DEBUG, stackTrace[i].toString());
                }
                invalidTags.add(tag);
            }
            variant = getFallback();
        }
        return variant;
    }

    public T get(NBTTagCompound nbt) {
        return get(nbt.getString(nbtTag));
    }

    public T get(ItemStack stack) {
        NBTTagCompound nbt = stack.getSubCompound(RailcraftConstantsAPI.MOD_ID, false);
        if (nbt != null)
            return get(nbt);
        return getFallback();
    }

    public T get(int id) {
        return idMap.get(id);
    }

    public T getFallback() {
        return get(fallback);
    }

    public int getId(T variant) {
        if (idMap.isEmpty())
            return 0;
        return idMap.inverse().get(variant);
    }

    /**
     * Returns all registered variants.
     *
     * @return list of variants
     */
    public Map<String, T> getVariants() {
        return registry;
    }

    public static class TrackRegistryException extends RuntimeException {

        public TrackRegistryException(String msg) {
            super(msg);
        }

    }

}
