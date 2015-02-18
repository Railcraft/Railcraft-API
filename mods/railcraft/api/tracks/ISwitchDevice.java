package mods.railcraft.api.tracks;

import net.minecraft.entity.item.EntityMinecart;

public interface ISwitchDevice {

    /**
     * This method is used by the <code>switchTrack</code> to ask the switch
     * device whether it thinks the track should be switched or not. Ultimately,
     * the track itself will decide whether it will be switched, however the
     * track will usually try to honor results of this method when possible.
     * 
     * @param switchTrack The switch track that is asking
     * @param cart The cart that the switch may use to determine switch status.
     * Implementations should expect null values.
     * @return true if the switch would like the track switched
     */
    public boolean shouldSwitch(ITrackSwitch switchTrack, EntityMinecart cart);

}
