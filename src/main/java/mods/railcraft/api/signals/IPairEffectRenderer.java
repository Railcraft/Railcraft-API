/*------------------------------------------------------------------------------
 Copyright (c) CovertJaguar, 2011-2019

 This work (the API) is licensed under the "MIT" License,
 see LICENSE.md for details.
 -----------------------------------------------------------------------------*/
package mods.railcraft.api.signals;

import net.minecraft.tileentity.TileEntity;

/**
 *
 * @author CovertJaguar <http://www.railcraft.info>
 */
public interface IPairEffectRenderer {

    boolean isTuningAuraActive();

    void tuningEffect(TileEntity start, TileEntity dest);
}
