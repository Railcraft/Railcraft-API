/*------------------------------------------------------------------------------
 Copyright (c) CovertJaguar, 2011-2018

 This work (the API) is licensed under the "MIT" License,
 see LICENSE.md for details.
 -----------------------------------------------------------------------------*/

package mods.railcraft.api.crafting;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;

import java.util.function.Predicate;

/**
 *
 */
public interface IRollingMachineRecipe extends Predicate<InventoryCrafting> {

    @Override
    boolean test(InventoryCrafting inv);

    default ItemStack getOutput(InventoryCrafting inv) {
        return getSampleOutput().copy();
    }

    ItemStack getSampleOutput();

    int getTime();

    default void consume(InventoryCrafting inv) {
        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if (!stack.isEmpty()) {
                inv.decrStackSize(i, 1);
            }
        }
    }
}
