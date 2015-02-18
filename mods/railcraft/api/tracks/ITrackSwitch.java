package mods.railcraft.api.tracks;


import net.minecraft.util.AxisAlignedBB;

public interface ITrackSwitch extends ITrackInstance {

    public boolean isSwitched();

    /**
     * @see #registerSwitch(ISwitchDevice)
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
     * @see #registerSwitch(ISwitchDevice)
     * @see mods.railcraft.api.tracks.ISwitchDevice
     * @deprecated replaced by registerSwitch() and ISwitchDevice
     */
    @Deprecated
    public AxisAlignedBB getRoutingSearchBox();

    /**
     * Register a controller device to the switch.
     * This device will provide guidance to switch
     * as to which direction a cart should travel.
     *
     * @param switchDevice
     */
    public void registerSwitch(ISwitchDevice switchDevice);

    enum ArrowDirection {
        NORTH, SOUTH, EAST, WEST, NORTH_SOUTH, EAST_WEST
    }
}
