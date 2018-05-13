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
import org.checkerframework.checker.nullness.qual.NonNull;

import javax.annotation.Nullable;
import java.util.List;

public interface IBlastFurnaceCraftingManager {

    IBlastFurnaceFuel createFuel(Ingredient matcher, int cookTime);

    IBlastFurnaceRecipe createRecipe(Ingredient matcher, int cookTime, ItemStack output);

    void addRecipe(IBlastFurnaceRecipe recipe);

    void addFuel(IBlastFurnaceFuel fuel);

    default void addRecipe(Ingredient input, int cookTime, ItemStack output) {
        addRecipe(createRecipe(input, cookTime, output));
    }

    default void addFuel(Ingredient input, int cookTime) {
        addFuel(createFuel(input, cookTime));
    }

    List<@NonNull IBlastFurnaceFuel> getFuels();

    int getCookTime(ItemStack stack);

    @Nullable
    IBlastFurnaceRecipe getRecipe(ItemStack stack);

    List<@NonNull IBlastFurnaceRecipe> getRecipes();
}
