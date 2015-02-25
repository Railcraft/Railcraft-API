/*
 * Copyright (c) CovertJaguar, 2015 http://railcraft.info
 *
 * This code is the property of CovertJaguar
 * and may only be used with explicit written
 * permission unless otherwise specified on the
 * license page at http://railcraft.info/wiki/info:license.
 */

package mods.railcraft.api.crafting;

/**
 * Created by CovertJaguar on 2/16/2015.
 */
public interface IChemLabRecipe {

    /**
     * A unique identifier for the recipe, should be of the form "[modid]:[recipename]".
     *
     * @return
     */
    String getTag();

    int getCookTime();

    int getMinHeat();

    int getMaxHeat();

    /**
     * This is called every cook step (20 ticks) where the ChemLabs heat value falls below the operating limits for the recipe as define by getMinHeat().
     * If you want to turn the inputs into a block of Ice, do it here.
     * During this time the recipe will not progress towards completion and will likely require user input to resolve.
     *
     * @param chemlab
     */
    void onMinHeat(IChemLab chemlab);

    /**
     * This is called every cook step (20 ticks) where the ChemLabs heat value exceeds the operating limits for the recipe as define by getMaxHeat().
     * If you want to explode, this is the place to do it.
     * During this time the recipe will not progress towards completion and will likely require user input to resolve.
     *
     * @param chemlab
     */
    void onMaxHeat(IChemLab chemlab);

    /**
     * Called once per cook step (20 ticks), but only if canContinue() returns true.
     * This is where you should use consumable resources from the ChemLab Expansion Modules.
     * This is also where you generate or remove heat from the process.
     * <p/>
     * This function is still called even in min/max heat situations.
     *
     * @param chemlab
     */
    void onCookStep(IChemLab chemlab);

    /**
     * Called once upon recipe completion. This is where you should consume inputs and generate outputs.
     *
     * @param chemlab
     */
    void onComplete(IChemLab chemlab);

    /**
     * This function should return true if, and only if, the necessary inputs are present to evaluate the recipe.
     * This includes items, fluids, and ChemLab Expansion Modules.
     *
     * @param chemlab
     * @return
     */
    boolean matches(IChemLab chemlab);

    /**
     * Called once per cook step (20 ticks), you should return true if, and only if, the recipe has everything it needs.
     * This includes room for the output, specific ChemLab Expansion Modules (and their resources), etc...
     *
     * @param chemLab
     * @return
     */
    boolean canContinue(IChemLab chemLab);
}
