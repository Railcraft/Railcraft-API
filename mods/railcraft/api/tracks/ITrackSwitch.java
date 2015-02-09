package mods.railcraft.api.tracks;


public interface ITrackSwitch extends ITrackInstance
{

    enum ArrowDirection
    {
        NORTH, SOUTH, EAST, WEST, NORTH_SOUTH, EAST_WEST
    };

    public boolean isSwitched();

    public boolean isMirrored();

    public ArrowDirection getRedSignDirection();

    public ArrowDirection getWhiteSignDirection();

    public void registerSwitch(ISwitch switchDevice);
}
