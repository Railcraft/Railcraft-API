package mods.railcraft.api.crafting;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.function.Predicate;

/**
 *
 */
public interface IRollingMachineRecipe extends Predicate<@NonNull InventoryCrafting> {

    @Override
    boolean test(@NonNull InventoryCrafting inv);

    default ItemStack getOutput(@NonNull InventoryCrafting inv) {
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
