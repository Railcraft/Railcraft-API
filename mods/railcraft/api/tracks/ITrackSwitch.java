package mods.railcraft.api.tracks;

import net.minecraft.util.AxisAlignedBB;

public interface ITrackSwitch extends ITrackInstance {
    public boolean isSwitched();

    /**
     * @see #getSwitchDevice()
     * @see mods.railcraft.api.tracks.ISwitchDevice
     * @deprecated replaced by registerSwitch() and ISwitchDevice
     */
    @Deprecated
    public void setSwitched(boolean switched);

    public boolean isMirrored();

    public ArrowDirection getRedSignDirection();

    public ArrowDirection getWhiteSignDirection();

    /**
     * @return null
     * @see #getSwitchDevice()
     * @see mods.railcraft.api.tracks.ISwitchDevice
     * @deprecated replaced by registerSwitch() and ISwitchDevice
     */
    @Deprecated
    public AxisAlignedBB getRoutingSearchBox();

    /**
     * This method should only return a valid (isInvalid() == false) ISwitchDevice or null
     *
     * @return the ISwitchDevice that can interact with this switch track
     */
    public ISwitchDevice getSwitchDevice();

    enum ArrowDirection {
        NORTH, SOUTH, EAST, WEST, NORTH_SOUTH, EAST_WEST
    }
}
