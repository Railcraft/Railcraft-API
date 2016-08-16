/*------------------------------------------------------------------------------
 Copyright (c) CovertJaguar, 2011-2016

 This work (the API) is licensed under the "MIT" License,
 see LICENSE.md for details.
 -----------------------------------------------------------------------------*/

package mods.railcraft.api.tracks;

import mods.railcraft.api.core.RailcraftConstantsAPI;
import net.minecraft.nbt.NBTTagCompound;
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
 * @see TrackKit
 * @see ITrackKitInstance
 * @see TrackKitInstance
 */
public class TrackRegistry {

    private static final TreeMap<String, TrackKit> trackKitsFromTag = new TreeMap<String, TrackKit>();
    private static final Set<String> invalidSpecTags = new HashSet<String>();
    private static final TrackKit missingKit = new TrackKit("railcraft:missing", null, TrackKitMissing.class);

    public static class TrackSpecConflictException extends RuntimeException {

        public TrackSpecConflictException(String msg) {
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
     * Registers a new TrackSpec. This should be called before the Post-Init
     * Phase, during the Init or Pre-Init Phase.
     */
    public static void registerTrackKit(TrackKit trackKit) {
        if (trackKitsFromTag.put(trackKit.getName(), trackKit) != null)
            throw new TrackSpecConflictException("TrackKit conflict detected, please contact the author of the " + trackKit.getName());
    }

    /**
     * Returns a cached copy of a TrackSpec object.
     */
    @Nonnull
    public static TrackKit getTrackKit(@Nonnull String kitTag) {
        kitTag = kitTag.toLowerCase(Locale.ROOT);
        TrackKit spec = trackKitsFromTag.get(kitTag);
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

    @Nonnull
    public static TrackKit getTrackKit(NBTTagCompound nbt) {
        return getTrackKit(nbt.getString(TrackKit.NBT_TAG));
    }

    public static int getTrackKitId(TrackKit trackKit) {
        String name = trackKit.getName();
        return trackKitsFromTag.containsKey(name) ? trackKitsFromTag.headMap(name).size() : -1;
    }

    @Nonnull
    public static TrackKit getMissingTrackKit() {
        return missingKit;
    }

    /**
     * Returns all Registered TrackSpecs.
     *
     * @return list of TrackSpecs
     */
    public static Map<String, TrackKit> getTrackKits() {
        return trackKitsFromTag;
    }

}
