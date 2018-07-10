/*
 * ******************************************************************************
 *  Copyright 2011-2015 CovertJaguar
 *
 *  This work (the API) is licensed under the "MIT" License, see LICENSE.md for details.
 * ***************************************************************************
 */

package mods.railcraft.api.helpers;

import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;

import org.jetbrains.annotations.Nullable;
import java.util.List;

/**
 * @author CovertJaguar <http://www.railcraft.info/>
 */
@SuppressWarnings("unused")
public interface StructureHelper {

    void placeBlastFurnace(World world, BlockPos pos, @Nullable ItemStack input, @Nullable ItemStack output, @Nullable ItemStack fuel);

    void placeCokeOven(World world, BlockPos pos, int creosote, @Nullable ItemStack input, @Nullable ItemStack output);

    void placeFluidBoiler(World world, BlockPos pos, int width, int height, boolean highPressure, int water, @Nullable FluidStack fuel);

    void placeIronTank(World world, BlockPos pos, int patternIndex, @Nullable FluidStack fluid);

    void placeRockCrusher(World world, BlockPos pos, int patternIndex, @Nullable List<ItemStack> input, @Nullable List<ItemStack> output);

    void placeSolidBoiler(World world, BlockPos pos, int width, int height, boolean highPressure, int water, @Nullable List<ItemStack> fuel);

    void placeSteamOven(World world, BlockPos pos, @Nullable List<ItemStack> input, @Nullable List<ItemStack> output);

    void placeSteelTank(World world, BlockPos pos, int patternIndex, @Nullable FluidStack fluid);

    void placeWaterTank(World world, BlockPos pos, int water);

    void placeFluxTransformer(World world, BlockPos pos);

}
