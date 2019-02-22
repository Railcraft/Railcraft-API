/*------------------------------------------------------------------------------
 Copyright (c) CovertJaguar, 2011-2019

 This work (the API) is licensed under the "MIT" License,
 see LICENSE.md for details.
 -----------------------------------------------------------------------------*/

package mods.railcraft.api.signal;

import mods.railcraft.api.core.RailcraftConstantsAPI;

public final class BuiltInSignals {

    public static final int WHITE_COLOR_MULTIPLIER = 0xf9fffe;
    public static final int BLACK_COLOR_MULTIPLIER = 0x1d1d21;
    public static final int LUNAR_COLOR_MULTIPLIER = 0xb7d2da;
    public static final int RED_COLOR_MULTIPLIER = 0xb02e26;
    public static final int GREEN_COLOR_MULTIPLIER = 0x5e7c16;
    public static final int YELLOW_COLOR_MULTIPLIER = 0xfed83d;
    public static final int BLUE_COLOR_MULTIPLIER = 0x3c44aa;
    public static final int PURPLE_COLOR_MULTIPLIER = 0x8932b8;
    public static final int LIGHT_BLUE_COLOR_MULTIPLIER = 0x3ab3da;

    /**
     * The color light aspect for a dark light.
     */
    public static final IColorLightAspect OFF_ASPECT = new IColorLightAspect.Impl(RailcraftConstantsAPI.locationOf("off"), 0, false, BLACK_COLOR_MULTIPLIER);

    public static final IColorLightAspect RED_ASPECT = regular("red", RED_COLOR_MULTIPLIER);
    public static final IColorLightAspect BLINK_RED_ASPECT = blinking("blink_red", RED_COLOR_MULTIPLIER);
    public static final IColorLightAspect YELLOW_ASPECT = regular("yellow", YELLOW_COLOR_MULTIPLIER);
    public static final IColorLightAspect BLINK_YELLOW_ASPECT = blinking("blink_yellow", YELLOW_COLOR_MULTIPLIER);
    public static final IColorLightAspect GREEN_ASPECT = regular("green", GREEN_COLOR_MULTIPLIER);
    public static final IColorLightAspect BLINK_GREEN_ASPECT = blinking("blink_green", GREEN_COLOR_MULTIPLIER);
    public static final IColorLightAspect BLUE_ASPECT = regular("blue", BLUE_COLOR_MULTIPLIER);
    public static final IColorLightAspect LUNAR_ASPECT = regular("lunar", LUNAR_COLOR_MULTIPLIER);

    public static final IRule<IColorLightAspect> SINGLE_RED_RULE = simpleColorLight("single_red", RED_ASPECT);
    public static final IRule<IColorLightAspect> SINGLE_BLINK_RED_RULE = simpleColorLight("single_blink_red", RED_ASPECT);
    public static final IRule<IColorLightAspect> SINGLE_YELLOW_RULE = simpleColorLight("single_yellow", RED_ASPECT);
    public static final IRule<IColorLightAspect> SINGLE_BLINK_YELLOW_RULE = simpleColorLight("single_blink_yellow", RED_ASPECT);
    public static final IRule<IColorLightAspect> SINGLE_GREEN_RULE = simpleColorLight("single_green", RED_ASPECT);
    public static final IRule<IColorLightAspect> SINGLE_BLINK_GREEN_RULE = simpleColorLight("single_blink_green", RED_ASPECT);
    public static final IRule<IColorLightAspect> SINGLE_BLUE_RULE = simpleColorLight("single_blue", RED_ASPECT);
    public static final IRule<IColorLightAspect> SINGLE_LUNAR_RULE = simpleColorLight("single_lunar", RED_ASPECT);

    private static IColorLightAspect regular(String name, int color) {
        return new IColorLightAspect.Impl(RailcraftConstantsAPI.locationOf(name), 6, false, color);
    }

    private static IColorLightAspect blinking(String name, int color) {
        return new IColorLightAspect.Impl(RailcraftConstantsAPI.locationOf(name), 3, true, color);
    }

    private static IRule<IColorLightAspect> simpleColorLight(String name, IColorLightAspect aspect) {
        return new IRule.Impl<>(RailcraftConstantsAPI.locationOf(name), aspect, IColorLightAspect.class);
    }

    private BuiltInSignals() {
        SignalRegistry.INSTANCE.load();
    }
}
