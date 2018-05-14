package mods.railcraft.api.core;

import net.minecraft.item.crafting.Ingredient;

import javax.annotation.Nullable;

/**
 * An interface for containers that can extract getRecipes.
 */
public interface IIngredientSource {

    /**
     * Gets an ingredient for this container. If one is not available,
     * {@link Ingredient#EMPTY} is returned.
     *
     * @return The ingredient
     */
    Ingredient getIngredient();

    /**
     * Gets an ingredient for this container of a certain variant.
     *
     * @param variant The specified variant
     * @return The ingredient
     * @deprecated Variants are to be removed
     */
    default Ingredient getIngredient(@Nullable IVariantEnum variant) {
        return getIngredient();
    }
}
