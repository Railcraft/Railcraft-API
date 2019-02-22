/*------------------------------------------------------------------------------
 Copyright (c) CovertJaguar, 2011-2019

 This work (the API) is licensed under the "MIT" License,
 see LICENSE.md for details.
 -----------------------------------------------------------------------------*/
package mods.railcraft.api.signals;

import mods.railcraft.api.signal.IColorLightAspect;
import mods.railcraft.api.signal.IRule;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

/**
 *
 * @author CovertJaguar <http://www.railcraft.info>
 */
public interface IReceiverTile {

    World getWorld();

    SignalReceiver getReceiver();

    @Deprecated
    void onControllerAspectChange(SignalController con, SignalAspect aspect);

    default void onControllerRuleChange(SignalController con, @Nullable IRule<IColorLightAspect> rule) {
        onControllerAspectChange(con, SignalAspect.fromRule(rule));
    }

}
