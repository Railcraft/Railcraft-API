/*
 * ******************************************************************************
 *  Copyright 2011-2015 CovertJaguar
 *
 *  This work (the API) is licensed under the "MIT" License, see LICENSE.md for details.
 * ***************************************************************************
 */

package mods.railcraft.api.crafting;

import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

/**
 * @author CovertJaguar <http://www.railcraft.info>
 */
public interface IBlastFurnaceRecipe {

    int getCookTime();

    ItemStack getInput();

    ItemStack getOutput();

    int getOutputStackSize();

    boolean isRoomForOutput(ItemStack outputSlot);
}
