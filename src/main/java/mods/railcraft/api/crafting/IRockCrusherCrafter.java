/*------------------------------------------------------------------------------
 Copyright (c) CovertJaguar, 2011-2018

 This work (the API) is licensed under the "MIT" License,
 see LICENSE.md for details.
 -----------------------------------------------------------------------------*/

package mods.railcraft.api.crafting;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @author CovertJaguar <http://www.railcraft.info>
 */
public interface IRockCrusherCrafter {
    int PROCESS_TIME = 100;

    /**
     * Begins the definition of a Rock Crusher recipe.
     *
     * @param name A resource location that describes the recipe.
     *             It is only used for logging, so it doesn't need to be exact or unique.
     */
    default IRecipeBuilder makeRecipe(String name, Ingredient input) {
        return makeRecipe(new ResourceLocation(name), input);
    }

    default IRecipeBuilder makeRecipe(@Nullable ResourceLocation name, Ingredient input) {
        return new IRecipeBuilder() {};
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
        default IRecipeBuilder setProcessTime(int processTime) {
            return this;
        }

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
    interface IRecipe extends ISimpleRecipe {

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
