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
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import mods.railcraft.api.core.WorldCoordinate;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

/**
 * Any Electric Track needs to implement this interface on either the track
 * TileEntity or ITrackInstance object.
 *
 * @author CovertJaguar <http://www.railcraft.info/>
 */
public interface IElectricGrid {

    public static final double MAX_CHARGE = 10000.0;
    public static final double LOSS = 0.05;
    public static final int SEARCH_INTERVAL = 64;
    public static final Random rand = new Random();

    public ChargeHandler getChargeHandler();

    public TileEntity getTile();

    public static final class ChargeHandler {

        public enum ConnectType {

            TRACK {

                        @Override
                        public Set<WorldCoordinate> getPossibleConnectionLocations(IElectricGrid gridObject) {
                            int dim = gridObject.getTile().getWorldObj().provider.dimensionId;
                            int x = gridObject.getTile().xCoord;
                            int y = gridObject.getTile().yCoord;
                            int z = gridObject.getTile().zCoord;
                            Set<WorldCoordinate> positions = new HashSet<WorldCoordinate>();
                            positions.add(new WorldCoordinate(dim, x + 1, y, z));
                            positions.add(new WorldCoordinate(dim, x - 1, y, z));

                            positions.add(new WorldCoordinate(dim, x + 1, y + 1, z));
                            positions.add(new WorldCoordinate(dim, x + 1, y - 1, z));

                            positions.add(new WorldCoordinate(dim, x - 1, y + 1, z));
                            positions.add(new WorldCoordinate(dim, x - 1, y - 1, z));

                            positions.add(new WorldCoordinate(dim, x, y - 1, z));

                            positions.add(new WorldCoordinate(dim, x, y, z + 1));
                            positions.add(new WorldCoordinate(dim, x, y, z - 1));

                            positions.add(new WorldCoordinate(dim, x, y + 1, z + 1));
                            positions.add(new WorldCoordinate(dim, x, y - 1, z + 1));

                            positions.add(new WorldCoordinate(dim, x, y + 1, z - 1));
                            positions.add(new WorldCoordinate(dim, x, y - 1, z - 1));
                            return positions;
                        }

                    },
            WIRE {
                        @Override
                        public Set<WorldCoordinate> getPossibleConnectionLocations(IElectricGrid gridObject) {
                            int dim = gridObject.getTile().getWorldObj().provider.dimensionId;
                            int x = gridObject.getTile().xCoord;
                            int y = gridObject.getTile().yCoord;
                            int z = gridObject.getTile().zCoord;
                            Set<WorldCoordinate> positions = new HashSet<WorldCoordinate>();
                            positions.add(new WorldCoordinate(dim, x + 1, y, z));
                            positions.add(new WorldCoordinate(dim, x - 1, y, z));
                            positions.add(new WorldCoordinate(dim, x, y + 1, z));
                            positions.add(new WorldCoordinate(dim, x, y - 1, z));
                            positions.add(new WorldCoordinate(dim, x, y, z + 1));
                            positions.add(new WorldCoordinate(dim, x, y, z - 1));
                            return positions;
                        }

                    },
            BLOCK {
                        @Override
                        public Set<WorldCoordinate> getPossibleConnectionLocations(IElectricGrid gridObject) {
                            int dim = gridObject.getTile().getWorldObj().provider.dimensionId;
                            int x = gridObject.getTile().xCoord;
                            int y = gridObject.getTile().yCoord;
                            int z = gridObject.getTile().zCoord;
                            Set<WorldCoordinate> positions = new HashSet<WorldCoordinate>();
                            positions.add(new WorldCoordinate(dim, x + 1, y, z));
                            positions.add(new WorldCoordinate(dim, x - 1, y, z));
                            positions.add(new WorldCoordinate(dim, x, y + 1, z));
                            positions.add(new WorldCoordinate(dim, x, y - 1, z));
                            positions.add(new WorldCoordinate(dim, x, y, z + 1));
                            positions.add(new WorldCoordinate(dim, x, y, z - 1));
                            return positions;
                        }

                    };

            public abstract Set<WorldCoordinate> getPossibleConnectionLocations(IElectricGrid gridObject);

        };

        private final IElectricGrid gridObject;
        private final ConnectType type;
        private final Set<ChargeHandler> neighbors = new HashSet<ChargeHandler>();
        private double charge = 0;
        private int clock = rand.nextInt();

        public ChargeHandler(IElectricGrid gridObject, ConnectType type) {
            this.gridObject = gridObject;
            this.type = type;
        }

        public Set<WorldCoordinate> getPossibleConnectionLocations() {
            return type.getPossibleConnectionLocations(gridObject);
        }

        public double getCharge() {
            return charge;
        }

        public double getCapacity() {
            return MAX_CHARGE;
        }

        /**
         * Averages the charge between two ChargeHandlers.
         *
         * @param other
         */
        public void balance(ChargeHandler other) {
            double total = charge + other.charge;
            double half = total / 2.0;
            charge = half;
            other.charge = half;
        }

        public void addCharge(double charge) {
            this.charge += charge;
        }

        /**
         * Remove up to the requested amount of charge and returns the amount
         * removed.
         *
         * @param request
         * @return charge removed
         */
        public double removeCharge(double request) {
            if (charge >= request) {
                charge -= request;
                return request;
            }
            double ret = charge;
            charge = 0.0;
            return ret;
        }

        /**
         * Must be called once per tick by the owning object. Server side only.
         */
        public void tick() {
            clock++;
            removeCharge(LOSS);

            if (charge <= 0.0)
                return;

            if (clock % SEARCH_INTERVAL == 0) {
                neighbors.clear();
                Set<IElectricGrid> connections = GridTools.getMutuallyConnectedObjects(gridObject);
                for (IElectricGrid t : connections) {
                    neighbors.add(t.getChargeHandler());
                }
            }

            Iterator<ChargeHandler> it = neighbors.iterator();
            while (it.hasNext()) {
                ChargeHandler ch = it.next();
                if (ch.gridObject.getTile().isInvalid())
                    it.remove();
            }
            for (ChargeHandler t : neighbors) {
                balance(t);
            }
        }

        /**
         * Must be called by the owning object's save function.
         *
         * @param nbt
         */
        public void writeToNBT(NBTTagCompound nbt) {
            NBTTagCompound tag = new NBTTagCompound();
            tag.setDouble("charge", charge);
            nbt.setTag("chargeHandler", tag);
        }

        /**
         * Must be called by the owning object's load function.
         *
         * @param nbt
         */
        public void readFromNBT(NBTTagCompound nbt) {
            NBTTagCompound tag = nbt.getCompoundTag("chargeHandler");
            charge = tag.getDouble("charge");
        }

    }

}
