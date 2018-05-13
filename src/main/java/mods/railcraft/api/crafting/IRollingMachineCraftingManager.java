/*------------------------------------------------------------------------------
 Copyright (c) CovertJaguar, 2011-2017

 This work (the API) is licensed under the "MIT" License,
 see LICENSE.md for details.
 -----------------------------------------------------------------------------*/

package mods.railcraft.api.crafting;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

import javax.annotation.Nullable;
import java.util.Collection;

/**
 * This interface allows you to interact with the Rolling Machine Crafting Manager.
 * You can add any type of IRecipe to the recipe list directly, but it only provides helpers for vanilla getRecipes.
 *
 * @author CovertJaguar <http://www.railcraft.info>
 */
public interface IRollingMachineCraftingManager {

    /**
     * Adds a rolling machine recipe to all types of rolling machines.
     *
     * @param recipe The recipe instance
     */
    void addRecipe(IRollingMachineRecipe recipe);

    ShapedRecipeBuilder newShapedRecipeBuilder();

    ShapelessRecipeBuilder newShapelessRecipeBuilder();

    /**
     * Adds a shaped rolling machine recipe.
     *
     * @param output     the result
     * @param components the crafting ingredients
     */
    @Deprecated
    void addRecipe(ItemStack output, Object... components);

    /**
     * Adds a shapeless rolling machine recipe.
     *
     * @param output     the result
     * @param components the crafting ingredients
     */
    @Deprecated
    void addShapelessRecipe(ItemStack output, Object... components);

    /**
     * Given a specific combination of ItemStacks, this function will output the resulting crafting result.
     *
     * @param inventoryCrafting the crafting inventory
     * @return the resulting ItemStack
     */
    @Nullable
    IRollingMachineRecipe findMatching(InventoryCrafting inventoryCrafting);

    /**
     * You can add/remove getRecipes by modifying this list.
     * It fully supports and IRecipe, including Forge OreDict Recipes.
     *
     * @return the complete, fully modifiable, recipe list.
     */
    Collection<IRollingMachineRecipe> getRecipes();

    interface RecipeBuilder<S extends RecipeBuilder<S>> {

        S ingredients(Ingredient... ingredients);

        S ingredients(Iterable<Ingredient> ingredients);

        S output(ItemStack output);

        S time(int time);

        IRollingMachineRecipe build() throws IllegalArgumentException;

        void buildAndRegister() throws IllegalArgumentException;
    }

    interface ShapelessRecipeBuilder extends RecipeBuilder<ShapelessRecipeBuilder> {
        ShapelessRecipeBuilder add(Ingredient ingredient);
    }

    interface ShapedRecipeBuilder extends RecipeBuilder<ShapedRecipeBuilder> {
        ShapedRecipeBuilder height(int height);

        ShapedRecipeBuilder width(int width);

        ShapedRecipeBuilder grid(Ingredient[][] ingredients);

        ShapedRecipeBuilder allowsFlip(boolean flip);

        default ShapedRecipeBuilder allowsFlip() {
            return allowsFlip(true);
        }
    }

}
