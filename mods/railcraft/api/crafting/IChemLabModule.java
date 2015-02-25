/*
 * Copyright (c) CovertJaguar, 2015 http://railcraft.info
 *
 * This code is the property of CovertJaguar
 * and may only be used with explicit written
 * permission unless otherwise specified on the
 * license page at http://railcraft.info/wiki/info:license.
 */

package mods.railcraft.api.crafting;

/**
 * Created by CovertJaguar on 2/16/2015.
 */
public interface IChemLabModule {

    String getType();

    boolean isReadyForUse();

    void onRecipeUse(Object... args);
}
