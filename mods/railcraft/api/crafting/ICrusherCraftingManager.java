/*
 * ******************************************************************************
 *  Copyright 2011-2015 CovertJaguar
 *
 *  This work (the API) is licensed under the "MIT" License, see LICENSE.md for details.
 * ***************************************************************************
 */

package mods.railcraft.api.crafting;

import com.google.common.base.Predicate;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;

/**
 * @author CovertJaguar <http://www.railcraft.info>
 */
@SuppressWarnings("unused")
public interface ICrusherCraftingManager {
    /**
     * Creates a new ICrusherRecipe object.
     * Accepts a Predicate object to be used for determining if a given input matches.
     *
     * @see #createInputMatcher(ItemStack, boolean, boolean)
     * @see Predicate
     */
    ICrusherRecipe createRecipe(IInputMatcher inputMatcher);

    /**
     * Creates a new ICrusherRecipe object.
     *
     * This is equivalent to:
     * <code>
     * createRecipe(output, createInputMatcher(input, matchDamage, matchNBT));
     * </code>
     *
     * @see #createInputMatcher(ItemStack, boolean, boolean)
     * @see Predicate
     */
    ICrusherRecipe createRecipe(ItemStack input, boolean matchDamage, boolean matchNBT);

    /**
     * Creates a new ICrusherRecipe object and adds it to the recipe list.
     * Accepts a Predicate object to be used for determining if a given input matches.
     *
     * This is equivalent to:
     * <code>
     * ICrusherRecipe recipe = createRecipe(output, inputMatcher);
     * recipes().add(recipe);
     * </code>
     *
     * @see #createInputMatcher(ItemStack, boolean, boolean)
     * @see Predicate
     */
    ICrusherRecipe createAndAddRecipe(IInputMatcher inputMatcher);

    /**
     * Creates a new ICrusherRecipe object and adds it to the recipe list.
     *
     * This is equivalent to:
     * <code>
     * ICrusherRecipe recipe = createRecipe(output, createInputMatcher(input, matchDamage, matchNBT));
     * recipes().add(recipe);
     * </code>
     *
     * @see #createInputMatcher(ItemStack, boolean, boolean)
     * @see Predicate
     */
    ICrusherRecipe createAndAddRecipe(ItemStack input, boolean matchDamage, boolean matchNBT);

    /**
     * This function will locate the highest priority recipe that successfully matches against the given ItemStack.
     */
    @Nullable
    ICrusherRecipe getRecipe(ItemStack input);

    /**
     * Returns the list of Recipes, it is safe to modify this list (probably).
     */
    Collection<? extends ICrusherRecipe> recipes();

    /**
     * Creates a Predicate object capable of performing complex decision making about whether
     * an input ItemStack matches this recipe. Use of this method is not required. You may create your own IInputMatcher.
     *
     * @param input       the input ItemStack
     * @param matchDamage if true, the recipe will only match if the item damages match exactly
     * @param matchNBT    if true, the recipe will only match if the item NBTCompounds match exactly
     */
    IInputMatcher createInputMatcher(ItemStack input, boolean matchDamage, boolean matchNBT);

    /**
     * Creates a Predicate object capable of performing complex decision making about whether an output entry
     * should generate when a recipe is completed.
     *
     * @param randomChance the base chance an entry will appear in the output
     * @param maxItems     if the number of outputs already generated has exceeded this number, don't generate this entry, -1 will disable this check
     * @param groupNames   only one entry from the set of entries sharing identical group names will generate
     */
    IGenRule createGenRule(float randomChance, int maxItems, String... groupNames);

    IGenRule createGenRule(float randomChance, String... groupNames);

    IGenRule createGenRule();

    interface IInputMatcher extends Predicate<ItemStack> {
        enum Priority {
            LOW, MEDIUM, HIGH
        }

        /**
         * ItemStack used for display in recipe lookup mods.
         */
        ItemStack getDisplayStack();

        /**
         * The Priority of the recipe. Higher Priority recipes are matched against first.
         * {@link #createInputMatcher(ItemStack, boolean, boolean)} will by default create a HIGH Priority matcher
         * when matchNBT is true and a MEDIUM Priority matcher when matchDamage is true.
         */
        Priority getPriority();
    }

    interface IGenRule extends Predicate<List<IOutputEntry>> {

        /**
         * A brief description of the rules for generating this entry.
         */
        List<ITextComponent> getToolTip();
    }

    interface IOutputEntry {

        ItemStack getOutput();

        IGenRule getGenRule();

    }

    interface ICrusherRecipe {

        IInputMatcher getInputMatcher();

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
         * @see #createGenRule(float, int, String...)
         * @see Predicate
         */
        void addOutput(@Nullable ItemStack output, IGenRule genRule);

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
        void addOutput(@Nullable ItemStack output, float randomChance);

        /**
         * Adds a new entry to the output list.
         *
         * If the output is null, the call will be ignored.
         *
         * This is equivalent to:
         * <code>
         * addOutput(output, createGenRule(randomChance, maxItems, groupNames));
         * </code>
         *
         * @param randomChance the base chance an entry will appear in the output
         * @param maxItems     if the number of outputs already generated has exceeded this number, don't generate this entry, -1 will disable this check
         * @param groupNames   only one entry from the set of entries sharing identical group names will generate
         */
        void addOutput(@Nullable ItemStack output, float randomChance, int maxItems, String... groupNames);

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
        void addOutput(@Nullable ItemStack output);

        /**
         * Returns a list containing each output entry.
         */
        List<IOutputEntry> getOutputs();

        /**
         * Returns a list of all possible outputs. This is basically a condensed
         * version of getOutputs().
         */
        List<ItemStack> getPossibleOutputs();

        /**
         * Returns a list of outputs after it has passed through the predicate processor.
         */
        List<ItemStack> getProcessedOutputs();

    }
}
