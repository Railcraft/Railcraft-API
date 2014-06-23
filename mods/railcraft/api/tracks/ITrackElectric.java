/*
 * Copyright (c) CovertJaguar, 2011 http://railcraft.info
 * 
 * This code is the property of CovertJaguar
 * and may only be used with explicit written
 * permission unless otherwise specified on the
 * license page at railcraft.wikispaces.com.
 */
package mods.railcraft.api.tracks;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

/**
 *
 * @author CovertJaguar <http://www.railcraft.info/>
 */
public interface ITrackElectric {

    public static final double MAX_CHARGE = 500.0;
    public static final double LOSS = 0.99;
    public static final int SEARCH_INTERVAL = 64;
    public static final int BALANCE_INTERVAL = 4;
    public static final Random rand = new Random();

    public ChargeHandler getChargeHandler();

    public static class ChargeHandler {

        private final ITrackInstance track;
        private final Set<ChargeHandler> neighbors = new HashSet<ChargeHandler>();
        private double charge = 0;
        private int clock = rand.nextInt();

        public ChargeHandler(ITrackInstance track) {
            this.track = track;
        }

        public void balance(ChargeHandler other) {
            double total = charge + other.charge;
            charge = total / 2;
        }

        public boolean addCharge(double charge) {
            if (this.charge < MAX_CHARGE) {
                this.charge += charge;
                return true;
            }
            return false;
        }

        public double removeCharge(double request) {
            if (charge >= request) {
                charge -= request;
                return request;
            }
            charge = 0.0;
            return charge;
        }

        public void tick() {
            clock++;
            charge += LOSS;

            if (clock % SEARCH_INTERVAL == 0) {
                neighbors.clear();
                Set<ITrackTile> tracks = RailTools.getAdjecentTrackTiles(track.getWorld(), track.getX(), track.getY(), track.getZ());
                for (ITrackTile t : tracks) {
                    ITrackInstance ti = t.getTrackInstance();
                    if (ti instanceof ITrackElectric)
                        neighbors.add(((ITrackElectric) ti).getChargeHandler());
                }
            }

            if (clock % BALANCE_INTERVAL == 0) {
                Iterator<ChargeHandler> it = neighbors.iterator();
                while (it.hasNext()) {
                    ChargeHandler ch = it.next();
                    if (ch.track.getTile().isInvalid())
                        it.remove();
                }
                for (ChargeHandler t : neighbors) {
                    balance(t);
                }
            }
        }

    }
}
