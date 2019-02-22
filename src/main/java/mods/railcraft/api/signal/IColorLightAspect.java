/*------------------------------------------------------------------------------
 Copyright (c) CovertJaguar, 2011-2019

 This work (the API) is licensed under the "MIT" License,
 see LICENSE.md for details.
 -----------------------------------------------------------------------------*/

package mods.railcraft.api.signal;

import net.minecraft.util.ResourceLocation;

public interface IColorLightAspect extends IAspect {

    IColorLightAspect OFF = BuiltInSignals.OFF_ASPECT;

    int getLightLevel();

    boolean isBlinking();

    default int getColorMultiplier() {
        return BuiltInSignals.WHITE_COLOR_MULTIPLIER;
    }

    class Impl implements IColorLightAspect {

        protected final ResourceLocation id;
        protected final int light;
        protected final boolean blinking;
        protected final int color;

        public Impl(ResourceLocation id, int light, boolean blinking, int color) {
            this.id = id;
            this.light = light;
            this.blinking = blinking;
            this.color = color;
        }

        @Override
        public int getLightLevel() {
            return light;
        }

        @Override
        public boolean isBlinking() {
            return blinking;
        }

        @Override
        public ResourceLocation getId() {
            return id;
        }

        @Override
        public int getColorMultiplier() {
            return color;
        }
    }
}
