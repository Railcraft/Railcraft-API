/*------------------------------------------------------------------------------
 Copyright (c) CovertJaguar, 2011-2018

 This work (the API) is licensed under the "MIT" License,
 see LICENSE.md for details.
 -----------------------------------------------------------------------------*/

package mods.railcraft.api.crafting;

import mods.railcraft.api.core.IIngredientSource;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @author CovertJaguar <http://www.railcraft.info>
 */
public interface IRockCrusherCrafter {
    // TODO Add descriptor name
    default IRecipeBuilder makeRecipe(Ingredient input) {
        return new IRecipeBuilder() {};
    }

    // TODO Add descriptor name
    default IRecipeBuilder makeRecipe(IIngredientSource input) {
        return makeRecipe(input.getIngredient());
    }

    /**
     * This function will locate the highest priority recipe that successfully matches against the given ItemStack.
     */
    default Optional<IRecipe> getRecipe(ItemStack input) {
        return Optional.empty();
    }

    /**
     * You can remove recipes from this list, but do not add them, it will throw an UnsupportedOperationException.
     */
    default List<IRecipe> getRecipes() {
        return Collections.emptyList();
    }

    interface IRecipeBuilder {
        default IRecipeBuilder addOutput(IOutputEntry entry) {
            return this;
        }

        default IRecipeBuilder addOutput(ItemStack output, IGenRule rule) {
            return this;
        }

        default IRecipeBuilder addOutput(ItemStack output, float chance) {
            return this;
        }

        default IRecipeBuilder addOutput(ItemStack output) {
            return addOutput(output, 1);
        }

        default void register() throws IllegalArgumentException {}
    }

    /**
     * A Rock Crusher Recipe
     */
    interface IRecipe {

        Ingredient getInput();

        /**
         * Returns a list containing each output entry.
         */
        List<IOutputEntry> getOutputs();

        /**
         * Returns a list of outputs after it has passed through the predicate processor.
         */
        default List<ItemStack> pollOutputs(Random random) {
            return getOutputs().stream()
                    .filter(entry -> entry.getGenRule().test(random))
                    .map(IOutputEntry::getOutput)
                    .collect(Collectors.toList());
        }

    }
}
