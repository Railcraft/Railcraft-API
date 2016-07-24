/*------------------------------------------------------------------------------
 Copyright (c) CovertJaguar, 2011-2016

 This work (the API) is licensed under the "MIT" License,
 see LICENSE.md for details.
 -----------------------------------------------------------------------------*/

package mods.railcraft.api.tracks;

import mods.railcraft.api.core.RailcraftConstantsAPI;
import net.minecraftforge.fml.common.FMLLog;
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
 * @see TrackSpec
 * @see ITrackInstance
 * @see TrackInstanceBase
 */
public class TrackRegistry {

    private static final Map<Short, TrackSpec> trackSpecsFromID = new HashMap<Short, TrackSpec>();
    private static final Map<String, TrackSpec> trackSpecsFromTag = new HashMap<String, TrackSpec>();
    private static final Set<Short> invalidSpecIDs = new HashSet<Short>();
    private static final Set<String> invalidSpecTags = new HashSet<String>();
    private static final TrackSpec defaultSpec = new TrackSpec((short) -1, "railcraft:default", null, TrackInstanceDefault.class, null);

    public static class TrackSpecConflictException extends RuntimeException {

        public TrackSpecConflictException(String msg) {
            super(msg);
        }

    }

    private TrackRegistry() {
    }

    static {
        registerTrackSpec(defaultSpec);
    }

    /**
     * Registers a new TrackSpec. This should be called before the Post-Init
     * Phase, during the Init or Pre-Init Phase.
     */
    public static void registerTrackSpec(TrackSpec trackSpec) {
        if (trackSpecsFromID.put(trackSpec.getTrackId(), trackSpec) != null)
            throw new TrackSpecConflictException("TrackId conflict detected, please adjust your config (id:" + trackSpec.getTrackId() + ") or contact the author of the " + trackSpec.getTrackTag());
        if (trackSpecsFromTag.put(trackSpec.getTrackTag(), trackSpec) != null)
            throw new TrackSpecConflictException("TrackTag conflict detected, please contact the author of the " + trackSpec.getTrackTag());
    }

    /**
     * Returns a cached copy of a TrackSpec object.
     */
    @Nonnull
    @Deprecated
    public static TrackSpec getTrackSpec(int trackId) {
        Short id = (short) trackId;
        TrackSpec spec = trackSpecsFromID.get(id);
        if (spec == null) {
            if (!invalidSpecIDs.contains(id)) {
                FMLLog.log(RailcraftConstantsAPI.MOD_ID, Level.WARN, "Unknown Track Spec ID(%d), reverting to normal track", trackId);
                StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
                for (int i = 1; i < stackTrace.length && i < 9; i++) {
                    FMLLog.log(Level.DEBUG, stackTrace[i].toString());
                }
                invalidSpecIDs.add(id);
            }
            spec = getDefaultTrackSpec();
        }
        return spec;
    }

    /**
     * Returns a cached copy of a TrackSpec object.
     */
    @Nonnull
    public static TrackSpec getTrackSpec(@Nonnull String trackTag) {
        trackTag = trackTag.toLowerCase(Locale.ENGLISH);
        TrackSpec spec = trackSpecsFromTag.get(trackTag);
        if (spec == null) {
            if (!invalidSpecTags.contains(trackTag)) {
                FMLLog.log(RailcraftConstantsAPI.MOD_ID, Level.WARN, "Unknown Track Spec Tag(%s), reverting to normal track", trackTag);
                StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
                for (int i = 1; i < stackTrace.length && i < 9; i++) {
                    FMLLog.log(Level.DEBUG, stackTrace[i].toString());
                }
                invalidSpecTags.add(trackTag);
            }
            spec = getDefaultTrackSpec();
        }
        return spec;
    }

    @Nonnull
    public static TrackSpec getDefaultTrackSpec() {
        return defaultSpec;
    }

    /**
     * Returns all Registered TrackSpecs.
     *
     * @return list of TrackSpecs
     */
    public static Map<Short, TrackSpec> getTrackSpecIDs() {
        return trackSpecsFromID;
    }

    /**
     * Returns all Registered TrackSpecs.
     *
     * @return list of TrackSpecs
     */
    public static Map<String, TrackSpec> getTrackSpecTags() {
        return trackSpecsFromTag;
    }

}
