/*------------------------------------------------------------------------------
 Copyright (c) CovertJaguar, 2011-2018

 This work (the API) is licensed under the "MIT" License,
 see LICENSE.md for details.
 -----------------------------------------------------------------------------*/

package mods.railcraft.api.core;

import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.CraftingHelper;
import org.jetbrains.annotations.Nullable;

/**
 * Created by CovertJaguar on 9/2/2016 for Railcraft.
 *
 * @author CovertJaguar <http://www.railcraft.info>
 */
public interface IRailcraftRecipeIngredient extends IIngredientSource {

    /**
     * @deprecated As of MC 1.12, replaced by
     * {@link #getIngredient()} }
     */
    @Nullable
    @Deprecated
    Object getRecipeObject();

    /**
     * @deprecated As of MC 1.12, replaced by
     * {@link #getIngredient(IVariantEnum)} }
     */
    @Deprecated
    default @Nullable Object getRecipeObject(@Nullable IVariantEnum variant) {
        return getRecipeObject();
    }

    @Override
    @SuppressWarnings("deprecation")
    default Ingredient getIngredient() {
        Ingredient ret = CraftingHelper.getIngredient(getRecipeObject());
        return ret == null ? Ingredient.EMPTY : ret;
    }

    @Override
    @SuppressWarnings("deprecation")
    default Ingredient getIngredient(@Nullable IVariantEnum variant) {
        Ingredient ret = CraftingHelper.getIngredient(getRecipeObject(variant));
        return ret == null ? Ingredient.EMPTY : ret;
    }
}
