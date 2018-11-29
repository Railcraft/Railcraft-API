/*------------------------------------------------------------------------------
 Copyright (c) CovertJaguar, 2011-2018

 This work (the API) is licensed under the "MIT" License,
 see LICENSE.md for details.
 -----------------------------------------------------------------------------*/

package mods.railcraft.api.crafting;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

/**
 * Represents a blast furnace recipe.
 *
 * <p>Call {@link IBlastFurnaceCraftingManager#createRecipe(Ingredient, int, ItemStack, ItemStack)}</p>
 * to create one such recipe.
 */
public interface IBlastFurnaceRecipe {

    /**
     * Gets the input for this recipe.
     *
     * @return The input for this recipe
     */
    Ingredient getInput();

    /**
     * Gets the cooking time for this recipe.
     *
     * @return The cooking time
     */
    int getCookTime();

    /**
     * Gets the output for this recipe.
     *
     * @return The output, safe to modify
     */
    ItemStack getOutput();

    /**
     * Gets the secondary output for this recipe.
     *
     * @return The secondary output, safe to modify
     */
    ItemStack getSecondOutput();
}
