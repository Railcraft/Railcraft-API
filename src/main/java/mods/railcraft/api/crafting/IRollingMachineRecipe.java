package mods.railcraft.api.crafting;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

/**
 *
 */
public interface IRollingMachineRecipe extends Predicate<@NotNull InventoryCrafting> {

    @Override
    boolean test(@NotNull InventoryCrafting inv);

    default ItemStack getOutput(@NotNull InventoryCrafting inv) {
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
