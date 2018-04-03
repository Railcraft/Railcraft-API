/*------------------------------------------------------------------------------
 Copyright (c) CovertJaguar, 2011-2016

 This work (the API) is licensed under the "MIT" License,
 see LICENSE.md for details.
 -----------------------------------------------------------------------------*/

package mods.railcraft.api.core;

import javax.annotation.Nullable;

/**
 * Created by CovertJaguar on 9/2/2016 for Railcraft.
 *
 * @author CovertJaguar <http://www.railcraft.info>
 */
public interface IRailcraftRecipeIngredient {
    @Nullable
    Object getRecipeObject();

    @Nullable
    default Object getRecipeObject(@Nullable IVariantEnum variant) {
        return getRecipeObject();
    }
}
