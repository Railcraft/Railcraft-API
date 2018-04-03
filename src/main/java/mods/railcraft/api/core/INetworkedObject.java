/*------------------------------------------------------------------------------
 Copyright (c) CovertJaguar, 2011-2017

 This work (the API) is licensed under the "MIT" License,
 see LICENSE.md for details.
 -----------------------------------------------------------------------------*/

package mods.railcraft.api.core;

import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public interface INetworkedObject<I extends DataInputStream, O extends DataOutputStream> {

    @Nullable
    World theWorld();

    default void readPacketData(I data) throws IOException {
    }

    default void writePacketData(O data) throws IOException {
    }

    void sendUpdateToClient();

}
