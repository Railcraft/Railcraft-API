/*------------------------------------------------------------------------------
 Copyright (c) CovertJaguar, 2011-2016

 This work (the API) is licensed under the "MIT" License,
 see LICENSE.md for details.
 -----------------------------------------------------------------------------*/
package mods.railcraft.api.core;

import net.minecraft.util.IStringSerializable;

import javax.annotation.Nullable;

/**
 * @author CovertJaguar <http://www.railcraft.info/>
 */
public interface IVariantEnum extends IStringSerializable {

    int ordinal();

    default String getResourcePathSuffix() {
        return getName().replace("_", ".");
    }

    @Nullable
    default String getOreTag() {
        return null;
    }

    @Nullable
    default Object getAlternate(String objectTag) {
        return getOreTag();
    }

    default boolean isEnabled() {
        return true;
    }
}
