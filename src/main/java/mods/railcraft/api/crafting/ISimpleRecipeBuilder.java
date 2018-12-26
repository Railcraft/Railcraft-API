/*------------------------------------------------------------------------------
 Copyright (c) CovertJaguar, 2011-2018

 This work (the API) is licensed under the "MIT" License,
 see LICENSE.md for details.
 -----------------------------------------------------------------------------*/

package mods.railcraft.api.crafting;

import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.Nullable;

/**
 * Created by CovertJaguar on 12/25/2018 for Railcraft.
 *
 * @author CovertJaguar <http://www.railcraft.info>
 */
@SuppressWarnings("unchecked")
public interface ISimpleRecipeBuilder<B extends ISimpleRecipeBuilder> {
    /**
     * Adds a resource location that describes the fuel source.
     * It is only used for logging, so it doesn't need to be exact.
     *
     * This is required.
     */
    default B name(@Nullable ResourceLocation name) {
        return (B) this;
    }

    /**
     * Adds a resource location that describes the fuel source.
     * It is only used for logging, so it doesn't need to be exact.
     *
     * This is required.
     */
    default B name(String name) {
        name(new ResourceLocation(name));
        return (B) this;
    }

    /**
     * Adds a resource location that describes the fuel source.
     * It is only used for logging, so it doesn't need to be exact.
     *
     * This is required.
     */
    default B name(String namespace, String descriptor) {
        name(new ResourceLocation(namespace, descriptor));
        return (B) this;
    }

    /**
     * Sets the cooking time/heat value/process time for this recipe, in ticks.
     */
    default B time(int ticks) {
        return (B) this;
    }

    default ResourceLocation getName() {
        return new ResourceLocation("invalid", "dummy");
    }

    default boolean notRegistered() {return true;}
}
