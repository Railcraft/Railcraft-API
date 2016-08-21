/*------------------------------------------------------------------------------
 Copyright (c) CovertJaguar, 2011-2016

 This work (the API) is licensed under the "MIT" License,
 see LICENSE.md for details.
 -----------------------------------------------------------------------------*/

package mods.railcraft.api.tracks;

import mods.railcraft.api.core.RailcraftConstantsAPI;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.LoaderState;
import org.apache.logging.log4j.Level;

import javax.annotation.Nonnull;
import java.util.*;

/**
 * The TrackRegistry is part of a system that allows 3rd party addon to simply,
 * quickly, and easily define new Tracks with unique behaviors without requiring
 * that any additional block ids be used.
 *
 * All the tracks in RailcraftProxy are implemented using this system 100%
 * (except for Gated Tracks and Switch Tracks which have some custom render
 * code).
 *
 * To define a new track, you need to define a TrackSpec and create a
 * ITrackInstance.
 *
 * The TrackSpec contains basic constant information about the Track, while the
 * TrackInstance controls how an individual Track block interact with the world.
 *
 * @author CovertJaguar <http://www.railcraft.info>
 * @see TrackKit
 * @see ITrackKitInstance
 * @see TrackKitInstance
 */
public class TrackRegistry {

    private static final TreeMap<String, TrackKit> trackKits = new TreeMap<String, TrackKit>();
    private static final TreeMap<String, TrackType> trackTypes = new TreeMap<String, TrackType>();
    private static final Set<String> invalidSpecTags = new HashSet<String>();
    private static final TrackKit missingKit = new TrackKit("railcraft:missing", null, TrackKitMissing.class);

    public static class TrackRegistryException extends RuntimeException {

        public TrackRegistryException(String msg) {
            super(msg);
        }

    }

    private TrackRegistry() {
    }

    static {
        missingKit.setVisible(false);
        registerTrackKit(missingKit);
    }

    /**
     * Registers a new TrackKit. This should be called before the Post-Init
     * Phase, during the Init or Pre-Init Phase.
     */
    public static void registerTrackKit(TrackKit trackKit) {
        if (Loader.instance().isInState(LoaderState.POSTINITIALIZATION))
            throw new TrackRegistryException("Attempted to register a TrackKit during Post");
        if (trackKits.put(trackKit.getName(), trackKit) != null)
            throw new TrackRegistryException("TrackKit conflict detected, please contact the author of the " + trackKit.getName());
    }

    /**
     * Registers a new TrackType. This should be called before the Post-Init
     * Phase, during the Init or Pre-Init Phase.
     */
    public static void registerTrackKit(TrackType trackType) {
        if (Loader.instance().isInState(LoaderState.POSTINITIALIZATION))
            throw new TrackRegistryException("Attempted to register a TrackType during Post");
        if (trackTypes.put(trackType.getName(), trackType) != null)
            throw new TrackRegistryException("TrackType conflict detected, please contact the author of the " + trackType.getName());
    }

    /**
     * Returns a TrackKit object.
     */
    @Nonnull
    public static TrackKit getTrackKit(@Nonnull String kitTag) {
        kitTag = kitTag.toLowerCase(Locale.ROOT);
        TrackKit spec = trackKits.get(kitTag);
        if (spec == null) {
            if (!invalidSpecTags.contains(kitTag)) {
                FMLLog.log(RailcraftConstantsAPI.MOD_ID, Level.WARN, "Unknown TrackKit Tag(%s), reverting to normal track", kitTag);
                StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
                for (int i = 1; i < stackTrace.length && i < 9; i++) {
                    FMLLog.log(Level.DEBUG, stackTrace[i].toString());
                }
                invalidSpecTags.add(kitTag);
            }
            spec = getMissingTrackKit();
        }
        return spec;
    }

    /**
     * Returns a TrackType object.
     */
    @Nonnull
    public static TrackType getTrackType(@Nonnull String trackTag) {
        trackTag = trackTag.toLowerCase(Locale.ROOT);
        TrackType spec = trackTypes.get(trackTag);
        if (spec == null) {
            if (!invalidSpecTags.contains(trackTag)) {
                FMLLog.log(RailcraftConstantsAPI.MOD_ID, Level.WARN, "Unknown TrackType Tag(%s), reverting to normal track", trackTag);
                StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
                for (int i = 1; i < stackTrace.length && i < 9; i++) {
                    FMLLog.log(Level.DEBUG, stackTrace[i].toString());
                }
                invalidSpecTags.add(trackTag);
            }
            spec = getTrackType("railcraft:iron");
        }
        return spec;
    }

    @Nonnull
    public static TrackKit getTrackKit(NBTTagCompound nbt) {
        return getTrackKit(nbt.getString(TrackKit.NBT_TAG));
    }

    public static int getTrackKitId(TrackKit trackKit) {
        String name = trackKit.getName();
        return trackKits.containsKey(name) ? trackKits.headMap(name).size() : -1;
    }

    @Nonnull
    public static TrackKit getMissingTrackKit() {
        return missingKit;
    }

    /**
     * Returns all Registered TrackKits.
     *
     * @return list of TrackKits
     */
    public static Map<String, TrackKit> getTrackKits() {
        return trackKits;
    }

    /**
     * Returns all Registered TrackTypes.
     *
     * @return list of TrackTypes
     */
    public static Map<String, TrackType> getTrackTypes() {
        return trackTypes;
    }

}
