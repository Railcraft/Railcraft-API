/*------------------------------------------------------------------------------
 Copyright (c) CovertJaguar, 2011-2019

 This work (the API) is licensed under the "MIT" License,
 see LICENSE.md for details.
 -----------------------------------------------------------------------------*/

package mods.railcraft.api.items;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * A condensation rule for metal chests.
 */
public interface IMetalsChestCondenseRule {
    /**
     * A list of metals chest condense rules the metals chest will check.
     */
    List<IMetalsChestCondenseRule> rules = new ArrayList<>();

    /**
     * Checks if an item can be consumed for condensation
     *
     * @return the predicate to check if an item matches
     */
    Predicate<ItemStack> getPredicate();

    /**
     * Returns the number of item needed for one condensation.
     */
    int removeCount();

    /**
     * Gets the condensation result. Make sure to make clean copies!
     */
    ItemStack getResult();

    /**
     * Utility method for quickly creating a metals chest condensation rule.
     *
     * @param predicate the checker
     * @param count the requirement count
     * @param result the compressed result
     * @return the created rule
     */
    static IMetalsChestCondenseRule of(Predicate<ItemStack> predicate, int count, ItemStack result) {
        return new MetalsChestCondenseRuleImpl(predicate, count, result);
    }
}
