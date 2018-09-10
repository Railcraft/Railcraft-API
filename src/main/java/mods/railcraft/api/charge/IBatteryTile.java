package mods.railcraft.api.charge;

import net.minecraft.tileentity.TileEntity;
import org.jetbrains.annotations.Nullable;

/**
 * Tile entities that have batteries should implement this method.
 */
public interface IBatteryTile {
    /**
     * Allows railcraft to retrieve the battery and register in the charge system.
     *
     * @return The battery to register
     */
    @Nullable
    IBlockBattery getBattery();

    /**
     * Utility method for easy loading of batteries.
     *
     * <p>This method is suggested to be called in {@link TileEntity#onLoad()}</p>
     */
    default void loadBattery() {
        TileEntity entity = (TileEntity) this;
        ChargeToolsApi.getDimension(entity.getWorld()).getNode(entity.getPos()).loadBattery();
    }

    /**
     * Utility method for easy unloading of batteries.
     *
     * <p>This method is suggested to be called in {@link TileEntity#onChunkUnload()}</p>
     */
    default void unloadBattery() {
        TileEntity entity = (TileEntity) this;
        ChargeToolsApi.getDimension(entity.getWorld()).getNode(entity.getPos()).unloadBattery();
    }
}
