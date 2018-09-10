package mods.railcraft.api.charge;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.function.Function;

/**
 * The central hook for accessing the charge system of Railcraft.
 */
public final class ChargeToolsApi {

    static Function<World, IChargeDimension> accessor = (world) -> new IChargeDimension() {
        @Override
        public World getWorld() {
            return world;
        }

        @Override
        public void registerChargeNode(BlockPos pos, ChargeNodeDefinition def) {
        }

        @Override
        public void deregisterChargeNode(BlockPos pos) {
        }

        @Override
        public IChargeNode getNode(BlockPos pos) {
            return new DummyNode(this, pos);
        }
    };

    /**
     * Gets the charge system for a world.
     *
     * <p>If Railcraft is not available, a dummy one is installed.</p>
     *
     * @param world The world
     * @return A charge system
     */
    public static IChargeDimension getDimension(World world) {
        return accessor.apply(world);
    }

    private ChargeToolsApi() {
    }
}
