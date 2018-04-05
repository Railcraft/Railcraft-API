package mods.railcraft.api.tracks;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Created by CovertJaguar on 3/19/2016 for Railcraft.
 *
 * @author CovertJaguar <http://www.railcraft.info>
 */
public class TrackScanner {

    /**
     * Verifies that two rails are connected to each other along a straight line
     * with no gaps or wanderings.
     *
     * @param world The World object
     * @return true if they are connected
     */
    public static boolean areTracksConnectedAlongAxis(World world, BlockPos start, BlockPos end) {
        return scanStraightTrackSection(world, start, end).verdict == ScanResult.Verdict.VALID;
    }

    /**
     * Verifies that two rails are connected to each other along a straight line
     * with no gaps or wanderings.
     * <p/>
     * Also records the min and max y values along the way.
     *
     * @param world The World object
     * @return ScanResult object with results
     */
    public static ScanResult scanStraightTrackSection(World world, BlockPos start, BlockPos end) {
        int x1 = start.getX();
        int y1 = start.getY();
        int z1 = start.getZ();

        int x2 = end.getX();
        int y2 = end.getY();
        int z2 = end.getZ();

        int minY = Math.min(y1, y2);
        int maxY = Math.max(y1, y2);
        if (x1 != x2 && z1 != z2)
            return new ScanResult(ScanResult.Verdict.NOT_ALIGNED, minY, maxY);
        if (x1 != x2) {
            int min;
            int max;
            int yy;
            if (x1 < x2) {
                min = x1;
                max = x2;
                yy = y1;
            } else {
                min = x2;
                max = x1;
                yy = y2;
            }
            for (int xx = min; xx <= max; xx++) {
//                if (world.blockExists(xx, yy, z1))
                BlockPos p = new BlockPos(xx, yy, z1);
                if (TrackToolsAPI.isRailBlockAt(world, p)) {
                } else if (TrackToolsAPI.isRailBlockAt(world, p.down())) {
                    yy--;
                    if (yy < minY)
                        minY = yy;
                } else if (TrackToolsAPI.isRailBlockAt(world, p.up())) {
                    yy++;
                    if (yy > maxY)
                        maxY = yy;
                } else if (!world.isBlockLoaded(p)) {
                    return new ScanResult(ScanResult.Verdict.UNKNOWN, minY, maxY);
                } else
                    return new ScanResult(ScanResult.Verdict.PATH_NOT_FOUND, minY, maxY);
            }
        } else if (z1 != z2) {
            int min;
            int max;
            int yy;
            if (z1 < z2) {
                min = z1;
                max = z2;
                yy = y1;
            } else {
                min = z2;
                max = z1;
                yy = y2;
            }
            for (int zz = min; zz <= max; zz++) {
//                if (world.blockExists(x1, yy, zz))
                BlockPos p = new BlockPos(x1, yy, zz);
                if (TrackToolsAPI.isRailBlockAt(world, p)) {
                } else if (TrackToolsAPI.isRailBlockAt(world, p.down())) {
                    yy--;
                    if (yy < minY)
                        minY = yy;
                } else if (TrackToolsAPI.isRailBlockAt(world, p.up())) {
                    yy++;
                    if (yy > maxY)
                        maxY = yy;
                } else if (!world.isBlockLoaded(p)) {
                    return new ScanResult(ScanResult.Verdict.UNKNOWN, minY, maxY);
                } else
                    return new ScanResult(ScanResult.Verdict.PATH_NOT_FOUND, minY, maxY);
            }
        }
        return new ScanResult(ScanResult.Verdict.VALID, minY, maxY);
    }

    public static class ScanResult {
        public final Verdict verdict;
        public final boolean areConnected;
        public final int minY, maxY;

        public ScanResult(Verdict verdict, int minY, int maxY) {
            this.verdict = verdict;
            this.areConnected = verdict == Verdict.VALID;
            this.minY = minY;
            this.maxY = maxY;
        }

        public enum Verdict {
            VALID,
            UNKNOWN,
            NOT_ALIGNED,
            PATH_NOT_FOUND
        }
    }
}