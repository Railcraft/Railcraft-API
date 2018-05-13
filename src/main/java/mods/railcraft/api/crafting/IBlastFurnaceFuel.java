package mods.railcraft.api.crafting;

import net.minecraft.item.crafting.Ingredient;

/**
 * Represents a blast furnace fuel.
 *
 * <p>Call {@link IBlastFurnaceCraftingManager#createFuel(Ingredient, int)}
 * to create one such fuel.</p>
 */
public interface IBlastFurnaceFuel {

    /**
     * Gets the input for this fuel.
     *
     * @return The input matcher
     */
    Ingredient getInput();

    /**
     * Gets the cooking time for this fuel, in ticks.
     *
     * @return The cooking time
     */
    int getCookTime();
}
