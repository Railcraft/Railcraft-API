package mods.railcraft.api.charge;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiFunction;

/**
 * Definitions of charge nodes.
 *
 * <p>{@link ConnectType Connection types} are the connections between nodes.</p>
 *
 * <p>{@code cost} (maintenance cost) represents the per-tick loss of charge caused by
 * this node even if no other consumer used charge.</p>
 *
 * <p>{@code battery} provides a way for non-entity blocks to create their own batteries
 * that does not unload with chunks (but unloads with the world). For blocks that contain
 * entities, the entity should implement {@link IBatteryTile} so that it can register its
 * battery to the charge system.</p>
 *
 * <p>This class is immutable.</p>
 */
public final class ChargeNodeDefinition {
    private static final BiFunction<? super World, ? super BlockPos, @Nullable ? extends IBlockBattery> defaultSupplier = (world, pos) -> {
        TileEntity entity = world.getTileEntity(pos);
        if (entity instanceof IBatteryTile) {
            return ((IBatteryTile) entity).getBattery();
        }
        return null;
    };
    private final ConnectType connectType;
    private final double cost;
    private final BiFunction<? super World, ? super BlockPos, @Nullable ? extends IBlockBattery> batterySupplier;

    /**
     * Creates a charge node definition with given connection type and maintenance cost.
     *
     * @param connectType The connection type
     * @param cost        The maintenance cost
     */
    public ChargeNodeDefinition(ConnectType connectType, double cost) {
        this(connectType, cost, null);
    }

    /**
     * Creates a charge node definition with given connection type and battery supplier.
     *
     * @param connectType     The connection type
     * @param batterySupplier The battery supplier
     */
    public ChargeNodeDefinition(ConnectType connectType, @Nullable BiFunction<? super World, ? super BlockPos, @Nullable ? extends IBlockBattery> batterySupplier) {
        this(connectType, 0.0, batterySupplier);
    }

    /**
     * Creates a charge node definition with given connection type, maintenance cost, and battery supplier.
     *
     * @param connectType     The connection type
     * @param cost            The maintenance cost
     * @param batterySupplier The battery supplier
     */
    public ChargeNodeDefinition(ConnectType connectType, double cost, @Nullable BiFunction<? super World, ? super BlockPos, @Nullable ? extends IBlockBattery> batterySupplier) {
        this.connectType = connectType;
        this.cost = cost;
        this.batterySupplier = batterySupplier == null ? defaultSupplier : batterySupplier;
    }

    /**
     * Gets the per-tick maintenance cost of the node defined.
     *
     * @return The cost
     */
    public double getMaintenanceCost() {
        return cost;
    }

    /**
     * Gets the connect type of the node defined.
     *
     * @see ConnectType
     * @return The connect type
     */
    public ConnectType getConnectType() {
        return connectType;
    }

    /**
     * Used by railcraft to register a block-based battery into the charge system.
     *
     * @param world The world
     * @param pos The position
     * @return The battery
     */
    @Nullable
    public IBlockBattery createBattery(World world, BlockPos pos) {
        if (batterySupplier == null)
            return null;
        return batterySupplier.apply(world, pos);
    }

    /**
     * Used by the railcraft charge storage to store the information about charge nodes.
     *
     * @param tag The nbt compound
     */
    public void writeToNBT(NBTTagCompound tag) {
        tag.setByte("connectType", (byte) connectType.ordinal());
        tag.setDouble("cost", cost);
    }

    /**
     * Used by the railcraft charge storage to read the information about charge nodes.
     *
     * @param tag The nbt compound
     * @return The read charge node definition
     */
    public static ChargeNodeDefinition readFromNBT(NBTTagCompound tag) {
        ConnectType type = ConnectType.values()[tag.getByte("connectType")];
        double cost = tag.getDouble("cost");
        return new ChargeNodeDefinition(type, cost);
    }

    @Override
    public String toString() {
        return String.format("ChargeDef{%s, cost=%f, hasBat=%s}", connectType, cost, batterySupplier != null);
    }
}
