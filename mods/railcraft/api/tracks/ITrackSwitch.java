package mods.railcraft.api.tracks;

public interface ITrackSwitch extends ITrackInstance {

    public boolean isSwitched();

    public boolean isMirrored();

    public ArrowDirection getRedSignDirection();

    public ArrowDirection getWhiteSignDirection();

    /**
     * This method should only return a valid (isInvalid() == false) ISwitchDevice or null
     * @return the ISwitchDevice that can interact with this switch track
     */
    public ISwitchDevice getSwitchDevice();

    enum ArrowDirection {
        NORTH, SOUTH, EAST, WEST, NORTH_SOUTH, EAST_WEST
    }
}
