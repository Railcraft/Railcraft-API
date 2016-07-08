/*
 * ******************************************************************************
 *  Copyright 2011-2015 CovertJaguar
 *
 *  This work (the API) is licensed under the "MIT" License, see LICENSE.md for details.
 * ***************************************************************************
 */

package mods.railcraft.api.crafting;

import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;
import java.util.List;

/**
 * @author CovertJaguar <http://www.railcraft.info>
 */
public interface IBlastFurnaceCraftingManager {

    /**
     * Adds a new Blast Furnace Recipe.
     *
     * If either the input or output are null, the recipe will not be registered.
     *
     * @param input       the input, if null the function will silently abort
     * @param matchDamage if true, it will compare item damage, if false, just
     *                    the item ID
     * @param matchNBT    if true, it will compare nbt
     * @param cookTime    the time it takes to cook the recipe
     * @param output      the output
     */
    void addRecipe(@Nullable ItemStack input, boolean matchDamage, boolean matchNBT, int cookTime,@Nullable  ItemStack output);

    List<ItemStack> getFuels();

    @Nullable
    IBlastFurnaceRecipe getRecipe(ItemStack stack);

    List<? extends IBlastFurnaceRecipe> getRecipes();
}
