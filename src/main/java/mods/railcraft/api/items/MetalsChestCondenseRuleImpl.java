/*------------------------------------------------------------------------------
 Copyright (c) CovertJaguar, 2011-2019

 This work (the API) is licensed under the "MIT" License,
 see LICENSE.md for details.
 -----------------------------------------------------------------------------*/

package mods.railcraft.api.items;

import net.minecraft.item.ItemStack;

import java.util.function.Predicate;

class MetalsChestCondenseRuleImpl implements IMetalsChestCondenseRule {
    private final Predicate<ItemStack> predicate;
    private final int count;
    private final ItemStack output;

    MetalsChestCondenseRuleImpl(Predicate<ItemStack> predicate, int count, ItemStack output) {
        this.predicate = predicate;
        this.count = count;
        this.output = output.copy();
    }

    @Override
    public Predicate<ItemStack> getPredicate() {
        return predicate;
    }

    @Override
    public int removeCount() {
        return count;
    }

    @Override
    public ItemStack getResult() {
        return output.copy();
    }
}
