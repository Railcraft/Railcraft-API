package mods.railcraft.api.charge;

import com.google.common.collect.ForwardingMap;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Defines the ways nodes connect to each other.
 */
public enum ConnectType {
    /**
     * The typical connections for a track, e.g. Railcraft electric track.
     */
    TRACK {
        @Override
        public Map<BlockPos, EnumSet<ConnectType>> getPossibleConnectionLocations(BlockPos pos) {
            int x = pos.getX();
            int y = pos.getY();
            int z = pos.getZ();
            Map<BlockPos, EnumSet<ConnectType>> positions = new ConnectionMap();

            EnumSet<ConnectType> all = EnumSet.allOf(ConnectType.class);
            EnumSet<ConnectType> notWire = EnumSet.complementOf(EnumSet.of(ConnectType.WIRE));
            EnumSet<ConnectType> track = EnumSet.of(ConnectType.TRACK);

            positions.put(new BlockPos(x + 1, y, z), notWire);
            positions.put(new BlockPos(x - 1, y, z), notWire);

            positions.put(new BlockPos(x + 1, y + 1, z), track);
            positions.put(new BlockPos(x + 1, y - 1, z), track);

            positions.put(new BlockPos(x - 1, y + 1, z), track);
            positions.put(new BlockPos(x - 1, y - 1, z), track);

            positions.put(new BlockPos(x, y - 1, z), all);

            positions.put(new BlockPos(x, y, z + 1), notWire);
            positions.put(new BlockPos(x, y, z - 1), notWire);

            positions.put(new BlockPos(x, y + 1, z + 1), track);
            positions.put(new BlockPos(x, y - 1, z + 1), track);

            positions.put(new BlockPos(x, y + 1, z - 1), track);
            positions.put(new BlockPos(x, y - 1, z - 1), track);
            return positions;
        }

    },
    /**
     * The typical connections for a wire, e.g. Railcraft's charge wire.
     */
    WIRE {
        @Override
        public Map<BlockPos, EnumSet<ConnectType>> getPossibleConnectionLocations(BlockPos pos) {
            int x = pos.getX();
            int y = pos.getY();
            int z = pos.getZ();
            Map<BlockPos, EnumSet<ConnectType>> positions = new ConnectionMap();

            EnumSet<ConnectType> all = EnumSet.allOf(ConnectType.class);
            EnumSet<ConnectType> notTrack = EnumSet.complementOf(EnumSet.of(ConnectType.TRACK));

            positions.put(new BlockPos(x + 1, y, z), notTrack);
            positions.put(new BlockPos(x - 1, y, z), notTrack);
            positions.put(new BlockPos(x, y + 1, z), all);
            positions.put(new BlockPos(x, y - 1, z), notTrack);
            positions.put(new BlockPos(x, y, z + 1), notTrack);
            positions.put(new BlockPos(x, y, z - 1), notTrack);
            return positions;
        }

    },
    /**
     * The typical connections for a charge block, e.g. a charge feeder.
     */
    BLOCK {
        @Override
        public Map<BlockPos, EnumSet<ConnectType>> getPossibleConnectionLocations(BlockPos pos) {
            Map<BlockPos, EnumSet<ConnectType>> positions = new ConnectionMap();

            EnumSet<ConnectType> all = EnumSet.allOf(ConnectType.class);
            EnumSet<ConnectType> notTrack = EnumSet.complementOf(EnumSet.of(ConnectType.TRACK));

            for (EnumFacing facing : EnumFacing.VALUES) {
                positions.put(pos.offset(facing), all);
            }

            positions.put(pos.down(), notTrack);
            return positions;
        }

    };

    /**
     * Offers a map that gives the set of connections accepted by this type of connection at
     * a few absolute positions around the source position.
     *
     * @param pos The absolute position of the source type
     * @return The map described above
     */
    @NotNull
    public abstract Map<BlockPos, EnumSet<ConnectType>> getPossibleConnectionLocations(BlockPos pos);

    class ConnectionMap extends ForwardingMap<BlockPos, EnumSet<ConnectType>> {

        private final Map<BlockPos, EnumSet<ConnectType>> delegate;

        ConnectionMap() {
            delegate = new HashMap<>();
        }

        @Override
        protected Map<BlockPos, EnumSet<ConnectType>> delegate() {
            return delegate;
        }

        @Override
        @NotNull
        public EnumSet<ConnectType> get(@Nullable Object key) {
            EnumSet<ConnectType> ret = super.get(key);
            return ret == null ? EnumSet.noneOf(ConnectType.class) : ret;
        }
    }
}
