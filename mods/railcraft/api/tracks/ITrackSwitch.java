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
     * This method should only return a valid (isInvalid() == false) ISwitchDevice or null
     * @return the ISwitchDevice that can interact with this switch track
     */
    public ISwitchDevice getSwitchDevice();
}
