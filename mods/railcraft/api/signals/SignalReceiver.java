/*
 * This code is the property of CovertJaguar
 * and may only be used with explicit written
 * permission unless otherwise specified on the
 * license page at railcraft.wikispaces.com.
 */
package mods.railcraft.api.signals;

import mods.railcraft.api.core.WorldCoordinate;
import net.minecraft.tileentity.TileEntity;

/**
 * @author CovertJaguar <http://www.railcraft.info>
 */
public abstract class SignalReceiver extends AbstractPair {
    protected boolean needsInit = true;

    public SignalReceiver(String name, TileEntity tile, int maxPairings) {
        super(name, tile, maxPairings);
    }

    public SignalController getControllerAt(WorldCoordinate coord) {
        TileEntity con = getPairAt(coord);
        if (con != null) {
            return ((IControllerTile) con).getController();
        }
        return null;
    }

    @Override
    protected String getTagName() {
        return "receiver";
    }

    @Override
    public boolean isValidPair(TileEntity otherTile) {
        return isValidPair(null, otherTile);
    }

    @Override
    public boolean isValidPair(WorldCoordinate otherCoord, TileEntity otherTile) {
        if (otherTile instanceof IControllerTile) {
            SignalController controller = ((IControllerTile) otherTile).getController();
            return controller.isPairedWith(getCoords());
        }
        return false;
    }

    public void onControllerAspectChange(SignalController con, SignalAspect aspect) {
        ((IReceiverTile) tile).onControllerAspectChange(con, aspect);
    }

    protected void registerController(SignalController controller) {
        addPairing(controller.getCoords());
    }

    @Override
    public void tickServer() {
        super.tickServer();
        if (needsInit) {
            needsInit = false;
            for (WorldCoordinate pair : pairings) {
                SignalController controller = getControllerAt(pair);
                if (controller != null) {
                    SignalAspect aspect = controller.getAspectFor(getCoords());
                    if (aspect != null) {
                        onControllerAspectChange(controller, aspect);
                    }
                }
            }
        }
    }
}
