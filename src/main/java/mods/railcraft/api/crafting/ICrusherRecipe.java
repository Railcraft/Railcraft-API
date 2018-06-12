package mods.railcraft.api.crafting;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
        List<ItemStack> result = new ArrayList<>();
        for (IOutputEntry entry : getOutputs()) {
            if (entry.getGenRule().test(random)) {
                result.add(entry.getOutput());
            }
        }
        return result;
    }

}
