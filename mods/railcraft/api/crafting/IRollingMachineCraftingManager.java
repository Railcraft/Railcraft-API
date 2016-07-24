/*------------------------------------------------------------------------------
 Copyright (c) CovertJaguar, 2011-2016

 This work (the API) is licensed under the "MIT" License,
 see LICENSE.md for details.
 -----------------------------------------------------------------------------*/

package mods.railcraft.api.crafting;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

/**
 * This interface allows you to interact with the Rolling Machine Crafting Manager.
 * You can add any type of IRecipe to the recipe list directly, but it only provides helpers for vanilla recipes.
 *
 * @author CovertJaguar <http://www.railcraft.info>
 */
public interface IRollingMachineCraftingManager {

    /**
     * This function can be used to add any type of IRecipe to the table.
     */
    void addRecipe(IRecipe recipe);

    /**
     * Adds a vanilla style shaped recipe.
     *
     * @param output     the result
     * @param components the crafting matrix
     */
    void addRecipe(@Nullable ItemStack output, Object... components);

    /**
     * Adds a vanilla style shapeless recipe.
     *
     * @param output     the result
     * @param components the crafting matrix
     */
    void addShapelessRecipe(@Nullable ItemStack output, Object... components);

    /**
     * Given a specific combination of ItemStacks, this function will output the resulting crafting result.
     *
     * @param inventoryCrafting the crafting inventory
     * @param world             the world
     * @return the resulting ItemStack
     */
    ItemStack findMatchingRecipe(InventoryCrafting inventoryCrafting, World world);

    /**
     * You can add/remove recipes by modifying this list.
     * It fully supports and IRecipe, including Forge OreDict Recipes.
     *
     * @return the complete, fully modifiable, recipe list.
     */
    List<IRecipe> getRecipeList();

}
