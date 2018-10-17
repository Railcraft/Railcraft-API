/*------------------------------------------------------------------------------
 Copyright (c) CovertJaguar, 2011-2018

 This work (the API) is licensed under the "MIT" License,
 see LICENSE.md for details.
 -----------------------------------------------------------------------------*/

package mods.railcraft.api.crafting;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * A utility class for keeping dummy instances.
 */
final class CraftingDummies {

    static final ICokeOvenCraftingManager COKE_OVEN_CRAFTING_MANAGER = new ICokeOvenCraftingManager() {

        @Override
        public ICokeOvenRecipe create(Ingredient input, ItemStack output, @Nullable FluidStack liquidOutput, int cookTime) {
            return CraftingDummies.COKE_OVEN_RECIPE;
        }

        @Override
        public void addRecipe(ICokeOvenRecipe recipe) {
        }

        @Override
        public @Nullable ICokeOvenRecipe getRecipe(ItemStack stack) {
            return null;
        }

        @Override
        public Collection<ICokeOvenRecipe> getRecipes() {
            return Collections.emptyList();
        }
    };

    static final IBlastFurnaceCraftingManager BLAST_FURNACE_CRAFTING_MANAGER = new IBlastFurnaceCraftingManager() {
        @Override
        public IBlastFurnaceFuel createFuel(Ingredient matcher, int cookTime) {
            return BLAST_FURNACE_FUEL;
        }

        @Override
        public IBlastFurnaceRecipe createRecipe(Ingredient matcher, int cookTime, ItemStack output, ItemStack secondOutput) {
            return BLAST_FURNACE_RECIPE;
        }

        @Override
        public void addRecipe(IBlastFurnaceRecipe recipe) {
        }

        @Override
        public void addFuel(IBlastFurnaceFuel fuel) {
        }

        @Override
        public List<IBlastFurnaceFuel> getFuels() {
            return Collections.emptyList();
        }

        @Override
        public int getCookTime(ItemStack stack) {
            return 0;
        }

        @Override
        public @Nullable IBlastFurnaceRecipe getRecipe(ItemStack stack) {
            return null;
        }

        @Override
        public List<IBlastFurnaceRecipe> getRecipes() {
            return Collections.emptyList();
        }
    };

    static final IBlastFurnaceRecipe BLAST_FURNACE_RECIPE = new IBlastFurnaceRecipe() {
        @Override
        public Ingredient getInput() {
            return Ingredient.EMPTY;
        }

        @Override
        public int getCookTime() {
            return 0;
        }

        @Override
        public ItemStack getOutput() {
            return ItemStack.EMPTY;
        }

        @Override
        public ItemStack getSecondOutput() {
            return ItemStack.EMPTY;
        }
    };

    static final IBlastFurnaceFuel BLAST_FURNACE_FUEL = new IBlastFurnaceFuel() {
        @Override
        public Ingredient getInput() {
            return Ingredient.EMPTY;
        }

        @Override
        public int getCookTime() {
            return 0;
        }
    };

    static final IRollingMachineCraftingManager ROLLING_MACHINE_CRAFTING_MANAGER = new IRollingMachineCraftingManager() {
        @Override
        public void addRecipe(IRollingMachineRecipe recipe) {
        }

        @Override
        public ShapedRecipeBuilder newShapedRecipeBuilder() {
            return new ShapedRecipeBuilder() {
                @Override
                public ShapedRecipeBuilder height(int height) {
                    return this;
                }

                @Override
                public ShapedRecipeBuilder width(int width) {
                    return this;
                }

                @Override
                public ShapedRecipeBuilder grid(Ingredient[][] ingredients) {
                    return this;
                }

                @Override
                public ShapedRecipeBuilder allowsFlip(boolean b) {
                    return this;
                }

                @Override
                public ShapedRecipeBuilder ingredients(Ingredient... ingredients) {
                    return this;
                }

                @Override
                public ShapedRecipeBuilder ingredients(Iterable<Ingredient> ingredients) {
                    return this;
                }

                @Override
                public ShapedRecipeBuilder output(ItemStack output) {
                    return this;
                }

                @Override
                public ShapedRecipeBuilder time(int time) {
                    return this;
                }

                @Override
                public IRollingMachineRecipe build() throws IllegalArgumentException {
                    return CraftingDummies.ROLLING_MACHINE_RECIPE;
                }

                @Override
                public void buildAndRegister() throws IllegalArgumentException {
                }
            };
        }

        @Override
        public ShapelessRecipeBuilder newShapelessRecipeBuilder() {
            return new ShapelessRecipeBuilder() {
                @Override
                public ShapelessRecipeBuilder add(Ingredient ingredient) {
                    return this;
                }

                @Override
                public ShapelessRecipeBuilder ingredients(Ingredient... ingredients) {
                    return this;
                }

                @Override
                public ShapelessRecipeBuilder ingredients(Iterable<Ingredient> ingredients) {
                    return this;
                }

                @Override
                public ShapelessRecipeBuilder output(ItemStack output) {
                    return this;
                }

                @Override
                public ShapelessRecipeBuilder time(int time) {
                    return this;
                }

                @Override
                public IRollingMachineRecipe build() throws IllegalArgumentException {
                    return CraftingDummies.ROLLING_MACHINE_RECIPE;
                }

                @Override
                public void buildAndRegister() throws IllegalArgumentException {
                }
            };
        }

        @Override
        public void addRecipe(ItemStack output, Object... components) {
        }

        @Override
        public void addShapelessRecipe(ItemStack output, Object... components) {
        }

        @Override
        public @Nullable IRollingMachineRecipe findMatching(InventoryCrafting inventoryCrafting) {
            return null;
        }

        @Override
        public Collection<IRollingMachineRecipe> getRecipes() {
            return Collections.emptyList();
        }
    };

    static final ICokeOvenRecipe COKE_OVEN_RECIPE = new ICokeOvenRecipe() {
        @Override
        public int getCookTime() {
            return 0;
        }

        @Override
        public Ingredient getInput() {
            return Ingredient.EMPTY;
        }

        @Override
        public @Nullable FluidStack getFluidOutput() {
            return null;
        }

        @Override
        public ItemStack getOutput() {
            return ItemStack.EMPTY;
        }
    };

    static final IRollingMachineRecipe ROLLING_MACHINE_RECIPE = new IRollingMachineRecipe() {
        @Override
        public boolean test(InventoryCrafting inv) {
            return false;
        }

        @Override
        public int getTime() {
            return 1000;
        }

        @Override
        public ItemStack getSampleOutput() {
            return ItemStack.EMPTY;
        }
    };

    private CraftingDummies() {
    }
}
