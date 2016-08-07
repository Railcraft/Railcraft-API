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
 * @see TrackKitSpec
 * @see ITrackKit
 * @see TrackKit
 */
public class TrackRegistry {

    private static final Map<String, TrackKitSpec> trackSpecsFromTag = new HashMap<String, TrackKitSpec>();
    private static final Set<String> invalidSpecTags = new HashSet<String>();
    private static final TrackKitSpec defaultSpec = new TrackKitSpec("railcraft:default", null, TrackKitDefault.class, null);

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
    public static void registerTrackSpec(TrackKitSpec trackKitSpec) {
        if (trackSpecsFromTag.put(trackKitSpec.getTrackTag(), trackKitSpec) != null)
            throw new TrackSpecConflictException("TrackTag conflict detected, please contact the author of the " + trackKitSpec.getTrackTag());
    }

    /**
     * Returns a cached copy of a TrackSpec object.
     */
    @Nonnull
    public static TrackKitSpec getTrackSpec(@Nonnull String trackTag) {
        trackTag = trackTag.toLowerCase(Locale.ENGLISH);
        TrackKitSpec spec = trackSpecsFromTag.get(trackTag);
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
    public static TrackKitSpec getDefaultTrackSpec() {
        return defaultSpec;
    }

    /**
     * Returns all Registered TrackSpecs.
     *
     * @return list of TrackSpecs
     */
    public static Map<String, TrackKitSpec> getTrackSpecTags() {
        return trackSpecsFromTag;
    }

}
