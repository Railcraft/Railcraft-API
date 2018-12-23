/*------------------------------------------------------------------------------
 Copyright (c) CovertJaguar, 2011-2018

 This work (the API) is licensed under the "MIT" License,
 see LICENSE.md for details.
 -----------------------------------------------------------------------------*/

package mods.railcraft.api.crafting;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * A manager for coke oven recipes.
 *
 * @author CovertJaguar <http://www.railcraft.info>
 */
public interface ICokeOvenCrafter {
    /**
     * Adds a coke oven recipe with the following arguments.
     *
     * @param name        A resource location that describes the fuel source.
     *                    It is only used for logging, so it doesn't need to be exact or unique.
     * @param input       The input ingredient, should not be empty
     * @param output      The output item stack, may be empty
     * @param fluidOutput The fluid output, may be {@code null}
     * @param cookTime    The cooking time
     */
    default void addRecipe(String name, Ingredient input, ItemStack output, @Nullable FluidStack fluidOutput, int cookTime) {
        addRecipe(new ResourceLocation(name), input, output, fluidOutput, cookTime);
    }

    /**
     * Adds a coke oven recipe with the following arguments.
     *
     * @param name        A resource location that describes the fuel source.
     *                    It is only used for logging, so it doesn't need to be exact or unique.
     * @param input       The input ingredient, should not be empty
     * @param output      The output item stack, may be empty
     * @param fluidOutput The fluid output, may be {@code null}
     * @param cookTime    The cooking time
     */
    default void addRecipe(@Nullable ResourceLocation name, Ingredient input, ItemStack output, @Nullable FluidStack fluidOutput, int cookTime) {
    }

    /**
     * Gets the coke oven recipe that matches this input item stack.
     *
     * @param stack The input item stack
     * @return The matching recipe, may be {@code null}
     */
    default Optional<IRecipe> getRecipe(ItemStack stack) {
        return Optional.empty();
    }

    /**
     * Gets all the coke oven recipes registered in this manager.
     *
     * You can remove recipes from this list, but do not add them, it will throw an UnsupportedOperationException.
     *
     * @return The recipes registered
     */
    default List<IRecipe> getRecipes() {
        return Collections.emptyList();
    }

    /**
     * A coke oven recipe.
     */
    interface IRecipe extends ISimpleRecipe {

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
}
