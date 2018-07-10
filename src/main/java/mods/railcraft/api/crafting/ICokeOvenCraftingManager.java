/*------------------------------------------------------------------------------
 Copyright (c) CovertJaguar, 2011-2016

 This work (the API) is licensed under the "MIT" License,
 see LICENSE.md for details.
 -----------------------------------------------------------------------------*/

package mods.railcraft.api.crafting;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.fluids.FluidStack;

import org.jetbrains.annotations.Nullable;
import java.util.Collection;

/**
 * A manager for coke oven getRecipes.
 *
 * @author CovertJaguar <http://www.railcraft.info>
 */
public interface ICokeOvenCraftingManager {

    /**
     * Creates a coke oven recipe.
     *
     * <p>You are free to make your own coke oven getRecipes, too.</p>
     *
     * @param input        The input ingredient, should not be empty
     * @param output       The output item stack, may be empty
     * @param liquidOutput The fluid output, may be {@code null}
     * @param cookTime     The cooking time
     * @return The created recipe
     */
    ICokeOvenRecipe create(Ingredient input, ItemStack output, @Nullable FluidStack liquidOutput, int cookTime);

    /**
     * Adds a coke oven recipe to the coke oven crafting manager.
     *
     * @param recipe The recipe instance
     */
    void addRecipe(ICokeOvenRecipe recipe);

    /**
     * Adds a coke oven recipe with the following arguments.
     *
     * @param input        The input ingredient, should not be empty
     * @param output       The output item stack, may be empty
     * @param liquidOutput The fluid output, may be {@code null}
     * @param cookTime     The cooking time
     */
    default void addRecipe(Ingredient input, ItemStack output, @Nullable FluidStack liquidOutput, int cookTime) {
        addRecipe(create(input, output, liquidOutput, cookTime));
    }

    /**
     * Gets the coke oven recipe that matches this input item stack.
     *
     * @param stack The input item stack
     * @return The matching recipe, may be {@code null}
     */
    @Nullable
    ICokeOvenRecipe getRecipe(ItemStack stack);

    /**
     * Gets all the coke oven getRecipes registered in this manager.
     *
     * @return The getRecipes registered
     */
    Collection<ICokeOvenRecipe> getRecipes();
}
