/*
 * Copyright (c) CovertJaguar, 2015 http://railcraft.info
 *
 * This code is the property of CovertJaguar
 * and may only be used with explicit written
 * permission unless otherwise specified on the
 * license page at http://railcraft.info/wiki/info:license.
 */

package mods.railcraft.api.crafting;

import net.minecraft.inventory.IInventory;
import net.minecraftforge.fluids.IFluidTank;

import java.util.List;

/**
 * Created by CovertJaguar on 2/16/2015.
 */
public interface IChemLab {

    void addHeat(int heat);

    int getHeat();

    /**
     * A failure mode in which random ChemLab blocks are replaced with an inert block of similar composition.
     * This block can be recycled back into Steel and reforged.
     *
     * This failure mode is often the result of extremely cold temperatures.
     */
    void crack();

    /**
     * Causes the ChemLab to experience a standard RUD failure mode.
     * This can be the result of high temperature, pressure, and/or a runaway reaction.
     */
    void explode();

    /**
     * This failure mode replaces random ChemLab blocks with Lava source blocks.
     * This is generally the result of extremely high temperatures.
     */
    void melt();

    void incrementCookTime();

    int getCookTime();

    void setCookTime(int cookTime);

    List<IChemLabModule> getModules();

    IInventory getInputInv();

    IInventory getOutputInv();

    IFluidTank getInputTank1();

    IFluidTank getInputTank2();

    IFluidTank getOutputTank1();

    IFluidTank getOutputTank2();

    boolean isCooking();
}
