/*
 * ******************************************************************************
 *  Copyright 2011-2015 CovertJaguar
 *
 *  This work (the API) is licensed under the "MIT" License, see LICENSE.md for details.
 * ***************************************************************************
 */

package mods.railcraft.api.crafting;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author CovertJaguar <http://www.railcraft.info>
 */
public interface ICrusherCraftingManager {

    ICrusherRecipeBuilder createRecipeBuilder();

    void addRecipe(ICrusherRecipe recipe);

    /**
     * This function will locate the highest priority recipe that successfully matches against the given ItemStack.
     */
    @Nullable
    ICrusherRecipe getRecipe(ItemStack input);

    IGenRule createGenRule(float randomChance);

    default IGenRule createGenRule() {
        return createGenRule(1.0F);
    }

    /**
     * Returns the list of Recipes, it is safe to modify this list (probably).
     */
    List<ICrusherRecipe> getRecipes();

    interface ICrusherRecipeBuilder {
        ICrusherRecipeBuilder input(Ingredient input);

        ICrusherRecipeBuilder addOutput(IOutputEntry entry);

        ICrusherRecipeBuilder addOutput(ItemStack output, IGenRule rule);

        ICrusherRecipeBuilder addOutput(ItemStack output, float chance);

        default ICrusherRecipeBuilder addOutput(ItemStack output) {
            return addOutput(output, 1);
        }

        ICrusherRecipe build() throws IllegalArgumentException;

        void buildAndRegister() throws IllegalArgumentException;
    }

}
