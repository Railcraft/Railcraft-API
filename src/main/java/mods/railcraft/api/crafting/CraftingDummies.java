package mods.railcraft.api.crafting;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.fluids.FluidStack;
import org.checkerframework.checker.nullness.qual.NonNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * A utility class for keeping dummy instances.
 */
final class CraftingDummies {

    static final ICokeOvenCraftingManager COKE_OVEN_CRAFTING_MANAGER = new ICokeOvenCraftingManager() {
        final List<ICokeOvenRecipe> recipes = new ArrayList<>();

        @Override
        public ICokeOvenRecipe create(Ingredient input, ItemStack output, @Nullable FluidStack liquidOutput, int cookTime) {
            return CraftingDummies.COKE_OVEN_RECIPE;
        }

        @Override
        public void addRecipe(ICokeOvenRecipe recipe) {
        }

        @Nullable
        @Override
        public ICokeOvenRecipe getRecipe(ItemStack stack) {
            return null;
        }

        @Override
        public Collection<ICokeOvenRecipe> getRecipes() {
            return this.recipes;
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

        @Nullable
        @Override
        public IRollingMachineRecipe findMatching(InventoryCrafting inventoryCrafting) {
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

        @Nullable
        @Override
        public FluidStack getFluidOutput() {
            return null;
        }

        @Override
        public ItemStack getOutput() {
            return ItemStack.EMPTY;
        }
    };

    static final IRollingMachineRecipe ROLLING_MACHINE_RECIPE = new IRollingMachineRecipe() {
        @Override
        public boolean test(@NonNull InventoryCrafting inv) {
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
