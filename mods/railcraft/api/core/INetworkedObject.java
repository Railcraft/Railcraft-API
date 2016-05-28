/*
 * ******************************************************************************
 *  Copyright 2011-2015 CovertJaguar
 *
 *  This work (the API) is licensed under the "MIT" License, see LICENSE.md for details.
 * ***************************************************************************
 */

package mods.railcraft.api.core;

import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public interface INetworkedObject {

    @Nullable
    World theWorld();

    void writePacketData(DataOutputStream data) throws IOException;

    void readPacketData(DataInputStream data) throws IOException;
}
