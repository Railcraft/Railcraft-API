/*
 * Copyright (c) CovertJaguar, 2015 http://railcraft.info
 *
 * This code is the property of CovertJaguar
 * and may only be used with explicit written
 * permission unless otherwise specified on the
 * license page at http://railcraft.info/wiki/info:license.
 */

package mods.railcraft.api.crafting;

import com.google.common.collect.BiMap;

/**
 * Created by CovertJaguar on 2/22/2015.
 */
public interface IChemLabCraftingManager {
    BiMap<String, IChemLabRecipe> getRecipes();

    void addRecipe(IChemLabRecipe recipe);

    void removeRecipe(String recipeTag);

    IChemLabRecipe getMatchingRecipe(IChemLab chemlab);
}
