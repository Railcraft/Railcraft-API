/*
 * Copyright (c) CovertJaguar, 2011 http://railcraft.info
 * 
 * This code is the property of CovertJaguar
 * and may only be used with explicit written
 * permission unless otherwise specified on the
 * license page at railcraft.wikispaces.com.
 */
package mods.railcraft.api.electricity;

import java.util.HashSet;
import java.util.Set;
import mods.railcraft.api.core.WorldCoordinate;
import mods.railcraft.api.tracks.ITrackInstance;
import mods.railcraft.api.tracks.ITrackTile;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 *
 * @author CovertJaguar <http://www.railcraft.info/>
 */
public class GridTools {

    public static Set<IElectricGrid> getMutuallyConnectedObjects(IElectricGrid gridObject) {
        Set<IElectricGrid> connectedObjects = new HashSet<IElectricGrid>();

        WorldCoordinate myPos = new WorldCoordinate(gridObject.getTile());
        for (WorldCoordinate position : gridObject.getChargeHandler().getPossibleConnectionLocations()) {
            IElectricGrid g = getGridObjectAt(gridObject.getTile().getWorldObj(), position);
            if (g != null && g.getChargeHandler().getPossibleConnectionLocations().contains(myPos))
                connectedObjects.add(g);
        }
        return connectedObjects;
    }

    public static IElectricGrid getGridObjectAt(World world, WorldCoordinate pos) {
        return getGridObjectAt(world, pos.x, pos.y, pos.z);
    }

    public static IElectricGrid getGridObjectAt(World world, int x, int y, int z) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile == null)
            return null;
        if (tile instanceof IElectricGrid)
            return (IElectricGrid) tile;
        if (tile instanceof ITrackTile) {
            ITrackInstance track = ((ITrackTile) tile).getTrackInstance();
            if (track instanceof IElectricGrid)
                return (IElectricGrid) track;
        }
        return null;
    }

}
