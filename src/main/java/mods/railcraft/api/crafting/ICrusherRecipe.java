package mods.railcraft.api.crafting;

import com.google.common.base.Predicate;
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
     * Adds a new entry to the output list.
     *
     * It accepts a Predicate object to be used for determining if an output entry should generate during result processing.
     *
     * If the output is null, the call will be ignored.
     *
     * Note to ModTweaker Devs:
     * I'd suggest adding production rules similar to the following:
     * "random:XX" - Simple random chance to generate
     * "max:XX" - Maximum number of items to generate
     * "exclusive:_groupName_" - Only one entry tagged with the same GroupName can generate
     *
     * @param output  the stack to output
     * @param genRule the rules for whether an entry should be produced
     * @see Predicate
     */
    @Deprecated
    void addOutput(ItemStack output, IGenRule genRule);

    /**
     * Adds a new entry to the output list.
     *
     * If the output is null, the call will be ignored.
     *
     * This is equivalent to:
     * <code>
     * addOutput(output, createGenRule(randomChance));
     * </code>
     *
     * @param output       the stack to output
     * @param randomChance the change to output this stack
     */
    @Deprecated
    default void addOutput(ItemStack output, float randomChance) {
        addOutput(output, RailcraftCraftingManager.getRockCrusherCraftings().createGenRule(randomChance));
    }

    /**
     * Adds a new entry to the output list.
     *
     * If the output is null, the call will be ignored.
     *
     * This is equivalent to:
     * <code>
     * addOutput(output, createGenRule());
     * </code>
     *
     * @param output the stack to output
     */
    @Deprecated
    default void addOutput(ItemStack output) {
        addOutput(output, 1);
    }

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
