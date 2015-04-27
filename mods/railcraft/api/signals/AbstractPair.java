/*
 * ******************************************************************************
 *  Copyright 2011-2015 CovertJaguar
 *
 *  This work (the API) is licensed under the "MIT" License, see LICENSE.md for details.
 * ***************************************************************************
 */
package mods.railcraft.api.signals;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mods.railcraft.api.core.WorldCoordinate;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

import java.util.*;

/**
 * @author CovertJaguar <http://www.railcraft.info>
 */
public abstract class AbstractPair {
    protected static final Random rand = new Random();
    private static final int SAFE_TIME = 32;
    private static final int PAIR_CHECK_INTERVAL = 128;
    public final TileEntity tile;
    public final String locTag;
    public final int maxPairings;
    protected final Deque<WorldCoordinate> pairings = new LinkedList<WorldCoordinate>();
    private final Collection<WorldCoordinate> safePairings =  Collections.unmodifiableCollection(pairings);
    protected final Set<WorldCoordinate> invalidPairings = new HashSet<WorldCoordinate>();
    private WorldCoordinate coords;
    private boolean isBeingPaired;
    private int update = rand.nextInt();
    private int ticksExisted;
    private boolean needsInit = true;

    public AbstractPair(String locTag, TileEntity tile, int maxPairings) {
        this.tile = tile;
        this.maxPairings = maxPairings;
        this.locTag = locTag;
    }

    protected void addPairing(WorldCoordinate other) {
        pairings.remove(other);
        pairings.add(other);
        while (pairings.size() > getMaxPairings()) {
            pairings.remove();
        }
        SignalTools.packetBuilder.sendPairPacketUpdate(this);
    }

    public void clearPairing(WorldCoordinate other) {
        invalidPairings.add(other);
    }

    public void endPairing() {
        isBeingPaired = false;
    }

    public void tickClient() {
        if (needsInit) {
            needsInit = false;
            SignalTools.packetBuilder.sendPairPacketRequest(this);
        }
    }

    public void tickServer() {
        ticksExisted++;
        update++;
        if (ticksExisted >= SAFE_TIME && update % PAIR_CHECK_INTERVAL == 0)
            validatePairings();
    }

    protected void validatePairings() {
        for (WorldCoordinate coord : pairings) {
            getPairAt(coord);
        }
        cleanPairings();
    }

    public void cleanPairings() {
        boolean changed = pairings.removeAll(invalidPairings);
        invalidPairings.clear();
        if (changed)
            SignalTools.packetBuilder.sendPairPacketUpdate(this);
    }

    protected TileEntity getPairAt(WorldCoordinate coord) {
        if (!pairings.contains(coord))
            return null;

        if (coord.y < 0) {
            clearPairing(coord);
            return null;
        }

        int x = coord.x;
        int y = coord.y;
        int z = coord.z;
        if (!tile.getWorldObj().blockExists(x, y, z))
            return null;

        TileEntity target = tile.getWorldObj().getTileEntity(x, y, z);
        if (isValidPair(coord, target))
            return target;

        clearPairing(coord);
        return null;
    }

    @Deprecated
    public boolean isValidPair(TileEntity otherTile) {
        return false;
    }

    public boolean isValidPair(WorldCoordinate otherCoord, TileEntity otherTile) {
        return isValidPair(otherTile);
    }

    public WorldCoordinate getCoords() {
        if (coords == null)
            coords = new WorldCoordinate(tile.getWorldObj().provider.dimensionId, tile.xCoord, tile.yCoord, tile.zCoord);
        return coords;
    }

    @Deprecated
    public String getName() {
        return getLocalizationTag();
    }

    public String getLocalizationTag() {
        return locTag;
    }

    public int getMaxPairings() {
        return maxPairings;
    }

    public int getNumPairs() {
        return pairings.size();
    }

    public boolean isPaired() {
        return !pairings.isEmpty();
    }

    public Collection<WorldCoordinate> getPairs() {
        return safePairings;
    }

    public TileEntity getTile() {
        return tile;
    }

    public void startPairing() {
        isBeingPaired = true;
    }

    public boolean isBeingPaired() {
        return isBeingPaired;
    }

    public boolean isPairedWith(WorldCoordinate other) {
        return pairings.contains(other);
    }

    protected abstract String getTagName();

    public final void writeToNBT(NBTTagCompound data) {
        NBTTagCompound tag = new NBTTagCompound();
        saveNBT(tag);
        data.setTag(getTagName(), tag);
    }

    protected void saveNBT(NBTTagCompound data) {
        NBTTagList list = new NBTTagList();
        for (WorldCoordinate c : pairings) {
            NBTTagCompound tag = new NBTTagCompound();
            tag.setIntArray("coords", new int[]{c.dimension, c.x, c.y, c.z});
            list.appendTag(tag);
        }
        data.setTag("pairings", list);
    }

    public final void readFromNBT(NBTTagCompound data) {
        NBTTagCompound tag = data.getCompoundTag(getTagName());
        loadNBT(tag);
    }

    protected void loadNBT(NBTTagCompound data) {
        NBTTagList list = data.getTagList("pairings", 10);
        for (byte entry = 0; entry < list.tagCount(); entry++) {
            NBTTagCompound tag = list.getCompoundTagAt(entry);
            int[] c = tag.getIntArray("coords");
            pairings.add(new WorldCoordinate(c[0], c[1], c[2], c[3]));
        }
    }

    @SideOnly(Side.CLIENT)
    public void addPair(int x, int y, int z) {
        pairings.add(new WorldCoordinate(tile.getWorldObj().provider.dimensionId, x, y, z));
    }

    @SideOnly(Side.CLIENT)
    public void removePair(int x, int y, int z) {
        pairings.remove(new WorldCoordinate(tile.getWorldObj().provider.dimensionId, x, y, z));
    }

    public void clearPairings() {
        pairings.clear();
        if (!tile.getWorldObj().isRemote)
            SignalTools.packetBuilder.sendPairPacketUpdate(this);
    }
}
