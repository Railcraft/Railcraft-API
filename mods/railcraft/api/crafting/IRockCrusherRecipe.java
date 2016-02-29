/*
 * ******************************************************************************
 *  Copyright 2011-2015 CovertJaguar
 *
 *  This work (the API) is licensed under the "MIT" License, see LICENSE.md for details.
 * ***************************************************************************
 */

package mods.railcraft.api.crafting;

import net.minecraft.item.ItemStack;

import java.util.List;
import java.util.Map;

/**
 * @author CovertJaguar <http://www.railcraft.info>
 */
@SuppressWarnings("unused")
public interface IRockCrusherRecipe {

    ItemStack getInput();

    /**
     * Adds a new entry to the output list.
     *
     * @param output the stack to output
     * @param chance the change to output this stack
     */
    void addOutput(ItemStack output, float chance);

    /**
     * Returns a list containing each output entry and its chance of being
     * included.
     */
    List<Map.Entry<ItemStack, Float>> getOutputs();

    /**
     * Returns a list of all possible outputs. This is basically a condensed
     * version of getOutputs() without the chances.
     */
    List<ItemStack> getPossibleOutputs();

    /**
     * Returns a list of outputs after it has passed through the randomizer.
     */
    List<ItemStack> getRandomizedOutputs();
}
