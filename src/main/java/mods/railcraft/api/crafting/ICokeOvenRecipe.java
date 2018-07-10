/*------------------------------------------------------------------------------
 Copyright (c) CovertJaguar, 2011-2017

 This work (the API) is licensed under the "MIT" License,
 see LICENSE.md for details.
 -----------------------------------------------------------------------------*/

package mods.railcraft.api.crafting;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.fluids.FluidStack;

import org.jetbrains.annotations.Nullable;

/**
 * Represents a coke oven recipe.
 *
 * <p>For usage in {@link ICokeOvenCraftingManager}, you may either implement this interface
 * or call {@link ICokeOvenCraftingManager#create(Ingredient, ItemStack, FluidStack, int)}
 * to create one.</p>
 */
public interface ICokeOvenRecipe {

    /**
     * Gets the input matcher for this recipe.
     *
     * @return The input matcher
     */
    Ingredient getInput();

    /**
     * Gets the process time for this recipe.
     *
     * @return The process time
     */
    int getCookTime();

    /**
     * Gets the fluid output for this recipe.
     *
     * <p>Returns {@code null} if this recipe has no fluid products.</p>
     *
     * @return The fluid output
     */
    @Nullable
    FluidStack getFluidOutput();

    /**
     * Gets the item stack output for this recipe.
     *
     * @return The output item stack
     */
    ItemStack getOutput();
}
