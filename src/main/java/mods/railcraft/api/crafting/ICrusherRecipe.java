/*------------------------------------------------------------------------------
 Copyright (c) CovertJaguar, 2011-2018

 This work (the API) is licensed under the "MIT" License,
 see LICENSE.md for details.
 -----------------------------------------------------------------------------*/

package mods.railcraft.api.crafting;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 *
 */
public interface ICrusherRecipe {

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
