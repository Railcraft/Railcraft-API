/*------------------------------------------------------------------------------
 Copyright (c) CovertJaguar, 2011-2018

 This work (the API) is licensed under the "MIT" License,
 see LICENSE.md for details.
 -----------------------------------------------------------------------------*/

package mods.railcraft.api.crafting;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public interface IBlastFurnaceCrafter {

    // TODO Add descriptor name
    default void addRecipe(Ingredient input, int cookTime, ItemStack output, ItemStack secondOutput) { }

    /**
     * Add an ItemStack as a fuel source. It uses the standard Furnace cookTime for the heat value.
     *
     * @param name A resource location that describes the fuel source.
     *             It is only used for logging, so it doesn't need to be exact.
     */
    default void addFuel(@Nullable ResourceLocation name, ItemStack stack) { }

    /**
     * @param name A resource location that describes the fuel source.
     *             It is only used for logging, so it doesn't need to be exact.
     */
    default void addFuel(@Nullable ResourceLocation name, ItemStack stack, int cookTime) {
        addFuel(name, Ingredient.fromStacks(stack), cookTime);
    }

    /**
     * @param name A resource location that describes the fuel source.
     *             It is only used for logging, so it doesn't need to be exact.
     */
    default void addFuel(@Nullable ResourceLocation name, Ingredient input, int cookTime) { }

    /**
     * You can remove fuels from this list, but do not add them, it will throw an UnsupportedOperationException.
     */
    default List<@NotNull IFuel> getFuels() {
        return Collections.emptyList();
    }

    /**
     * You can remove recipes from this list, but do not add them, it will throw an UnsupportedOperationException.
     */
    default List<@NotNull IRecipe> getRecipes() {
        return Collections.emptyList();
    }

    default int getCookTime(ItemStack stack) {
        return 0;
    }

    default Optional<IRecipe> getRecipe(ItemStack stack) {
        return Optional.empty();
    }

    /**
     * Represents a blast furnace fuel.
     */
    interface IFuel {
        /**
         * Gets the input for this fuel.
         *
         * @return The input matcher
         */
        default Ingredient getInput() {
            return Ingredient.EMPTY;
        }

        /**
         * Gets the cooking time for this fuel, in ticks.
         *
         * @return The cooking time
         */
        default int getCookTime() {
            return 0;
        }
    }

    /**
     * Represents a blast furnace recipe.
     */
    interface IRecipe {

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
}
